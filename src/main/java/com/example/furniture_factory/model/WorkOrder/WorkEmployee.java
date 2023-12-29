
package com.example.furniture_factory.model.WorkOrder;

        import com.example.furniture_factory.model.employee.Employee;
        import com.example.furniture_factory.model.other.Product;
        import com.example.furniture_factory.model.other.Work;
        import jakarta.persistence.*;
        import lombok.*;

        import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter

public class WorkEmployee{
    @Id
    @GeneratedValue
    private long id;
    private java.sql.Date date;
    private boolean status=false;

    @ManyToOne
    @JoinColumn(name="work_id")
    private Work work;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="productWorkOrder_id")
    private ProductWorkOrder productWorkOrder;

    public WorkEmployee(Work work) {
        this.date=new Date(new java.util.Date().getTime());
        this.work = work;
    }
}