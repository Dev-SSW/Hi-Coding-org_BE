# API 목록

이 문서는 Hi-Coding Backend의 주요 API를 도메인별로 정리한 문서입니다. 정확한 요청/응답 DTO는 Swagger UI와 각 Controller 코드를 기준으로 확인합니다.

## Public

| Method | Path | 설명 |
|---|---|---|
| `POST` | `/public/signup` | 회원가입 |
| `POST` | `/public/signin` | 로그인/JWT 발급 |
| `POST` | `/public/validate` | 토큰 유효성 검증 |
| `POST` | `/refresh` | 토큰 재발급 |
| `GET` | `/public/item/searchAll` | 전체 상품 조회 |
| `GET` | `/public/item/searchId/{itemId}` | 상품 단건 조회 |
| `GET` | `/public/item/searchName` | 상품명 검색 |
| `POST` | `/public/items/addLike/{itemId}` | 상품 좋아요 |
| `POST` | `/public/upload` | S3 multipart 파일 업로드 |
| `POST` | `/public/delivery` | 배송 생성 |
| `POST` | `/public/delivery/start/{deliveryId}` | 배송 시작 |
| `POST` | `/public/delivery/complete/{deliveryId}` | 배송 완료 |
| `POST` | `/public/delivery/cancel/{deliveryId}` | 배송 취소 |
| `GET` | `/public/delivery/getInfo/{deliveryId}` | 배송 정보 조회 |
| `GET` | `/public/delivery/status/{status}` | 배송 상태별 조회 |
| `POST` | `/public/getPlantRecommandResult` | MBTI 기반 식물 추천 |

## User

| Method | Path | 설명 |
|---|---|---|
| `GET` | `/user/info` | 내 정보 조회 |
| `PUT` | `/user/changePassword` | 비밀번호 변경 |
| `PUT` | `/user/cart/add` | 장바구니 상품 추가 |
| `GET` | `/user/cart/get` | 장바구니 조회 |
| `PUT` | `/user/cartItem/update/{cartItemId}` | 장바구니 상품 수량 변경 |
| `DELETE` | `/user/cartItem/remove/{cartItemId}` | 장바구니 상품 삭제 |
| `POST` | `/user/order/create` | 단건 주문 생성 |
| `POST` | `/user/order/create/fromCart` | 장바구니 기반 주문 생성 |
| `POST` | `/user/order/cancel` | 주문 취소 |
| `POST` | `/user/order` | 주문 페이지 데이터 조회 |
| `GET` | `/user/order/history` | 주문 내역 조회 |
| `GET` | `/user/coupon/search` | 사용자 보유 쿠폰 조회 |
| `POST` | `/user/device/register` | 기기 일련번호 등록 |

## Plant

| Method | Path | 설명 |
|---|---|---|
| `GET` | `/plant/getList` | 내 식물 목록 조회 |
| `GET` | `/plant/getPlant/{plantId}` | 식물 단건 조회 |
| `POST` | `/plant/create` | 식물 등록, multipart |
| `PUT` | `/plant/update/{plantId}` | 식물 수정, multipart |
| `DELETE` | `/plant/delete/{plantId}` | 식물 삭제 |
| `POST` | `/plant/PlantGrowthLog/create/{plantId}` | 식물 성장 기록 등록 |
| `GET` | `/plant/PlantGrowthLog/get/{plantId}` | 특정 날짜 성장 기록 조회 |
| `GET` | `/plant/PlantGrowthLog/GetAll/{plantId}` | 전체 성장 기록 조회 |
| `POST` | `/plant/environment/create` | 식물 관리 환경 등록 |

## Admin

| Method | Path | 설명 |
|---|---|---|
| `POST` | `/admin/category/create` | 카테고리 생성 |
| `GET` | `/admin/category/getInfo/{categoryId}` | 카테고리 단건 조회 |
| `GET` | `/admin/category/search` | 카테고리 검색 |
| `DELETE` | `/admin/category/deleteCategory/{categoryId}` | 카테고리 삭제 |
| `POST` | `/admin/item/create` | 상품 생성 |
| `PUT` | `/admin/item/{itemId}` | 상품 수정 |
| `DELETE` | `/admin/item/delete/{itemId}` | 상품 삭제 |
| `POST` | `/admin/coupon/create` | 쿠폰 생성 |
| `POST` | `/admin/coupon/publish` | 쿠폰 발급 |
| `GET` | `/admin/coupon/birthday` | 생일 쿠폰 발급 |
| `GET` | `/admin/coupon/searchAll` | 전체 쿠폰 조회 |
| `POST` | `/admin/payment/create/{orderId}` | 결제 생성 |
| `POST` | `/admin/payment/process/{paymentId}` | 결제 처리 |
| `GET` | `/admin/payment/getInfo/{paymentId}` | 결제 단건 조회 |
| `GET` | `/admin/payment/getStatus/{status}` | 결제 상태별 조회 |
| `POST` | `/admin/payment/refund/{paymentId}` | 결제 환불 |
| `GET` | `/admin/payment/searchList` | 결제 목록 조회 |

## OAuth2

Spring Security OAuth2 Client 기본 시작 경로를 사용합니다.

```text
/oauth2/authorization/google
/oauth2/authorization/naver
```

Callback URI는 OAuth provider 콘솔 설정과 애플리케이션 설정이 일치해야 합니다.

```text
/login/oauth2/code/google
/login/oauth2/code/naver
```
