# Hi-Coding에서 Home-Socket으로 개선한 점

Hi-Coding Backend는 쇼핑몰과 식물 관리 기능을 구현하고, AWS 기반 배포와 Jenkins CI/CD를 경험한 프로젝트입니다. Home-Socket은 이 경험을 바탕으로 운영 구조, 성능, 비동기 처리, 배포 안정성을 개선하기 위해 진행한 리팩토링/확장 프로젝트입니다.

## 개선 배경

Hi-Coding을 구현하고 배포하면서 다음과 같은 개선 필요성을 느꼈습니다.

- 반복 조회 API에 대한 캐싱 부재
- 주문/결제 이후 후속 처리의 동기적 흐름
- 배포 이후 health check와 rollback 부재
- 운영 보안 설정의 체계화 필요
- 쿼리 성능 개선을 위한 index 관리 필요
- 배포 및 트러블슈팅 문서화 필요

## 비교

| 항목 | Hi-Coding | Home-Socket |
|---|---|---|
| DB | MySQL RDS | PostgreSQL |
| Cache | 미적용 | Redis |
| Messaging | 미적용 | Kafka |
| Realtime | 제한적 | WebSocket / STOMP |
| CI/CD | Jenkins | GitHub Actions |
| 배포 방식 | Docker image build/push/pull | JAR upload + Docker Compose rebuild |
| Cloud | AWS EC2 | Oracle Cloud |
| Schema 관리 | JPA 중심 | Flyway migration |
| 성능 개선 | 기본 구현 중심 | Redis cache, index, k6 테스트 |
| 운영 보안 | Nginx, HTTPS, Fail2Ban 경험 | Nginx, HTTPS, Fail2Ban, SSH key-only, iptables 정리 |
| 배포 검증 | 수동 확인 중심 | health check + rollback 자동화 |

## 1. 캐싱 도입

Hi-Coding에서는 조회 API가 DB 조회 중심으로 동작했습니다. Home-Socket에서는 카테고리, 상품, 리뷰처럼 반복 조회가 많은 API에 Redis cache를 적용했습니다.

```text
Client
→ API 요청
→ Redis cache hit
→ DB 조회 생략
```

## 2. Kafka 이벤트 처리

Hi-Coding에서는 주문/결제 후속 처리가 동기 흐름에 가까웠습니다. Home-Socket에서는 결제 완료 후 `order.paid` Kafka 이벤트를 발행하고, Consumer가 알림 저장과 WebSocket 전송을 담당하도록 분리했습니다.

```text
결제 승인
→ Transaction Commit
→ order.paid Kafka Event
→ Notification Consumer
→ DB 저장 + WebSocket 알림
```

## 3. DB 마이그레이션과 인덱스

Home-Socket에서는 Flyway를 사용해 schema 변경 이력을 관리하고, 조회/주문 성능 개선용 index를 migration으로 관리했습니다.

```text
V1: 기본 테이블
V2: unique index
V3: performance index
V4: payment
V5: notification
```

## 4. CI/CD 안정성 개선

Hi-Coding의 Jenkins pipeline은 Docker image를 빌드하고 배포하는 경험을 제공했습니다. Home-Socket에서는 GitHub Actions를 사용하면서 health check와 rollback을 추가했습니다.

```text
배포
→ /v3/api-docs health check
→ 실패 시 이전 JAR로 rollback
```

## 5. 운영 구조 개선

Hi-Coding에서는 AWS EC2, RDS, S3, Nginx, HTTPS, Jenkins를 직접 구성했습니다. Home-Socket에서는 이 경험을 바탕으로 App Server와 DB Server를 분리하고, Spring Boot app을 `127.0.0.1:8081`로만 바인딩해 Nginx를 통한 접근만 허용했습니다.

## 정리

Hi-Coding은 백엔드 기능 구현과 AWS/Jenkins 기반 초기 배포 경험을 얻은 프로젝트이고, Home-Socket은 그 경험을 바탕으로 캐싱, 이벤트 처리, 배포 검증, 운영 보안, 성능 테스트를 추가한 개선 프로젝트입니다.
