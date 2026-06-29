# Hi-Coding AWS S3 연동 정리

Hi-Coding Backend는 식물 이미지와 업로드 파일을 애플리케이션 서버 내부가 아니라 AWS S3에 저장하도록 구성했습니다.

## 사용 목적

- 식물 이미지 업로드
- 상품 또는 기타 서비스 이미지 업로드
- 파일 저장 위치를 애플리케이션 서버와 분리
- 업로드된 파일의 URL을 DB 또는 응답으로 활용
- EC2 서버 재배포 또는 컨테이너 교체 시에도 파일 유지

## 구조

```text
Client
→ Spring Boot API
→ AWS S3 Bucket
→ S3 Object URL 반환
```

## S3 Config 설정

S3 접근을 위해 access key, secret key, region을 기반으로 `AmazonS3` Bean을 등록했습니다.

```java
@Configuration
public class S3Config {
    @Bean
    public AmazonS3 amazonS3(@Value("${cloud.aws.credentials.access-key}") String accessKey,
                             @Value("${cloud.aws.credentials.secret-key}") String secretKey,
                             @Value("${cloud.aws.region.static}") String region) {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
```

## 파일 업로드

파일명 충돌을 막기 위해 UUID를 붙여 S3 object name을 생성했습니다.

```java
@Value("${cloud.aws.s3.bucket}")
private String bucket;

private final AmazonS3 amazonS3;

public String uploadFile(MultipartFile file) throws IOException {
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    metadata.setContentType(file.getContentType());

    amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

    return amazonS3.getUrl(bucket, fileName).toString();
}
```

### 처리 흐름

```text
MultipartFile 요청
→ 파일명 생성
→ ObjectMetadata 설정
→ S3 putObject()
→ S3 URL 반환
```

## 파일 삭제

S3에 저장된 기존 파일이 더 이상 사용되지 않으면 삭제합니다.

```java
public void deleteFile(String fileName) {
    amazonS3.deleteObject(bucket, fileName);
}
```

## 이미지 수정 시 고려사항

이미지가 저장되어 있는 객체를 수정할 때는 기존 이미지를 삭제한 후 새 이미지를 업로드해야 합니다.

```java
if (file != null && !file.isEmpty()) {
    String oldImageUrl = plant.getImageUrl();

    if (oldImageUrl != null) {
        s3Service.deleteFile(oldImageUrl);
    }

    String imageUrl = s3Service.uploadFile(file);
    plant.setImageUrl(imageUrl);
}
```

## 이미지 삭제 시 고려사항

식물 객체가 삭제될 때 기존 이미지 URL이 있다면 S3 object도 함께 삭제해야 합니다.

```java
String url = findPlant.getImageUrl();

if (url != null && !url.trim().isEmpty()) {
    s3Service.deleteFile(url);
}
```
주의할 점은 blank 문자열 검사입니다.

## S3를 사용한 이유

| 이유 | 설명 |
|---|---|
| 서버 저장소 분리 | EC2 local disk에 이미지가 쌓이지 않도록 분리 |
| 재배포 안정성 | Docker container 교체 후에도 파일 유지 |
| 확장성 | 서버가 여러 대가 되어도 동일한 파일 저장소 사용 가능 |
| 접근성 | 업로드된 파일을 URL 기반으로 활용 가능 |
| 운영 편의성 | 정적 파일과 애플리케이션 실행 환경 분리 |

## 운영 시 주의 사항

- S3 access key와 secret key는 반드시 환경 변수로 관리합니다.
- public bucket 정책은 신중하게 설정해야 합니다.
- 업로드 파일 크기 제한이 필요합니다.
- 이미지 확장자와 MIME type 검증이 필요합니다.
- 삭제 시 DB transaction과 S3 object 삭제 순서를 고려해야 합니다.

## Home-Socket과의 차이

Hi-Coding은 S3를 통해 파일 저장소를 애플리케이션 서버와 분리하는 경험을 했습니다. Home-Socket은 파일 저장소보다 Redis 캐싱, Kafka 이벤트 처리, WebSocket 알림, PostgreSQL/Flyway, GitHub Actions 기반 배포 안정화에 초점을 두었습니다.
