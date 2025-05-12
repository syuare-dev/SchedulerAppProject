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

    /**
     * 일정 조회(전체) 기능
     * @return 일정이 저정된 HashMap 을 List 로 전체 조회
     *         저장된 일정 데이터가 없을 경우 빈 배열로 조회
     */

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {

        // init List + Map to List -> stream 활용
        List<ScheduleResponseDto> responseList = scheduleList.values()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);

    }

    /**
     * 일정 조회(단건) 기능
     * @param id 식별자 Id를 활용 > 일정 조회
     * @return 조회 id가 없을 경우 > 404 NOT FOUND 상태 코드 반환
     *         조회 id가 있을 경우 > 해당 id 일정 데이터 + 200 OK 상태 코드 반환
     */

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById (@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);

        if(schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ScheduleResponseDto(schedule),HttpStatus.OK);
    }



}
