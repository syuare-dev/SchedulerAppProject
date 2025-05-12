package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.ScheduleRequestDto;
import com.example.schedulerapp.dto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 메서드 구현 목적 > InMemory 생성
    private Map<Long, Schedule> scheduleList = new HashMap<>();


    /**
     * 일정 생성 기능
     * @param requestDto task, authorName, password 가 담긴 객체를 매개변수로 하여 요청받도록 함
     * @return 요청받은 데이터로 객체를 생성 후 반환
     */

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        // 식별자(id) 1씩 증가
        long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(scheduleId, requestDto.getTask(), requestDto.getAuthorName(), requestDto.getPassword());

        // InMemory DB에 Schedule 저장
        scheduleList.put(scheduleId, schedule);

        return new ScheduleResponseDto(schedule);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {

        // init List + Map to List -> stream 활용
        List<ScheduleResponseDto> responseList = scheduleList.values()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ScheduleResponseDto findScheduleById (@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);

        return new ScheduleResponseDto(schedule);
    }

}
