### 개요 
- 구성한 서비스는 REST API 로서의 기능과 Schedule 서비스를 제공하며, 별도로 작성된 화면은 존재하지 않음.
- REST API로 개발 된 기능은 다음과 같음.
  - `GET - /api/v1/menus` : 커피메뉴목록조회API
  - `GET - /api/v1/menus/top3` : 인기메뉴 목록조회 API
  - `POST - /api/v1/order` : 커피 주문, 결제하기 API
  - `POST - /api/v1/point` : 포인트 충전하기 API
- 최근 7일간 인기있는 메뉴 3개를 만들기 위해 `OrderHistorySchedulerService` 구현
- 서비스는 8080포트로 서버를 구동한다.
  - ` http://localhost:8080`
  
### 기술스택
- JDK 17 : 로컬 개발환경에 설치된 버전을 이용
- Framework : Spring Boot를 사용하였으며 다음 모듈을 이용한다.
  - webflux : 외부 API 연계 시 client로 WebClient 사용
  - data-jpa : DB ORM
  - h2 : 인메모리 DB (테스트 용도)
  - mysql : docker를 통해 서비스 용도로 사용
  - validation : 필수값 검증
  - lombok : 코드 가독성
- Testing : Junit5 사용
  
### 설계 및 구현
- 커피 메뉴 목록 조회 API
  - 커피 정보(메뉴ID, 이름, 가격)을 조회하는 API를 작성합니다.
  - menu 테이블을 생성하여 관리할 수 있도록 함
- 포인트 충전하기 API 
  - `point` 엔티티를 설계할때 `transaction_type`을 `WITHDRAWAL`, `DEPOSIT`로 구분하여 저장할 수 있도록 함.
  - 충전시에는 해당 타입이 `DEPOSIT`, 포인트로 결제시에는 `WITHDRAWAL` 타입으로 저장될 수 있도록 함
  - 포인트 조회시 `user_id` 기준으로 조회 후 `DEPOSIT`의 합과 `WITHDRAWAL`의 합에서 차이를 구함(차이가 음수일시 예외)
- 커피주문/결제 하기 API
  - 주문한 메뉴가 전체메뉴에 있는지 검증
  - 주문 엔티티 생성 : 이때 `OrderStatus`는 `READY` 값으로 세팅
  - 주문 아이템 생성
  - 지불해야할 금액과 현재 내가 보유한 포인트에 대한 검증 
  - 지불시에 `point`에 `WITHDRAWAL` 타입으로 저장
  - 주문 상태 변경 `READY` -> `ORDER`
  - 주문수 카운트 
    - 최근 7일간의 인기있는 메뉴 조회를 위해 시계열 데이터를 생성 후 삽입
  - 주문내역 데이터 수집 플랫폼으로 전송 비동기 논블록킹 호출
- 인기있는 목록 조회 API
  - 스케쥴 서비스를 통해 `popular_menu` 테이블은 3개의 데이터만 존재함
  - 데이터 조회 후 count 내림차순으로 정렬하여 제공 
- `OrderHistorySchedulerService` 스케쥴 서비스 구현
  - 매일 0시 0분에 실행하는 스케쥴 서비스
  - 7일전 데이터부터 현재까지 쌓인 데이터 그룹핑 후 Map의 value로 내림차순 정렬 후 상위 3개 아이템 반환
  - 기존 인기 데이터 삭제 처리
  - 반환된 상위 3개 아이템으로 인기 데이터 업데이트 처리

### 테이블 DDL
- 엔티티간의 관계를 생성하지 않음.
- delete 정책은 물리적인 삭제가 아닌 논리적인 삭제를 하도록 함.

```mysql
create table order_system.users
(
    id         bigint auto_increment
        primary key,
    created_at timestamp    null,
    deleted_at timestamp    null,
    updated_at timestamp    null,
    order_id   bigint       null,
    point_id   bigint       null,
    user_name  varchar(255) null
);
```
- 사용자 테이블은 주문번호식별자, 포인트식별자, 사용자이름으로 구성

```mysql
create table order_system.menus
(
    id         bigint auto_increment
        primary key,
    created_at timestamp      null,
    deleted_at timestamp      null,
    updated_at timestamp      null,
    name       varchar(255)   null,
    price      decimal(19, 2) null
);

create index idx_menus_deleted_at
    on order_system.menus (deleted_at);

create index idx_menus_id
    on order_system.menus (id);
```

- 메뉴 테이블은 메뉴명, 가격을 가질수 있도록 함.
- 메뉴 ID와 삭제여부가 조회조건에 포함되어 index 생성함.

```mysql
create table order_system.points
(
    id               bigint auto_increment
        primary key,
    created_at       timestamp      null,
    deleted_at       timestamp      null,
    updated_at       timestamp      null,
    deposit_date     timestamp      null,
    point            decimal(19, 2) null,
    transaction_type varchar(255)   null,
    user_id          bigint         null,
    withdrawal_date  timestamp      null
);

create index idx_points_deleted_at
    on order_system.points (deleted_at);

create index idx_points_transaction_type
    on order_system.points (transaction_type);

create index idx_points_user_id
    on order_system.points (user_id);
```
- transaction_type으로 입금,출금을 구분할 수 있도록 함

```mysql
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
- 주문일자와 주문상태를 가질 수 있도록 함 

```mysql
create table order_system.order_items
(
    id          bigint auto_increment
        primary key,
    created_at  timestamp      null,
    deleted_at  timestamp      null,
    updated_at  timestamp      null,
    menu_id     bigint         null,
    order_id    bigint         null,
    order_price decimal(19, 2) null
);

create index idx_order_items_deleted_at
    on order_system.order_items (deleted_at);

create index idx_order_items_order_id
    on order_system.order_items (order_id);
```
- 복수의 주문을 고려하여 `order_id`을 참조할 수 있도록 함 

```mysql
create table order_system.order_history
(
    id         bigint auto_increment
        primary key,
    created_at timestamp null,
    deleted_at timestamp null,
    updated_at timestamp null,
    count_id   bigint    null,
    menu_id    bigint    null,
    user_id    bigint    null
);

create index idx_order_history_created_at
    on order_system.order_history (created_at);

create index idx_order_history_deleted_at
    on order_system.order_history (deleted_at);
```
- 주문 및 결제가 완료되면 주문이력당 1row씩 쌓을수 있도록 함 

```mysql
create table order_system.popular_menu
(
    id         bigint auto_increment
        primary key,
    created_at timestamp null,
    deleted_at timestamp null,
    updated_at timestamp null,
    count      bigint    null,
    menu_id    bigint    null
);

create index idx_popular_menu_deleted_at
    on order_system.popular_menu (deleted_at);
```
- 스케줄링 서비스를 통해 생성되고 삭제되는 테이블
  
  
  
