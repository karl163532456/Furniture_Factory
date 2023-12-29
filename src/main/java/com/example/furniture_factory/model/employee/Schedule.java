package com.example.furniture_factory.model.employee;

import com.example.furniture_factory.model.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.sql.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private boolean isDay;

    @ManyToMany(mappedBy="schedules")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Employee> employees;

}
