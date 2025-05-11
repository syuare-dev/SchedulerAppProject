# Project SchedulerApp
***

본 프로그램은 사용자가 일정을 관리할 수 있도록 돕는 일정 관리 시스템으로,
사용자가 일정을 생성, 수정, 삭제할 수 있는 기본적인 CRUD(Create, Read, Update, Delete) 기능을 제공한다.   

***

## 일정 관리 앱 요구 사항

***
- **요청(Request)과 응답(Response) 통신 데이터 형태**: JSON 형태
- **일정 생성 시 포함되어야 할 데이터**:
  - **식별자(id)**: 고유 식별자(ID)를 자동으로 생성 및 관리
  - **할 일(task)**: 일정의 제목이나 내용
  - **작성자명(authorName)**: 일정을 작성한 사람의 이름
  - **비밀번호(password)**: 일정을 보호하기 위한 비밀번호
  - **작성일(createdDate)** / **수정일(modifiedDate)**: 일정의 작성일과 수정일 (최초 입력 시 수정일은 작성일과 동일)
    - **형식**: `YYYY-MM-DD`

## HTTP API 설계

- 대부분의 API는 CRUD 작업을 수행한다.

***

### 설계 순서
1. **HTTP Method**
   - `POST`: CREATE
   - `GET` : READ
   - `PUT`, `PATCH` : UPDATE
   - `DELETE` : DELETE

2. **Restful API → URL Mapping**

3. **요청 및 응답값 설계**
   - HTTP Method + URL 만으로 어떤 API 인지 구분이 가능해야 한다.

## API 상세 명세

***

### 일정 생성 / 작성하기
- **Method** : `POST`
- **URL**: `/api/schedules`

### 전체 일정 조회 (등록된 일정 불러오기)
- **Method** : `GET`
- **URL**: `/api/schedules`

### 선택 일정 조회
- **Method** : `GET`
- **URL**: `/api/schedules/{id}`

### 선택한 일정 삭제
- **Method** : `GET`
- **URL**: `/api/schedules/{id}`


| 기능               | Method | URL                    | Request             | Response               | HTTP Status |
|--------------------|--------|------------------------|---------------------|------------------------|-------------|
| 일정 생성          | POST   | /api/schedules          | 요청 body (id, task, authorName, password, createdDate) | 일정 생성 정보 (id, task, authorName, password, createdDate, modifiedDate) | 201 Created |
| 전체 일정 조회     | GET    | /api/schedules          | 없음                | 다건 일정 정보         | 200 OK      |
| 선택 일정 조회     | GET    | /api/schedules/{id}     | 없음                | 단건 일정 정보         | 200 OK      |
| 선택 일정 수정     | PATCH  | /api/schedules/{id}     | task, authorName, password | 수정된 일정 정보       | 200 OK      |
| 선택 일정 삭제     | DELETE | /api/schedules/{id}     | password            | 없음                   | 200 OK      |

### HTTP 상태 코드

- **200 OK**: 요청이 성공적으로 처리될 때 반환된다.
- **201 Created**: 일정이 성공적으로 생성되었을 때 반환된다.
- **400 Bad Request**: 잘못된 데이터로 요청되었을 때 반환된다.
- **404 Not Found**: 요청한 리소스가 없을 때 반환된다.
