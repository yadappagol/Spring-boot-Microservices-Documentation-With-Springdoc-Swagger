package com.microserviceexample.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceexample.demo.dto.PaymentRequest;
import com.microserviceexample.demo.dto.PaymentResponse;
import com.microserviceexample.demo.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment/")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/")
	public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {

		log.info("PaymentController | doPayment is called");

		log.info("PaymentController | doPayment | paymentRequest : " + paymentRequest.toString());

		return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId) {

		log.info("PaymentController | doPayment is called");

		log.info("PaymentController | doPayment | orderId : " + orderId);

		return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(orderId), HttpStatus.OK);
	}
}