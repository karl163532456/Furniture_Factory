package com.example.furniture_factory.model.repo;

import com.example.furniture_factory.model.employee.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepo extends CrudRepository<Schedule,Long> {
}
