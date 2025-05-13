package com.example.schedulerapp.repository;

import com.example.schedulerapp.entity.Schedule;

public interface ScheduleRepository {

    Schedule createdSchedule(Schedule schedule);
}
