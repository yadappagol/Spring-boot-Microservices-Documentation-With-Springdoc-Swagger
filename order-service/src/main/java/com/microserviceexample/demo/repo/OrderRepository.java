package com.microserviceexample.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microserviceexample.demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}