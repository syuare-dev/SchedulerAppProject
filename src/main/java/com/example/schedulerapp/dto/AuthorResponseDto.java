package com.example.schedulerapp.dto;

import com.example.schedulerapp.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
        this.createdDate = author.getCreatedDate();
        this.modifiedDate = author.getModifiedDate();
    }

}
