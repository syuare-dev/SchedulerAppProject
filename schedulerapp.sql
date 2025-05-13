USE schedulerapp_test;

CREATE TABLE IF NOT EXISTS schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    task VARCHAR(255) NOT NULL COMMENT '할 일',
    authorName VARCHAR(100) NOT NULL COMMENT '작성자명',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    createdDate DATE NOT NULL COMMENT '일정 생성일',
    modifiedDate DATE NOT NULL COMMENT '일정 수정일'
)