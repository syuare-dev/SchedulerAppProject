package com.example.schedulerapp.entity;

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

    public Schedule(String task, String authorName) {
        this.task = task;
        this.authorName = authorName;
    }


}
