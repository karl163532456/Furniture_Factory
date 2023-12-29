package com.example.furniture_factory.model.repo;

import com.example.furniture_factory.model.employee.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee,Long> {
    Optional<Employee> findByLogin(String login);

}
