# Hi-Coding 트러블슈팅

Hi-Coding Backend를 구현하고 AWS 기반으로 배포하면서 겪은 문제를 인증/보안, 테스트, 공통 응답, AWS 배포, Jenkins, S3, 계층별 설계 문제로 나누어 정리합니다.


---

## 1. OAuth2 로그인 후 JWT 저장 방식

### 문제

OAuth2 소셜 로그인 이후 JWT를 어디에 저장하고 어떻게 전달할지 정리가 필요했습니다.

### 해결

- Access Token은 응답값으로 반환
- Refresh Token은 cookie에 저장

```text
OAuth2 login success
→ Access Token response body
→ Refresh Token cookie
```

### 정리

Access Token은 클라이언트가 API 요청에 사용하고, Refresh Token은 재발급 흐름에서 사용하도록 분리했습니다.

---

## 2. `permitAll()`과 Custom Filter 제외 처리

### 문제

SecurityConfig에서 `permitAll()`을 적용해도 Anonymous User filter와 custom filter chain은 여전히 동작할 수 있습니다.

### 원인

`permitAll()`은 인증 없이 접근을 허용한다는 의미이지, Security filter chain을 완전히 통과하지 않는다는 의미는 아닙니다.

### 해결

JWT 인증이 필요 없는 경로는 custom JWT filter의 `shouldNotFilter()`에서 제외했습니다.

```text
permitAll()
→ 인증 없이 접근 허용

shouldNotFilter()
→ 해당 custom filter 실행 자체를 제외
```

---

## 3. SecurityConfig 순환 참조 문제

### 문제

SecurityConfig에서 여러 `@Bean`을 등록하면서 순환 참조 문제가 발생했습니다.

### 해결

의존성이 순환되는 부분에 지연 로딩을 적용해 순환 참조가 발생하지 않도록 조정했습니다.

```text
SecurityConfig
→ Bean 간 직접 참조 최소화
→ 필요한 경우 Lazy 처리
```

---

## 4. CORS 설정과 Preflight 요청

### 문제

프론트엔드와 연동하는 과정에서 CORS 오류가 발생했습니다. 특히 실제 요청 전에 발생하는 OPTIONS preflight 요청이 SecurityConfig에서 막히는 문제가 있었습니다.

### 해결

- 명시적인 도메인 또는 포트를 허용
- wildcard pattern이 필요한 경우 `allowedOriginPatterns` 사용
- OPTIONS 요청 허용

```text
setAllowedOrigins()
→ 명시적인 origin 목록

allowedOriginPatterns()
→ wildcard pattern 허용
```

### 정리

CORS는 단순히 `*`를 넣는 방식으로 처리하기보다, 배포 환경의 frontend domain과 backend domain을 기준으로 명확히 관리해야 합니다.

---

## 5. 소유자 검증 문제

### 문제

다른 사용자의 주소, 주문, 식물, 성장 기록 등을 수정/삭제할 수 있으면 보안 문제가 발생합니다.

### 해결

수정/삭제 기능에서는 해당 리소스가 현재 로그인한 사용자의 소유인지 검증했습니다.

```text
요청 사용자
→ 리소스 소유자 확인
→ 일치할 때만 수정/삭제 허용
```

단, 이미 상위 단계에서 소유자 검증을 마친 내부 로직에서는 중복 검증을 줄였습니다.

---

## 6. Builder 사용 시 List 초기화 문제

### 문제

Entity 필드에서 `new ArrayList<>()`로 초기화했더라도, Lombok Builder로 객체를 생성하면 List가 null이 되는 문제가 있었습니다.

### 해결

연관관계 List에 `@Builder.Default`를 적용했습니다.

```java
@Builder.Default
@OneToMany(mappedBy = "order")
private List<OrderItem> orderItems = new ArrayList<>();
```

---

## 7. AWS EC2 메모리 부족 문제

### 문제

Free tier EC2에서 Spring Boot, Jenkins, Docker build를 함께 다루다 보니 메모리가 부족해지는 문제가 있었습니다.

### 해결

Swap memory를 추가했습니다.

```bash
sudo dd if=/dev/zero of=/root/swapfile bs=1k count=2000000 conv=excl
sudo chmod 600 /root/swapfile
sudo mkswap /root/swapfile
sudo swapon /root/swapfile
free -m
```

부팅 후에도 유지되도록 `/etc/fstab`에 등록했습니다.

```bash
echo '/root/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

---

## 8. AWS EC2 악의적인 요청과 봇 접근

### 문제

운영 도메인을 외부에 공개하자 비정상적인 URL 탐색, 반복적인 404/403 요청, 봇 접근이 발생했습니다.

### 해결

- UFW로 필요한 포트만 허용
- Nginx access log 기반 Fail2Ban 설정
- GeoIP 기반 국가 제한도 검토
- Nginx rate limit 값 조정

```text
Nginx access log
→ Fail2Ban filter
→ 반복 실패 IP ban
```

### Nginx limit 문제

초기 rate limit 값이 낮아 정상 요청도 제한될 가능성이 있었습니다. 요청 제한 수치를 조정해 정상 사용자 요청과 비정상 요청 차단 사이의 균형을 맞췄습니다.

---

## 9. HTTPS와 도메인 적용

### 문제

EC2 public IP로 접속하면 HTTPS 인증서를 적용하기 어렵고, 사용자 입장에서도 신뢰할 수 있는 주소로 보기 어렵습니다.

### 해결

도메인을 연결하고 Certbot으로 Let's Encrypt 인증서를 발급했습니다.

```bash
sudo apt install certbot python3-certbot-nginx
sudo certbot --nginx -d {domain}
```

HTTP 요청은 HTTPS로 redirect하도록 Nginx를 설정했습니다.

```nginx
server {
    listen 80;
    server_name {domain};

    return 301 https://$host$request_uri;
}
```

---

## 10. `.env` 최신화와 Jenkins 배포

### 문제

`.env`가 변경될 때마다 EC2 서버에 수동으로 반영해야 했습니다.

### 해결

Jenkins pipeline 배포 과정에서 서버의 `.env`를 컨테이너에 mount하도록 구성했습니다.

```bash
docker run -d \
  --name ${CONTAINER_NAME} \
  -p 8081:8081 \
  -v /home/ubuntu/.env:/app/.env \
  ${IMAGE_NAME}
```

### 정리

실제 secret은 GitHub나 Docker image에 포함하지 않고, EC2 서버의 `.env`로 관리했습니다.

---

## 11. Jenkins와 Docker 실행 방식

### 문제

Jenkins를 Docker 컨테이너로 실행하면서 Jenkins 내부에서 Docker build/push를 수행해야 했습니다.

### 검토한 방식

| 방식 | 설명 |
|---|---|
| Docker in Docker | Jenkins 컨테이너 안에 Docker daemon을 실행 |
| Docker out of Docker | Jenkins 컨테이너가 host Docker socket을 mount |

### 적용 및 학습

처음에는 Jenkins 컨테이너 안에 Docker를 설치하는 방식으로 접근했지만, 실무적으로는 host Docker daemon socket을 mount하는 Docker out of Docker 방식이 많이 사용된다는 점을 학습했습니다.

```bash
-v /var/run/docker.sock:/var/run/docker.sock
```

---

## 12. 기존 컨테이너 교체 문제

### 문제

새 이미지를 배포할 때 기존 컨테이너가 같은 이름으로 실행 중이면 새 컨테이너를 실행할 수 없습니다.

### 해결

Jenkins pipeline에서 기존 컨테이너를 중지하고 삭제한 뒤 새 컨테이너를 실행했습니다.

```bash
docker pull ${IMAGE_NAME}
docker stop ${CONTAINER_NAME} || true
docker rm ${CONTAINER_NAME} || true
docker run -d --name ${CONTAINER_NAME} -p 8081:8081 ${IMAGE_NAME}
```

`|| true`를 사용해 기존 컨테이너가 없을 때도 pipeline이 실패하지 않도록 했습니다.

---

## 13. S3 Config 설정

### 문제

식물 이미지와 업로드 파일을 EC2 로컬 디스크에 저장하면 서버 교체나 재배포 시 파일 관리가 어렵습니다.

### 해결

AWS S3를 외부 파일 저장소로 사용했습니다.

S3 접근을 위해 access key, secret key, region을 기반으로 AmazonS3 Bean을 등록했습니다.

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

### 주의

S3 access key와 secret key는 `.env` 또는 외부 설정으로 관리하고, Git에 커밋하지 않습니다.

---

## 14. S3 업로드/삭제 처리

### 업로드

파일 업로드 시 UUID를 붙여 파일명 충돌을 방지했습니다.

```java
public String uploadFile(MultipartFile file) throws IOException {
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    metadata.setContentType(file.getContentType());

    amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

    return amazonS3.getUrl(bucket, fileName).toString();
}
```

### 삭제

S3에 저장된 기존 파일이 더 이상 사용되지 않으면 삭제하도록 처리했습니다.

```java
public void deleteFile(String fileName) {
    amazonS3.deleteObject(bucket, fileName);
}
```

---

## 15. S3 이미지 수정/삭제 시 기존 파일 처리

### 문제

식물 이미지가 수정되거나 식물 객체가 삭제될 때 기존 S3 파일을 삭제하지 않으면 사용하지 않는 이미지가 S3에 계속 남습니다.

### 해결

이미지 수정 시 기존 이미지를 삭제한 뒤 새 이미지를 업로드했습니다.

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

삭제 시에도 기존 이미지 URL이 있으면 S3 object를 삭제하도록 처리했습니다.

### 주의한 점

파일 삭제 조건은 null과 blank를 명확히 구분해야 합니다.

```java
if (url != null && !url.trim().isEmpty()) {
    s3Service.deleteFile(url);
}
```

---

## 정리

```text
인증/인가
→ JWT tokenVersion, OAuth2 token 저장

협업/API
→ DTO validation

AWS 운영
→ EC2/RDS/S3, HTTPS, Fail2Ban, Nginx

CI/CD
→ Jenkins, Docker image build/push, EC2 pull/restart

파일 저장
→ S3 upload/delete, 기존 이미지 정리
```
