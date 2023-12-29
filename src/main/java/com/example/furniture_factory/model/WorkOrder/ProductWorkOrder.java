package com.example.furniture_factory.model.WorkOrder;

import com.example.furniture_factory.model.other.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Entity
@ToString
@Setter
@Getter

public class ProductWorkOrder{
    @Id
    @GeneratedValue
    private long id;
    private boolean status=false;
    private java.sql.Date sqlDate;
    private int count;

    @ManyToOne
    @JoinColumn(name="workOrder_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


    @OneToMany(mappedBy = "productWorkOrder")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<WorkEmployee> workEmployees=new HashSet<>();

    public ProductWorkOrder addProduct(Product product,int count) {
        this.product = product;
        this.count=count;
        this.workEmployees.addAll(
                product.getWorks()
                        .stream()
                        .map(f->new WorkEmployee(f))
                        .toList());
        this.workEmployees.forEach(f->f.setProductWorkOrder(this));
        return this;
    }

}
