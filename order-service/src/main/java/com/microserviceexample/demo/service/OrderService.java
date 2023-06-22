package com.microserviceexample.demo.service;

import com.microserviceexample.demo.dto.OrderRequest;
import com.microserviceexample.demo.dto.OrderResponse;

public interface OrderService {
	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);
}