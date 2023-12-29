package com.example.furniture_factory.model.repo;

import com.example.furniture_factory.model.other.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product,Long> {
}
