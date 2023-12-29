package com.example.furniture_factory.model.repo;

import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import org.springframework.data.repository.CrudRepository;

public interface WorkOrderRepo extends CrudRepository<WorkOrder,Long> {
    Iterable<WorkOrder> findAllByStatus(boolean status);
}
