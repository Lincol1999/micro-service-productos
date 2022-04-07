package com.group.microserviceproductos.repository;

import com.group.microserviceproductos.entity.Category;
import com.group.microserviceproductos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRespository extends JpaRepository<Product, Long> {

    public List<Product> findByCategory(Category category);
}
