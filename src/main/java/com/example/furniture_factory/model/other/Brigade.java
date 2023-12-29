package com.example.furniture_factory.model.other;

import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter
@Table(name = "brigade")
public class Brigade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "chief_id")
    private Employee chief;

    @OneToMany(mappedBy = "brigade")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Employee> employees=new HashSet<>();

    @OneToMany(mappedBy = "brigade")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<WorkOrder> workOrders=new HashSet<>();
}
