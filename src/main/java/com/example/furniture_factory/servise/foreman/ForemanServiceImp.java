package com.example.furniture_factory.servise.foreman;

import com.example.furniture_factory.model.WorkOrder.ProductWorkOrder;
import com.example.furniture_factory.model.WorkOrder.WorkEmployee;
import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;
import com.example.furniture_factory.model.employee.Schedule;
import com.example.furniture_factory.model.other.Brigade;
import com.example.furniture_factory.model.repo.*;
import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.sql.Time;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@AllArgsConstructor
@Service
public class ForemanServiceImp implements ForemanService {
    EmployeeRepo employeeRepo;

    BrigadeRepo brigadeRepo;

    MaterialRepo materialRepo;

    ProductRepo productRepo;
    WorkEmployeeRepo workEmployeeRepo;
    ProductWorkOrderRepo productWorkOrderRepo;
    WorkRepo workRepo;

    WorkOrderRepo workOrderRepo;

    ScheduleRepo scheduleRepo;
    @Override
    public Brigade brigadeByAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return employeeRepo.findByLogin(auth.getName())
                .orElseThrow()
                .getBrigade();
    }
    @Override
    public WorkEmployee choseEmployeeForWork(Long idWork, Long idEmployee){
        WorkEmployee workEmployee=workEmployeeRepo.findById(idWork).orElseThrow();
        workEmployee.setStatus(true);
        workEmployee.setEmployee(employeeRepo.findById(idEmployee).orElseThrow());
        orderСompleted(workEmployee.getProductWorkOrder().getWorkOrder());
        return workEmployeeRepo.save(workEmployee);
    }
    @Override
    public WorkOrder orderСompleted(WorkOrder workOrder){
        workOrder.setStatus(workOrder
                .getProductWorkOrders()
                .stream().
                map(this::productOrderСompleted)
                .allMatch(ProductWorkOrder::isStatus));
        return workOrderRepo.save(workOrder);
    }
    @Override
    public ProductWorkOrder productOrderСompleted(ProductWorkOrder productWorkOrder){
        productWorkOrder
                .setStatus(productWorkOrder
                .getWorkEmployees()
                .stream()
                .allMatch(WorkEmployee::isStatus));
        if(productWorkOrder.isStatus())
            productWorkOrder.setSqlDate(new java.sql.Date(new Date().getTime()));
        return productWorkOrderRepo.save(productWorkOrder);
    }
    @Override
    public WorkEmployee foremanEmplWork(Long idWork){
        WorkEmployee workEmployee=workEmployeeRepo.findById(idWork).orElseThrow();
        workEmployee.setEmployee(null);
        workEmployee.setStatus(false);
        workEmployee.setDate(new java.sql.Date(new Date().getTime()));
        return workEmployeeRepo.save(workEmployee);
    }

    @Override
    public void deleteSchedule(){
        scheduleRepo.deleteAll();
    }
    @Override
    public void changeSchedule(Long idEmployee,Long idSchedule){
        Employee employee=employeeRepo.findById(idEmployee).orElseThrow();
        Schedule schedule=scheduleRepo.findById(idSchedule).orElseThrow();
        if(schedule.getEmployees().contains(employee)){
            employee.getSchedules().remove(schedule);//не уверен
        }
        else{
            Set<Schedule> scheduleSet=employee.getSchedules();
            scheduleSet.add(schedule);
            employee.setSchedules(scheduleSet);
        }
        employeeRepo.save(employee);
    }
    @Override
    public void createWeek(Date date){
        List<Schedule> scheduleList=new ArrayList<>();
        scheduleRepo.findAll().forEach(scheduleList::add);
        System.out.println("start");
        if(scheduleList.stream().noneMatch(f -> f.getDate().after(date) )){
            for (int i=0;i<7;i++){
                System.out.println("begin");
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, i);

                Schedule day=new Schedule();
                day.setDay(true);
                day.setDate(new java.sql.Date(c.getTime().getTime()));
                scheduleRepo.save(day);

                Schedule night=new Schedule();
                night.setDay(false);
                night.setDate(new java.sql.Date(c.getTime().getTime()));
                scheduleRepo.save(night);
            }
        };
    }
    @Override
    public List<Schedule> getWeek(Date date){
        createWeek(date);
        List<Schedule> scheduleList=new ArrayList<>();
        scheduleRepo.findAll().forEach(scheduleList::add);
        return scheduleList.stream()
                .filter(f->f.getDate().after(date))
                .toList();
    }
    public int earned(Employee employee){
        return employee.getWorkEmployees().stream()
                .filter(f->f.getDate()!=null &&
                        f.getDate().after(java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1))))
                .mapToInt(f->f.getWork().getPrice()*f.getProductWorkOrder().getCount())
                .sum();
    }
    public WorkOrder addTime(Long idWorkOrder, Time time){
        WorkOrder workOrder=workOrderRepo.findById(idWorkOrder).orElseThrow();
        workOrder.setSqlTime(time);
        return workOrderRepo.save(workOrder);
    }


}
