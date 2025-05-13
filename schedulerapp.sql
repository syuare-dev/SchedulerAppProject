USE schedulerapp_test;

/* schedules(일정) 테이블 생성 */
CREATE TABLE IF NOT EXISTS schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    task VARCHAR(255) NOT NULL COMMENT '할 일',
    authorName VARCHAR(100) NOT NULL COMMENT '작성자명',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_date DATE NOT NULL COMMENT '일정 생성일',
    modified_date DATE NOT NULL COMMENT '일정 수정일'
);

/* author(작성자) 테이블 생성 */
CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    name VARCHAR(100) NOT NULL COMMENT '작성자 이름',
    email VARCHAR(255) COMMENT '작성자 이메일',
    created_date DATE COMMENT '등록일',
    modified_date DATE COMMENT '수정일'
);


# 컬럼 추가(NULL 허용)
ALTER TABLE schedules
    ADD COLUMN author_id BIGINT NULL COMMENT '작성자 식별자' AFTER task;

/*
   authors >  name, created_date, modified_date 컬럼에 신규 데이터 삽입
   schedules > authorName 중복 값 제외, created_date, modified_date 에 CURRENT_DATE 데이터를 넣기
             > 데이터 삽입 시점에 작성자 레코드의 생성/수정일을 알 수 없기 때문
*/
INSERT INTO authors (name, created_date, modified_date)
SELECT DISTINCT authorName, CURRENT_DATE, CURRENT_DATE
FROM schedules;

/*
   s.authorName = a.name 으로 JOIN
   s.author_id 에 a.id 값을 넣기
 */
UPDATE schedules s
JOIN authors a ON s.authorName = a.name
SET s.author_id = a.id
WHERE s.author_id IS NULL;

/*
   schedules > author_id 컬럼을 NOT NULL 로 변경
   외래 키(Foreign Key)를 author_id 컬럼으로 선언 > id 컬럼 참조
   authors > id 행 삭제 시 schedules id 참조 데이터가 있을 경우 삭제 X
   authors > id 값 변경 시 schedules id 값도 자동 업데이트
 */
ALTER TABLE schedules
    MODIFY author_id BIGINT NOT NULL COMMENT '작성자 식별자' AFTER task,
    ADD CONSTRAINT FK_schedule_author
        FOREIGN KEY (author_id) REFERENCES authors(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE;




