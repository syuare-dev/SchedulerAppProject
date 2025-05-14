package com.example.schedulerapp.dto;

import lombok.Getter;

/**
 * 클라이언트가 서버로 보낼 데이터
 */

@Getter
public class ScheduleRequestDto {

    private String task;
    private Long authorId;
    private String authorName;
    private String password;


}
