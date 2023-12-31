package com.microserviceexample.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceexample.demo.dto.OrderRequest;
import com.microserviceexample.demo.dto.OrderResponse;
import com.microserviceexample.demo.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order/")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {

		log.info("OrderController | placeOrder is called");

		log.info("OrderController | placeOrder | orderRequest: {}", orderRequest.toString());

		long orderId = orderService.placeOrder(orderRequest);
		log.info("Order Id: {}", orderId);
		return new ResponseEntity<>(orderId, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {

		log.info("OrderController | getOrderDetails is called");

		OrderResponse orderResponse = orderService.getOrderDetails(orderId);

		log.info("OrderController | getOrderDetails | orderResponse : " + orderResponse.toString());

		return new ResponseEntity<>(orderResponse, HttpStatus.OK);
	}
}