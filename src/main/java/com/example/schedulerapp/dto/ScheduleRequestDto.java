package com.example.schedulerapp.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleRequestDto {

    private String task;
    private String authorName;
    private String password;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
}
