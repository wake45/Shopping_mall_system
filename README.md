# 설명
## 프로젝트명 : Shopping_mall_system ( 상품등록, 상품관리, 주문처리, 주문취소 및 환불 )
## 사용기술 : Kotlin, JavaScript, MariaDB, SpringBoot, MyBatis
## 기간 : 2025년 01/21 ~ 02/07

# 주요 설계 고려사항
### 상품 카테고리 및 계정 기능 생략
### 배송 과정 생략 (하루 지난 주문은 서비스 시작시 배치로 배송완료 상태로 변경)
### 관리자 계정과 고객 계정 통합（하나의 계정으로 상품 등록 및 주문까지 모두 가능）
### 상품 삭제시 상품 상태만 삭제로 변경 ( 과거 주문에 포함된 상품 관리하기 위함 )
### 일괄 주문시 단일 상품 환불,주문취소의 기능 제외 (주문 테이블과 주문상품 테이블의 status만 나누면 되기 때문에)
### > 필요하다면 Orders 테이블의 상태는 배송관련 기능, OrderItems 테이블의 상태는 주문관련 기능으로 분리하면됨
### ※ 프로젝트의 취지는 kotiln, MyBatis, MariaDB, Batch 사용 및 이미지 저장 로드 기능 구현
### - 동시성 문제에 대한 고려 해볼것 ( 수량 관련 ) 
###    > @Transactional 어노테이션을 사용해 일부해결, 추가적으로는 Lock을 구현 필요!

# API Endpoint에 대한 설명
## 상품등록
### 상품등록 화면 이동 input : user_id
### 상품등록 input : Product, imageFile
   
## 상품조회 및 수정,삭제(상품관리)
### 상품관리 화면 이동 input : user_id, page, size / output : user_id, products, total, pageNumbers, currentPage
### 상품수정 input : product, imageFile
### 상품삭제 input : product_id, user_id

## 주문하기
### 주문하기 화면 이동 input : user_id, page, size / output : user_id, products, total , pageNumbers, currentPage
### 주문하기 input : OrderRequest(OrderItems)

## 주문조회 및 주문상태변경
### 주문관리 화면 이동 : input : user_id, page, size / output: user_id, orders, total, pageNumbers, currentPage
### 주문상세조회 : input : OrderRequest(order_id) / output : OrderResult(orderItems)
### 주문상태 업데이트 : input : action, order_id

# History
## 1월 21일
### Git Repository 생성
### SpringBoot Project 생성
### ERD 생성
### DB구축(MariaDB)
### 설계서 작성(ppt)

## 1월 22일
### 화면 그리기
### DB 인덱싱 추가

## 1월 23일
### 메뉴 이동 구현
### 테스트 데이터 자동 생성 기능 구현
### 로그인 후 사용자 조회 기능 구현

## 1월 24일
### 상품 등록 기능 구현
### 이미지 저장 기능 구현
### 상품 목록 조회 기능 구현
### 상품 수정 팝업 수정

## 1월 25일
### 상품 수정 및 삭제 기능 구현

## 1월 26일
### 상품 조회 페이지네이션 추가

## 1월 27일
### 주문하기 상품 조회 기능 개발
### 주문하기 상품 페이지네이션 추가
### 주문하기 화면 디자인 수정
### 기타 오류 개선(상품관리 수정 이미지 null 허용, user_id int 값으로 변환 등)

## 1월 28일
### 장바구니 화면 수정
### 장바구니 전체 구매(주문하기) 기능 개발
### 바로 구매 기능 개발
### 주문, 주문상품 테이블 수정
### 뒤로가기 버튼 추가

## 1월 30일
### 상품등록 & 수정시 가격 유효성 로직 추가
### 주문하기시 재고 수량 확인 및 재고 감소 로직 추가
### 주문관리시 주문내역 조회 기능 개발

## 2월 1일
### 주문상세조회 기능추가(상품조회추가)
### 주문상세조회 디자인 수정
### 주문하기-전체구매시 하나의 MAPPER 메소드에서 UPDATE문 여러번 반복불가로 인해 MAPPER호출 반복으로 변경(MARIA DB이슈)

## 2월 2일
### 주문상태 변경 기능 개발(주문취소, 반품신청)
### 주문상태 변경 배치 개발(배송완료)


## 실행 방법
### ./mvnw spring-boot:run