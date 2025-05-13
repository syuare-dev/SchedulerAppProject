package com.example.schedulerapp.repository;

import com.example.schedulerapp.dto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    Schedule createdSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Schedule findScheduleById(Long id);

    void deleteSchedule(Long id);
}
