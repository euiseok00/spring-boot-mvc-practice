# spring-boot-mvc-practice
코멘토 백엔드 직무부트캠프 (26-01-17 ~ 26-02-14)


# 📊 Spring Boot 기반 통계 서비스 (Comento Assignment)

스프링 부트와 MyBatis를 활용하여 사용자 접속 통계를 제공하는 API 서비스입니다. 
데이터베이스의 기초 설계부터 공휴일을 제외한 정교한 통계 로직 구현까지 포함하고 있습니다.

## 🚀 수행 과제 요약
* **Spring vs Spring Boot**: 설정의 자동화와 내장 서버 활용의 차이점 이해 및 적용
* **API 설계 및 구현**: RestController를 활용한 통계 데이터 조회 API 개발
* **DB 연동**: MariaDB와 MyBatis를 활용한 효율적인 데이터 관리 및 쿼리 최적화
* **비즈니스 로직**: 공휴일 데이터를 활용한 '실제 영업일' 기준 로그인 수 집계

---

## 🛠 Tech Stack
- **Framework**: Spring Boot 3.4.2
- **Language**: Java 17
- **Database**: MariaDB
- **ORM**: MyBatis
- **Tool**: IntelliJ IDEA, Git, Postman

---

## 📂 Database Schema
### 1. Users (사용자 정보)
- 부서별 통계를 위해 `department`를 **ENUM** 타입으로 관리합니다.
- 부서 목록: 개발팀, 기획팀, 인사팀, 마케팅팀, 법률팀

### 2. Request Info (접속 기록)
- `create_date`를 `YYYYMMDD` 형식으로 저장하여 일자별 통계에 최적화했습니다.

---

## 🔍 주요 API 구현 내용

### 1. 년도별 로그인 수 조회
- 특정 연도를 입력받아 총 접속자 수를 반환합니다.

### 2. 월별 접속자 수 조회 (Unique User)
- `COUNT(DISTINCT user_id)`를 활용하여 중복을 제거한 순 방문자 수를 집계합니다.

### 3. [진행 중] 공휴일 제외 통계 로직
- **해결 방식**: 공휴일 정보를 별도의 데이터로 관리하여 자바 로직과 SQL을 조합해 필터링합니다.
- **로직**: `create_date`가 공휴일 리스트에 포함되지 않는 데이터만 추출.

---

## 🛡️ Exception Handling & Validation (진행 중)
`@ControllerAdvice`를 활용하여 잘못된 요청에 대한 예외 처리를 구현 중입니다.

- **연도 입력 오류**: '20년도'와 같은 잘못된 형식 입력 시 예외 발생 및 메시지 반환
- **월 범위 오류**: 1~12월을 벗어난 '13월' 등 입력 시 에러 핸들링
