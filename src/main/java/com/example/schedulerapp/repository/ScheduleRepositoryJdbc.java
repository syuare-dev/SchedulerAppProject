package com.example.schedulerapp.repository;

import com.example.schedulerapp.dto.ScheduleResponseDto;
import com.example.schedulerapp.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryJdbc implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryJdbc (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public ScheduleResponseDto createdSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("authorName",schedule.getAuthorName());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdDate", schedule.getCreatedDate());
        parameters.put("modifiedDate", schedule.getModifiedDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getAuthorName(),schedule.getCreatedDate(), schedule.getModifiedDate());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedules", scheduleRowMapper());
    }

    @Override
    public Schedule findScheduleByIDOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedules where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public int updateTaskOrAuthorName(Long id, String task, String authorName, String password) {
        return jdbcTemplate.update("UPDATE schedules set task = ?, authorName = ?, modifiedDate = ? where id = ? AND password = ?", task, authorName, LocalDate.now(), id, password);
    }

    @Override
    public int deleteSchedule(Long id, String password) {
        return jdbcTemplate.update("delete from schedules where id =? AND password = ?", id, password);

    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getString("authorName"),
                rs.getDate("createdDate").toLocalDate(),
                rs.getDate("modifiedDate").toLocalDate()
        );
    }


    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getString("authorName"),
                rs.getString("password"),
                rs.getDate("createdDate").toLocalDate(),
                rs.getDate("modifiedDate").toLocalDate()
        );
    }
}
