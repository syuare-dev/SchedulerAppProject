package com.example.schedulerapp.service;

import com.example.schedulerapp.dto.PageResponseDto;
import com.example.schedulerapp.dto.ScheduleRequestDto;
import com.example.schedulerapp.dto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    /**
     * Service 레이어 > 일정 생성 기능
     *
     * @param requestDto 클라이언트로 부터 요청받은 데이터를 담고 있는 DTO
     * @return Repository 레이어에서 저장된 Schedule Entity 기반으로 생성된 ScheduleResponseDto
     * 처리 순서:
     * 1) 요청받은 데이터를 이용하여 Schedule Entity 객체 생성
     * 2) Repository 레이어의 createdSchedule 메서드 호출 > InMemory DB에 저장
     * 3) 저장된 Schedule Entity 를 ScheduleResponseDto 로 변환 후 반환(return)
     */
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto.getTask(), requestDto.getAuthorName(), requestDto.getPassword());

        return scheduleRepository.createdSchedule(schedule);

    }

    /**
     * Service 레이어 > 일정 조회(전체) 기능
     *
     * @return 저장된 모든 일정을 ScheduleResponseDto 형태의 List 로 반환*
     * 처리 순서:
     * 1) Repository 레이어의 findAllSchedules 메서드 호출
     *    > ScheduleResponseDTO 리스트 조회
     *    > Repository 내부에서 Schedule Entity 를 DTO 로 변환
     * 2) 조회된 DTO 리스트를 반환
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        return scheduleRepository.findAllSchedules();
    }

    @Override
    public PageResponseDto<ScheduleResponseDto> findSchedulesPagination(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<ScheduleResponseDto> schedules = scheduleRepository.findSchedulesPagination(offset, size);
        Long total = scheduleRepository.countSchedules();
        Integer totalPages = (int) Math.ceil((double) total / size);

        return new PageResponseDto<>(schedules, page, size, total, totalPages);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIDOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateTaskOrAuthorName(Long id, String task, String authorName, String password) {

        Schedule schedule = scheduleRepository.findScheduleByIDOrElseThrow(id);

        // task 혹은 authorName 값이 null 일 경우 예외 처리
        if (task == null || authorName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task or authorName is a required value");
        }

        // password 가 틀렸을 경우 예외 처리
        if (password == null || !password.equals(schedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password do not match");
        }

        int updateRow = scheduleRepository.updateTaskOrAuthorName(id, task, authorName, password);

        // NPE 방지
        if(updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule updated = scheduleRepository.findScheduleByIDOrElseThrow(id);

        return new ScheduleResponseDto(updated);
    }

    @Override
    public void deleteSchedule(Long id, String password) {

        Schedule schedule = scheduleRepository.findScheduleByIDOrElseThrow(id);

        // password 가 틀렸을 경우 예외 처리
        if (password == null || !password.equals(schedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password do not match");
        }

        int deleteRow = scheduleRepository.deleteSchedule(id, password);

        // NPE 방지
        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
