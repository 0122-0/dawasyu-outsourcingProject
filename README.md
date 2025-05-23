# Dawssyu

![image](https://github.com/user-attachments/assets/7afa7809-4c0e-4c65-86a0-669c431993b7)


## 팀원 및 역할
```
이름 | 담당 기능 | 추가 업무
강동연 | 메뉴 등록, 메뉴 전체 조회, 메뉴 상세 조회, 메뉴 수정/삭제 | 발표 자료(PPT) 제작
김규현 | 주문 생성, 전체 주문 목록 조회, 주문 상태 변경 | 트러블 슈팅 정리
김믿음 | 리뷰 생성, 사용자 리뷰 전체 조회, 가게별 리뷰 전체 조회, 리뷰 상세 조회/수정 | 발표
김현정 | JWT 기반 로그인, 회원가입, 회원탈퇴, 비밀번호 수정, 주소 수정 | 발표 자료(대본) 제작
박민혁 | 가게 등록, 카테고리 생성 | 시연 동영상 제작
```

---

## 프로그램 설명 (아웃소싱 프로젝트)
이 프로젝트는 배달 애플리케이션 개발을 목표로 진행되었습니다. 사용자는 회원가입, 로그인, 회원탈퇴 기능을 이용할 수 있으며, 회원가입 시 USER와 OWNER로 역할을 구분할 수 있습니다.
OWNER는 최대 3개의 가게를 등록할 수 있으며, 가게 카테고리 설정과 메뉴 등록 기능을 제공합니다.
USER는 등록된 가게를 조회하고, 메뉴를 선택하여 주문할 수 있습니다. 또한, 회원정보 수정 기능도 지원합니다.
실제 배달 애플리케이션의 핵심 기능을 직접 설계하고 구현하는 것을 목표로 삼았습니다.

---

## 주요 구현 기능
#### 1. 회원가입/로그인
- 이메일 형식 아이디 + 영문/숫자/특수문자 포함 8자 이상 비밀번호로 회원가입.

- 비밀번호는 직접 만든 Bcrypt 인코더로 암호화.

- 가입 시 USER(고객) / OWNER(사장님) 권한 선택.

- 아이디 중복, 이메일/비밀번호 형식 오류 시 가입 불가.

- 비밀번호 확인 후 탈퇴 가능. 탈퇴하면 아이디 재사용/복구 불가.

- 로그인은 가입한 아이디와 비밀번호로 진행.

#### 2. 가게
- 사장님만 가게 생성 가능 (최대 3개까지).

- 가게 오픈/마감 시간, 최소 주문 금액 설정.

- 폐업 시 가게는 숨김 처리되고, 추가 등록 가능.

- 가게 단건 조회 시 메뉴도 같이 조회됨.

#### 3. 메뉴
- 사장님은 본인 가게에만 메뉴 등록/수정/삭제 가능.

- 메뉴 삭제 시 조회는 안 되지만, 주문 내역에는 보임.

#### 4. 주문
- 고객은 메뉴 하나씩 주문 가능.

- 사장님은 주문 상태를 순서대로 변경.

- 주문 생성/상태 변경 시 AOP로 로그 기록.

- 최소 주문 금액 이상, 오픈 시간 안에만 주문 가능.

#### 5. 리뷰
- 배달 완료된 주문만 리뷰 작성 가능 (별점 1~5점).

- 리뷰는 다건 조회만 가능, 최신순 정렬.

- 별점 범위로 필터링 가능.

---

## 기술적 포인트
#### 1. JWT 기반 로그인 구현

- 자체 구현한 @LoginUser 어노테이션을 통해 로그인 사용자 인증 및 확인 처리.

#### 2. 다대다(N:N) 연관관계 설계 및 구현

- 카테고리 ↔ 스토어카테고리 ↔ 스토어

- 오더 ↔ 오더메뉴 ↔ 메뉴

- 중간 테이블(스토어카테고리, 오더메뉴)을 만들어 다대다 관계 매핑.

#### 3. 

---

## 기술 스택
- Java 17 
- Spring Boot
- MySQL
- Spring Data JPA
- Postman (API 테스트용)
- JWT 기반 로그인, 인증/인가
- IntelliJ IDEA
- Lombok, Jakarta Validation
- GitHub

---

### 변수 지정표
![image](https://github.com/user-attachments/assets/efac7732-9ea7-4b29-8535-9c1a416549da)

---

## 연관관계
<img width="518" alt="image" src="https://github.com/user-attachments/assets/7f66b473-d59c-45fd-a090-00abadae1077" />

---

## API 명세서

### User, Auth
![image](https://github.com/user-attachments/assets/e8d65e4f-f4cc-4e1c-ac4c-9f30e0a232b3)

### Store
![image](https://github.com/user-attachments/assets/f93581a9-a98c-49e1-aee1-cc4aaeaab5d5)

### Category
![image](https://github.com/user-attachments/assets/25e67b44-5dad-4a1f-bf42-bc8375589d4f)

### Menu
![image](https://github.com/user-attachments/assets/84594244-52aa-4563-ae58-7b5db2b9bedb)

### Order
![image](https://github.com/user-attachments/assets/403176cf-ff88-4771-8eb6-896160036c7d)

### Review
![image](https://github.com/user-attachments/assets/5010a055-33a3-4471-8c0a-416b8017535b)

---

## ERD 작성
![outsourcingProject](https://github.com/user-attachments/assets/3f2e1375-cb1e-43de-acae-f2b80160182e)


---

## SQL 작성

```
CREATE TABLE `review` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`리뷰 내용`	TEXT	NULL,
	`별점(0~5) 정도`	TINYINT	NULL,
	`작성자 유저 ID`	BIGINT(FK)	NOT NULL,
	`리뷰 대상 가게 ID`	BIGINT(FK)	NOT NULL
);

CREATE TABLE `user` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`이메일`	VARCHAR(50)	NULL,
	`비밀번호`	VARCHAR(20)	NULL,
	`이름`	VARCHAR(20)	NULL,
	`전화번호`	VARCHAR(20)	NULL,
	`닉네임`	VARCHAR(20)	NULL,
	`기본주소`	VARCHAR(100)	NULL,
	`상세주소`	VARCHAR(100)	NULL,
	`유저 유형`	ENUM('USER', 'OWNER')	NULL
);

CREATE TABLE `menu` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`메뉴 이름`	VARCHAR(20)	NULL,
	`메뉴 설명`	TEXT	NULL,
	`가격`	INT	NULL,
	`메뉴 상태`	ENUM('ACTIVE', 'DELETED')	NULL,
	`가게 ID`	BIGINT(FK)	NOT NULL
);

CREATE TABLE `store` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`가게 이름`	VARCHAR(20)	NULL,
	`전화번호`	VARCHAR(20)	NULL,
	`사업자등록번호`	VARCHAR(20)	NULL,
	`기본주소`	VARCHAR(100)	NULL,
	`상세주소`	VARCHAR(100)	NULL,
	`최소 주문금액`	INT	NULL,
	`오픈 시간`	TIME	NULL,
	`마감 시간`	TIME	NULL,
	`유저 ID (사장님)`	BIGINT (FK)	NOT NULL
);

CREATE TABLE `order` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`주문번호`	VARCHAR(20)	NULL,
	`전체 금액`	INT	NULL,
	`주문 상태`	ENUM('REQUESTED', 'ACCEPTED', 'DELIVERING', 'COMPLETED')	NULL,
	`주문자 유저 ID`	BIGINT(FK)	NOT NULL,
	`주문한 가게 ID`	BIGINT(FK)	NOT NULL
);

CREATE TABLE `baseEntity` (
	`생성일시`	DATETIME	NULL,
	`수정일시`	DATETIME	NULL
);

CREATE TABLE `ordermenu` (
	`Key`	VARCHAR(255)	NOT NULL,
	`수량`	INT	NOT NULL,
	`메뉴 아이디`	BIGINT(FK)	NOT NULL	DEFAULT FK,
	`오더 아이디`	BIGINT(FK)	NOT NULL	DEFAULT FK
);

CREATE TABLE `storeCategory` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`스토어 아이디`	BIGINT(FK)	NOT NULL,
	`카테고리 아이디`	BIGINT(FK)	NOT NULL	DEFAULT FK
);

CREATE TABLE `category` (
	`기본키`	BIGINT(PK,Auto Increment)	NOT NULL	DEFAULT PK,
	`카테고리 이름`	VARCHAR(20)	NULL
);

ALTER TABLE `review` ADD CONSTRAINT `PK_REVIEW` PRIMARY KEY (
	`기본키`
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`기본키`
);

ALTER TABLE `menu` ADD CONSTRAINT `PK_MENU` PRIMARY KEY (
	`기본키`
);

ALTER TABLE `store` ADD CONSTRAINT `PK_STORE` PRIMARY KEY (
	`기본키`
);

ALTER TABLE `order` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (
	`기본키`
);

ALTER TABLE `ordermenu` ADD CONSTRAINT `PK_ORDERMENU` PRIMARY KEY (
	`Key`
);

ALTER TABLE `storeCategory` ADD CONSTRAINT `PK_STORECATEGORY` PRIMARY KEY (
	`기본키`
);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`기본키`
);

```

---

## 패키지 구조


