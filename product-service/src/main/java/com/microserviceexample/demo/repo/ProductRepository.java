package com.microserviceexample.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microserviceexample.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}