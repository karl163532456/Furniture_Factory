package com.example.furniture_factory.model.repo;


import com.example.furniture_factory.model.WorkOrder.WorkEmployee;
import org.springframework.data.repository.CrudRepository;

public interface WorkEmployeeRepo extends CrudRepository<WorkEmployee,Long> {
}
