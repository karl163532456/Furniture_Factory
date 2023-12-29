package com.example.furniture_factory.model.repo;

import com.example.furniture_factory.model.other.Material;
import org.springframework.data.repository.CrudRepository;

public interface MaterialRepo extends CrudRepository<Material,Long> {
}
