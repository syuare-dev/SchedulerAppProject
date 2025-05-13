package com.example.schedulerapp.repository;


import com.example.schedulerapp.dto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    // 메서드 구현 목적 > InMemory 생성
    private final Map<Long, Schedule> scheduleList = new HashMap<>();


    /**
     * Repository 레이어 > 일정 생성 기능
     *
     * @param schedule DB에 저장할 Schedule Entity 객체
     *                  - 객체 내 속성 중 id는 해당 메서드에서 설정
     * @return id 설정 + Schedule Entity 객체를 In-Memory 에 저장
     * 처리 순서:
     * 1) id 계산
     *      - scheduleList 가 비어 있을 경우 id를 1로,
     *      - 아닐 경우 기존 키 최대값 + 1로 계산
     * 2) 계산된 id를 Schedule Entity 에 설정
     * 3) scheduleList Map 에 (id, schedule) 으로 저장
     * 4) 저장된 Schedule Entity 를 반환
     */
    @Override
    public Schedule createdSchedule(Schedule schedule) {

        // 식별자(id) 1씩 증가
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;
        schedule.setId(scheduleId);

        // InMemory DB에 Schedule 저장
        scheduleList.put(scheduleId, schedule);

        return schedule;
    }


    /**
     * Repository 레이어 > 일정 조회(전체) 기능
     *
     * @return In-Memory DB에 저장된 모든 Schedule Entity 를 ScheduleResponseDto 에 변환하여 반환
     * 처리 순서:
     * 1) scheduleList 의 values() 컬렉션을 스트림으로 변환
     * 2) 각 Schedule 객체를 ScheduleResponseDto 생성자로 매핑
     * 3) stream > toList()를 통해 변환된 DTO 리스트 생성 및 반환
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        // init List + Map to List -> stream 활용
        return scheduleList.values()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Override
    public Schedule findScheduleById(Long id) {
        return scheduleList.get(id);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleList.remove(id);
    }
}
