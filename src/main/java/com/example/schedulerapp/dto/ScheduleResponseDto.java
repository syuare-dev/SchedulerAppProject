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
}
