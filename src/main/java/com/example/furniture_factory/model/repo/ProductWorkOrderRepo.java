package com.example.furniture_factory.model.repo;

import com.example.furniture_factory.model.WorkOrder.ProductWorkOrder;
import org.springframework.data.repository.CrudRepository;

public interface ProductWorkOrderRepo extends CrudRepository<ProductWorkOrder,Long> {
}
