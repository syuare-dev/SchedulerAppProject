package com.example.schedulerapp.entity;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Author {

    private Long id;
    private String name;
    private String email;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
}
