package com.example.furniture_factory.controllers;

import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;
import com.example.furniture_factory.model.employee.RoleEnum;
import com.example.furniture_factory.model.repo.*;
import com.example.furniture_factory.model.other.Brigade;
import com.example.furniture_factory.servise.foreman.ForemanService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Date;


@Controller
public class HomeController {
    @Autowired
    ForemanService foremanService;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    WorkOrderRepo workOrderRepo;
        //Вход
    @GetMapping("/login")
    String login(Model model){
        return "Login";
    }
    @GetMapping("/")
    String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee=employeeRepo.findByLogin(auth.getName()).orElse(null);
        if (employee==null)
            return "Login";
        else{
            RoleEnum roleEnum=employee.getRole();
            if(roleEnum==RoleEnum.ADMIN)
                return "redirect:/admin/home";
            else if(roleEnum==RoleEnum.BRIGADIER)
                return "redirect:/User";
            else
                return "redirect:/Brigadier";
        }
    }
    //Работник
    @GetMapping("/User")
    String user(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee=employeeRepo.findByLogin(auth.getName()).orElseThrow();
        model.addAttribute("employee",employee);
        model.addAttribute("permission",false);
        model.addAttribute("earned",foremanService.earned(employee));
        model.addAttribute("schedule",foremanService.getWeek(new Date()));
        model.addAttribute("employeeSchedule",employee.getSchedules());
        return "Employee";

    }

// Бригадир
    @GetMapping("/Brigadier")
    String foreman(Model model){
        Brigade b= foremanService.brigadeByAuth();
        model.addAttribute("brigade",b);
        model.addAttribute("WorkOrders",b.getWorkOrders().stream()
                .map(f->foremanService.orderСompleted(f))
                .filter(f->!f.isStatus()).toList());
        return "brigadier/Brigadier";
    }
//    //Бригадир смотрит работника
    @PostMapping("/Brigadier/employee")
    String choseEmployee(@RequestParam("id") Long id,Model model){
        Employee employee=employeeRepo.findById(id).orElseThrow();
        model.addAttribute("employee",employee);
        model.addAttribute("permission",true);
        model.addAttribute("earned",foremanService.earned(employee));
        model.addAttribute("schedule",foremanService.getWeek(new Date()));
        model.addAttribute("employeeSchedule",employee.getSchedules());
        return "Employee";
    }

    //Бригадир Выбирает работу
    @PostMapping("/Brigadier/Work")
    String choseWork(@RequestParam("idWork") long idWork, Model model){
        Brigade b= foremanService.brigadeByAuth();
        model.addAttribute("brigade",b);
        model.addAttribute("idWork",idWork);
        return "brigadier/ChoseEmployeeForWork";
    }
    //Бригадир назначает сотрудника
    @GetMapping("/Brigadier/Work/EmployeeFor/{idWork}/{idEmployee}")
    String choseEmployeeForWork(@PathVariable("idWork") long idWork, @PathVariable("idEmployee") Long idEmployee,Model model){
        model.addAttribute("employee",employeeRepo.findById(idEmployee).orElseThrow());
        model.addAttribute("permission",true);
        foremanService.choseEmployeeForWork(idWork,idEmployee);
        return "redirect:/Brigadier";
    }

    //    //бригадир убирает работу сотрудника
    @PostMapping("/Brigadier/employee/Work/{idWork}")
    String foremanEmplWork(@PathVariable("idWork") Long idWork,Model model){
        foremanService.foremanEmplWork(idWork);
        return "redirect:/Brigadier";
    }
    //Бригадир Меняет расписание сотруднику
    @GetMapping("/Brigadier/Employee/Schedule/{idSchedule}/{idEmployee}")
    String choseScheduleEmployee(@PathVariable("idSchedule") long idSchedule, @PathVariable("idEmployee") Long idEmployee,Model model){
        foremanService.changeSchedule(idEmployee,idSchedule);

        Employee employee=employeeRepo.findById(idEmployee).orElseThrow();
        model.addAttribute("employee",employee);
        model.addAttribute("permission",true);
        model.addAttribute("earned",foremanService.earned(employee));
        model.addAttribute("schedule",foremanService.getWeek(new Date()));
        model.addAttribute("employeeSchedule",employee.getSchedules());


        return "Employee";
    }
    @GetMapping("/Brigadier/AddTime/{id}")
    String foremanAddTime(@PathVariable("id") Long idWorkOrder,@RequestParam("time") String time,Model model){
        foremanService.addTime(idWorkOrder,Time.valueOf( time+":00"));
        WorkOrder workOrder=workOrderRepo.findById(idWorkOrder).orElseThrow();
        workOrder.setWaitTime(new java.sql.Date(new java.util.Date().getTime()));
        return "redirect:/Brigadier";
    }
//
//    @Autowired
//    EmployeeRepo employeeRepo;
//    @Autowired
//    BrigadeRepo brigadeRepo;
//    @Autowired
//    MaterialRepo materialRepo;
//    @Autowired
//    ProductRepo productRepo;
//
//    @Autowired
//    WorkEmployeeRepo workEmployeeRepo;
//    @Autowired
//    WorkRepo workRepo;
//    @Autowired
//    WorkOrderRepo workOrderRepo;
//
//    @Autowired
//    AdminService adminService;
//    @GetMapping("/")
//    String home(Model model){
//
//
//        return "Home";
//    }
//    //Вход
//    @GetMapping("/login")
//    String login(Model model){
//        return "login";
//    }
//    //Работник
////    @GetMapping("/User")
////    String user(Model model){
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        model.addAttribute("employee",employeeRepo.findByLogin(auth.getName()).orElseThrow());
////        return "Employee";
////    }
////   // Бригадир
//    @GetMapping("/Brigadier")
//    String foreman(Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Brigade b=employeeRepo.findByLogin(auth.getName())
//                .orElseThrow()
//                .getBrigade();
//        b.getWorkOrders().forEach(f-> System.out.println(f.getId()));
//        model.addAttribute("brigade",b);
//        model.addAttribute("WorkOrders",b.getWorkOrders().stream().filter(f->!f.isStatus()).toList());
//        return "brigadier/Brigadier";
//    }
////    //Бригадир смотрит работника
//    @PostMapping("/Brigadier/employee")
//    String choseEmployee(@RequestParam("id") Long id,Model model){
//        model.addAttribute("employee",employeeRepo.findById(id).orElseThrow());
//        model.addAttribute("permission",true);
//        return "Employee";
//    }
//
//    //Бригадир Выбирает работу
//    @PostMapping("/Brigadier/Work")
//    String choseWork(@RequestParam("idWork") long idWork, Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Brigade b=employeeRepo.findByLogin(auth.getName())
//                .orElseThrow()
//                .getBrigade();
//
//        model.addAttribute("brigade",b);
//        model.addAttribute("idWork",idWork);
//        return "brigadier/ChoseEmployeeForWork";
//    }
//    //Бригадир назначает сотрудника
//    @GetMapping("/Brigadier/Work/EmployeeFor/{idWork}/{idEmployee}")
//    String choseEmployeeForWork(@PathVariable("idWork") long idWork, @PathVariable("idEmployee") Long idEmployee){
//        WorkEmployee workEmployee=workEmployeeRepo.findById(idWork).orElseThrow();
//        workEmployee.setStatus(true);
//        workEmployee.setEmployee(employeeRepo.findById(idEmployee).orElseThrow());
//        workEmployeeRepo.save(workEmployee);
//        return "redirect:/Brigadier";
//    }
//
//    //    //бригадир убирает работу сотрудника
//    @PostMapping("/Brigadier/employee/Work/{idWork}")
//    String foremanEmplWork(@RequestParam("idWork") Long idWork,Model model){
//        WorkEmployee workEmployee=workEmployeeRepo.findById(idWork).orElseThrow();
//        workEmployee.setEmployee(null);
//        workEmployee.setStatus(false);
//
//        //редирект
//        model.addAttribute("employee",workEmployee.getEmployee());
//        model.addAttribute("permission",true);
//        return "Employee";
//    }
//    //Бригадир Меняет расписание сотруднику
//
//

}
