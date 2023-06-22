package com.microserviceexample.demo.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.microserviceexample.demo.dto.PaymentRequest;
import com.microserviceexample.demo.dto.PaymentResponse;
import com.microserviceexample.demo.entity.TransactionDetails;
import com.microserviceexample.demo.exeption.PaymentServiceCustomException;
import com.microserviceexample.demo.repo.TransactionDetailsRepository;
import com.microserviceexample.demo.utils.PaymentMode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final TransactionDetailsRepository transactionDetailsRepository;

	@Override
	public long doPayment(PaymentRequest paymentRequest) {

		log.info("PaymentServiceImpl | doPayment is called");

		log.info("PaymentServiceImpl | doPayment | Recording Payment Details: {}", paymentRequest);

		TransactionDetails transactionDetails = TransactionDetails.builder().paymentDate(Instant.now())
				.paymentMode(paymentRequest.getPaymentMode().name()).paymentStatus("SUCCESS")
				.orderId(paymentRequest.getOrderId()).referenceNumber(paymentRequest.getReferenceNumber())
				.amount(paymentRequest.getAmount()).build();

		transactionDetails = transactionDetailsRepository.save(transactionDetails);

		log.info("Transaction Completed with Id: {}", transactionDetails.getId());

		return transactionDetails.getId();
	}

	@Override
	public PaymentResponse getPaymentDetailsByOrderId(long orderId) {

		log.info("PaymentServiceImpl | getPaymentDetailsByOrderId is called");

		log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | Getting payment details for the Order Id: {}",
				orderId);

		TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId)
				.orElseThrow(() -> new PaymentServiceCustomException("TransactionDetails with given id not found",
						"TRANSACTION_NOT_FOUND"));

		PaymentResponse paymentResponse = PaymentResponse.builder().paymentId(transactionDetails.getId())
				.paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
				.paymentDate(transactionDetails.getPaymentDate()).orderId(transactionDetails.getOrderId())
				.status(transactionDetails.getPaymentStatus()).amount(transactionDetails.getAmount()).build();

		log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | paymentResponse: {}", paymentResponse.toString());

		return paymentResponse;
	}
}