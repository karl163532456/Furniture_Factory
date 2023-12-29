package com.example.furniture_factory.model.other;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter
@Table(name = "work")
public class Work {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int price;
    @ManyToMany(mappedBy = "works")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Product> products;

//    @Setter(AccessLevel.NONE)
//    @OneToMany(mappedBy = "work")
//    private Set<WorkEmployee> workEmployees;



//    public void setWorkEmployees(WorkEmployee workEmployees) {
//        this.workEmployees.add(workEmployees);
//    }
}
