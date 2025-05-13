package com.example.schedulerapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String task;
    private Long authorId;
    private String authorName;
    private String password;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    /**
     * 실제 DB에 저장되는 데이터 모델
     * @param task 할일
     * @param authorName 작성자
     * @param password 비밀번호
     * createDate 생성일 (자동 생성)
     * modifiedDate 수정일 (자동 생성)
     */

    public Schedule(String task, String authorName, String password) {
        this.task = task;
        this.authorName = authorName;
        this.password = password;

        LocalDate testCreatedDate = LocalDate.of(2025, 5, 7);
        this.createdDate = testCreatedDate;
        this.modifiedDate = testCreatedDate;
    }

    public void updateSchedule(String task, String authorName) {
        this.task = task;
        this.authorName = authorName;
        this.modifiedDate = LocalDate.now();
    }

}
