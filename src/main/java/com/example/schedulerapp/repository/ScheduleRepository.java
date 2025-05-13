package com.example.schedulerapp.repository;

import com.example.schedulerapp.dto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto createdSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Schedule findScheduleByIDOrElseThrow(Long id);

    int updateTaskOrAuthorName(Long id, String task, String authorName, String password);

    int deleteSchedule(Long id, String password);
}
