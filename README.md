# 📊 Spring Boot 기반 SW 활용 현황 통계 서비스
> **코멘토 백엔드 직무부트캠프 (26.01.17 ~ 26.02.14)** > 스프링 부트와 MyBatis를 활용하여 사용자 접속 통계를 분석하고 제공하는 API 서비스입니다.

---

## 🚀 수행 과제 요약
* **Spring vs Spring Boot**: 설정 자동화 및 내장 서버 활용의 차이점 이해 및 적용
* **API 설계 및 구현**: RestController를 활용한 5종의 통계 조회 API 개발
* **DB 연동**: MariaDB와 MyBatis 연동을 통한 데이터 관리 최적화
* **비즈니스 로직**: 공공 API 및 DB 연동을 통한 '실제 영업일(평일)' 기준 접속수 집계

## 🛠 Tech Stack
- **Framework**: Spring Boot 3.4.2
- **Language**: Java 17
- **Database**: MariaDB
- **ORM**: MyBatis
- **Tools**: IntelliJ IDEA, Git, Postman, HeidiSQL

---

## 📂 Database Schema 

### 1. Users (사용자 정보)
- `user_id` (PK): VARCHAR(50)
- `department` (ENUM): **`dev`, `plan`, `marketing`, `law`, `design`**
- *인사팀 데이터를 `design` 부서로 통합하여 5개 그룹으로 관리*

### 2. Request Info (접속 기록)
- `id` (PK): BIGINT (Auto Increment)
- `user_id` (FK): VARCHAR(50)
- `create_date`: VARCHAR(8) (YYYYMMDD 형식으로 저장하여 조회 성능 최적화)

### 3. Holiday (공휴일 정보)
- 공공 데이터 API를 활용하여 2000년~2026년(2025 제외) 공휴일 데이터를 DB화하여 통계 로직에 활용

---

## 🔍 API Endpoints Summary

모든 응답은 **JSON** 형식으로 제공되며, 연도(2000-2026) 및 날짜 형식에 대한 유효성 검사를 포함합니다.

| 기능 | Method | Endpoint | 설명 |
|:---:|:---:|:---|:---|
| 월별 접속자 | `GET` | `/stats/users/{year}/{month}` | 특정 월의 순 방문자 수 조회 |
| 일자별 접속자 | `GET` | `/stats/users/{year}/{month}/{day}` | 특정 날짜의 방문자 수 조회 |
| 평균 접속자 | `GET` | `/stats/users/avg-daily/{start-date}/{end-date}` | 기간 내 하루 평균 접속자 수 계산 |
| 부서별 통계 | `GET` | `/stats/users/dept-monthly/{year}/{month}/{dept}` | 특정 부서의 월간 접속자 분석 |
| 평일 통계 | `GET` | `/stats/users/excluding-holidays/{start}/{end}` | **공휴일을 제외한** 기간 내 접속자 수 조회 |

---

## 💡 주요 구현 특징

### 1. 공휴일 제외 통계 로직 (핵심 과제)
- **해결 방식**: 공공 API를 통해 수집한 공휴일 데이터를 내부 DB에 캐싱
- **조합 로직**: SQL로 전체 데이터를 조회한 후, Java Stream API를 사용하여 공휴일 리스트에 포함된 날짜를 필터링하는 방식으로 구현

### 2. 전역 예외 처리 (Exception Handling)
- `@ControllerAdvice`를 활용한 에러 핸들링
- **Validation**: 13월 입력, 잘못된 연도 형식, 유효하지 않은 부서명 요청 시 `400 Bad Request` 및 커스텀 에러 메시지 반환

### 3. 대량 데이터 검증
- SQL Stored Procedure를 활용하여 2000년~2026년 구간의 랜덤 접속 데이터(약 10000이상)를 생성하여 통계 데이터의 신뢰성 검증

---

## 📅 프로젝트 일정
- **3주차**: 환경 설정, DB 설계 및 기본 API 구현 완료
- **4주차**: 통계 API 5종 확정, 공휴일 필터링 로직 구현 및 서버 응답 확인 완료
