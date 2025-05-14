package com.example.schedulerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 페이지네이션 처리된 결과를 보여주는 DTO
 * - schedules: 페이지에 저장된 일정
 * - page: 페이지 번호
 * - size: 페이지 크기
 * - totalSchedules: 전체 일정 수
 * - totalPages: 전체 페이지 수
 */

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> schedules;
    private Integer page;
    private Integer size;
    private Long totalSchedules;
    private Integer totalPages;
}
