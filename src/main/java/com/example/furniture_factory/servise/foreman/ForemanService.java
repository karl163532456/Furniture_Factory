package com.example.furniture_factory.servise.foreman;

import com.example.furniture_factory.model.WorkOrder.ProductWorkOrder;
import com.example.furniture_factory.model.WorkOrder.WorkEmployee;
import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;
import com.example.furniture_factory.model.employee.Schedule;
import com.example.furniture_factory.model.other.Brigade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Time;
import java.util.*;

public interface ForemanService {

    Brigade brigadeByAuth();

    WorkEmployee choseEmployeeForWork(Long idWork, Long idEmployee);
    WorkEmployee foremanEmplWork(Long idWork);
    public void createWeek(Date date);
    public void deleteSchedule();
    public void changeSchedule(Long idEmployee,Long idSchedule);
    public List<Schedule> getWeek(Date date);
    public WorkOrder orderСompleted(WorkOrder workOrder);
    public ProductWorkOrder productOrderСompleted(ProductWorkOrder productWorkOrder);
    public int earned(Employee employee);
    public WorkOrder addTime(Long idWorkOrder, Time time);
}
