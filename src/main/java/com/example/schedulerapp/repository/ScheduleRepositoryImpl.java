package com.example.schedulerapp.repository;


import com.example.schedulerapp.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    // 메서드 구현 목적 > InMemory 생성
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @Override
    public Schedule createdSchedule(Schedule schedule) {

        // 식별자(id) 1씩 증가
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;
        schedule.setId(scheduleId);

        // InMemory DB에 Schedule 저장
        scheduleList.put(scheduleId, schedule);

        return schedule;
    }
}
