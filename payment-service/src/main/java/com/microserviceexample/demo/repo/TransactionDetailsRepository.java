package com.microserviceexample.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microserviceexample.demo.entity.TransactionDetails;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

	Optional<TransactionDetails> findByOrderId(long orderId);
}