package com.example.furniture_factory.model.WorkOrder;

import com.example.furniture_factory.model.other.Brigade;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter
@Table(name = "work_order")
public class WorkOrder {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private Date sqlDate;
    private Date waitTime;

    private boolean status = false;

    private java.sql.Time sqlTime;

    @ManyToOne
    @JoinColumn(name = "brigade_id")
    private Brigade brigade;

    @OneToMany(mappedBy = "workOrder")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<ProductWorkOrder> productWorkOrders=new HashSet<>();

    public WorkOrder() {
        sqlDate = new Date(new java.util.Date().getTime());
    }


    public boolean checkStatus(){
        if(this.productWorkOrders.stream().anyMatch(f -> !f.isStatus())){
            status=true;
        }
        return status;
    }
    public void addProductWorkOrder(ProductWorkOrder productWorkOrder){
        productWorkOrders.add(productWorkOrder);
        productWorkOrder.setWorkOrder(this);
    }

}

