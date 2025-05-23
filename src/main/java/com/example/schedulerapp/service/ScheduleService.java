package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.PageResponseDto;
import com.example.schedulerapp.dto.ScheduleRequestDto;
import com.example.schedulerapp.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules();

    PageResponseDto<ScheduleResponseDto> findSchedulesPagination(Integer page, Integer size);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateTaskOrAuthorName(Long id, String task, String authorName, String password);

    void deleteSchedule(Long id, String password);
}
