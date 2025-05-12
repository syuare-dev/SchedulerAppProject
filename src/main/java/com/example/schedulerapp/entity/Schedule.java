package com.example.schedulerapp.entity;

import com.example.schedulerapp.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Schedule {

    private long id;
    private String task;
    private String authorName;
    private String password;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    /**
     * 실제 DB에 저장되는 데이터 모델
     * @param id 식별자 ID
     * @param task 할일
     * @param authorName 작성자
     * @param password 비밀번호
     * createDate 생성일 (자동 생성)
     * modifiedDate 수정일 (자동 생성)
     */

    public Schedule(long id, String task, String authorName, String password) {
        this.id = id;
        this.task = task;
        this.authorName = authorName;
        this.password = password;

        LocalDate testCreatedDate = LocalDate.of(2025, 5, 7);
        this.createdDate = testCreatedDate;
        this.modifiedDate = testCreatedDate;
    }

    public void updateSchedule(ScheduleRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.authorName = requestDto.getAuthorName();
        this.modifiedDate = LocalDate.now();
    }


}
