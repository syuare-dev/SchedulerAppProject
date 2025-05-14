package com.example.schedulerapp.dto;

import com.example.schedulerapp.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private long id;
    private String task;
    private AuthorResponseDto author;
    private String authorName;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public ScheduleResponseDto (Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.authorName = schedule.getAuthorName();
        this.createdDate = schedule.getCreatedDate();
        this.modifiedDate = schedule.getModifiedDate();
    }

    public ScheduleResponseDto (Schedule schedule, AuthorResponseDto author) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.author = author;
        this.createdDate = schedule.getCreatedDate();
        this.modifiedDate = schedule.getModifiedDate();
    }

    public ScheduleResponseDto (Long id, String task, String authorName, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.task = task;
        this.authorName = authorName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public ScheduleResponseDto (Long id, String task, AuthorResponseDto author, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
