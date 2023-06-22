package com.microserviceexample.demo.service;

import com.microserviceexample.demo.dto.PaymentRequest;
import com.microserviceexample.demo.dto.PaymentResponse;

public interface PaymentService {
	long doPayment(PaymentRequest paymentRequest);

	PaymentResponse getPaymentDetailsByOrderId(long orderId);
}