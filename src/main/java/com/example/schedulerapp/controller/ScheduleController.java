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

    /**
     * Controller 레이어 > 일정 생성 기능
     *
     * @param requestDto 클라이언트에서 요청한 일정 생성 정보가 담겨있는 DTO
     *                    - task: 할 일
     *                    - authorName: 작성자명
     *                    - password: 비밀번호
     * @return 생성된 ScheduleResponseDto 객체를 포함한 ResponseEntity
     *         - 상태코드 201 CREATED
     * 처리 순서:
     * 1) @RequestBody 로 전달된 ScheduleRequestDto 를 Service 레이어에 전달
     * 2) Service 의 CreatedSchedule 메서드가 Schedule Entity 저장 > DTO 로 변환
     * 3) 변환된 DTO 를 ResponseEntity 에 담아 201 CREATED 로 반환
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        return new ResponseEntity<>(scheduleService.createSchedule(requestDto), HttpStatus.CREATED);
    }

    /**
     * Controller 레이어 > 일정 조회(전체) 기능
     *
     * @return 저장된 모든 일정을 ScheduleResponseDto List 형태로 반환하는 ResponseEntity
     *         - 상태코드 200 OK
     * 처리 순서:
     * 1) Service 레이어의 findAllSchedules 메서드를 호출 > DTO 리스트 조회
     * 2) 조회된 DTO 리스트를 ResponseEntity 에 담아 200 OK로 반환
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {

        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    /**
     * 저장된 일정 조회(단건) 기능
     * @param id 식별자 Id를 활용 > 일정 조회
     * @return 조회 id가 없을 경우 > 404 NOT FOUND 상태 코드 반환
     *         조회 id가 있을 경우 > 해당 id 일정 데이터 + 200 OK 상태 코드 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById (@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id),HttpStatus.OK);
    }

    /**
     * 저장된 일정 수정(단건) 기능
     * @param id 식별자 id 활용 > 저장된 일정 조회
     * @param requestDto 수정 요청할 데이터
     * @return 저장된 일정이 없을 경우 > NPE 방지
     *         저장된 일정은 있지만 password 가 틀렸을 경우 > 401 UNAUTHORIZED 상태 코드 반환
     *         저장된 일정은 있지만 task || authorName 이 null 일 경우 > 400 BAD REQUEST 상태 코드 반환
     *         저장된 일정이 있고, 정상적인 요청 데이터일 경우 > 일정 수정 + 200 OK 상태 코드 반환
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateTaskOrAuthorName(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateTaskOrAuthorName(
                id,
                requestDto.getTask(),
                requestDto.getAuthorName(),
                requestDto.getPassword()),
                HttpStatus.OK);
    }

    /**
     * 저장된 일정 삭제 기능
     * @param id 삭제할 일정의 식별자(id)
     * @param requestDto 삭제 시 요청 데이터에 password 값 필요
     * @return scheduleList 의 Key 값에 id가 포함되어 있을 경우
     *          1) password 불일치: 401 UNAUTHORIZED HTTP 상태 코드 반환
     *          2) password 일치: 저장된 데이터(id) 삭제 + 200 OK HTTP 상태 코드 반환
     *         scheduleList 의 Key 값에 id가 포함되어 있지 않을 경우: 404 NOT FOUND HTTP 상태 코드 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        scheduleService.deleteSchedule(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
