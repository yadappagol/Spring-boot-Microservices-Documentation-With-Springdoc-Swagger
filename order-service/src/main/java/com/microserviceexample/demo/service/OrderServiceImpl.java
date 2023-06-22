package com.microserviceexample.demo.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microserviceexample.demo.dto.OrderRequest;
import com.microserviceexample.demo.dto.OrderResponse;
import com.microserviceexample.demo.dto.PaymentRequest;
import com.microserviceexample.demo.dto.PaymentResponse;
import com.microserviceexample.demo.dto.ProductResponse;
import com.microserviceexample.demo.entity.Order;
import com.microserviceexample.demo.exception.OrderServiceCustomException;
import com.microserviceexample.demo.repo.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final RestTemplate restTemplate;

	@Override
	public long placeOrder(OrderRequest orderRequest) {

		log.info("OrderServiceImpl | placeOrder is called");

		// Order Entity -> Save the data with Status Order Created
		// Product Service - Block Products (Reduce the Quantity)
		// Payment Service -> Payments -> Success-> COMPLETE, Else
		// CANCELLED

		log.info("OrderServiceImpl | placeOrder | Placing Order Request orderRequest : " + orderRequest.toString());

		log.info("OrderServiceImpl | placeOrder | Creating Order with Status CREATED");
		Order order = Order.builder().amount(orderRequest.getTotalAmount()).orderStatus("CREATED")
				.productId(orderRequest.getProductId()).orderDate(Instant.now()).quantity(orderRequest.getQuantity())
				.build();

		order = orderRepository.save(order);

		log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");

		PaymentRequest paymentRequest = PaymentRequest.builder().orderId(order.getId())
				.paymentMode(orderRequest.getPaymentMode()).amount(orderRequest.getTotalAmount()).build();

		String orderStatus = null;

		try {
			log.info("OrderServiceImpl | placeOrder | Payment done Successfully. Changing the Oder status to PLACED");
			orderStatus = "PLACED";
		} catch (Exception e) {
			log.error(
					"OrderServiceImpl | placeOrder | Error occurred in payment. Changing order status to PAYMENT_FAILED");
			orderStatus = "PAYMENT_FAILED";
		}

		order.setOrderStatus(orderStatus);

		orderRepository.save(order);

		log.info("OrderServiceImpl | placeOrder | Order Places successfully with Order Id: {}", order.getId());

		return order.getId();
	}

	@Override
	public OrderResponse getOrderDetails(long orderId) {

		log.info("OrderServiceImpl | getOrderDetails | Get order details for Order Id : {}", orderId);

		Order order = orderRepository.findById(orderId).orElseThrow(
				() -> new OrderServiceCustomException("Order not found for the order Id:" + orderId, "NOT_FOUND", 404));

		log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}",
				order.getProductId());
		ProductResponse productResponse = restTemplate
				.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class);

		log.info("OrderServiceImpl | getOrderDetails | Getting payment information form the payment Service");
		PaymentResponse paymentResponse = restTemplate
				.getForObject("http://PAYMENT-SERVICE/payment/order/" + order.getId(), PaymentResponse.class);

		OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
				.productName(productResponse.getProductName()).productId(productResponse.getProductId()).build();

		OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
				.paymentId(paymentResponse.getPaymentId()).paymentStatus(paymentResponse.getStatus())
				.paymentDate(paymentResponse.getPaymentDate()).paymentMode(paymentResponse.getPaymentMode()).build();

		OrderResponse orderResponse = OrderResponse.builder().orderId(order.getId()).orderStatus(order.getOrderStatus())
				.amount(order.getAmount()).orderDate(order.getOrderDate()).productDetails(productDetails)
				.paymentDetails(paymentDetails).build();

		log.info("OrderServiceImpl | getOrderDetails | orderResponse : " + orderResponse.toString());

		return orderResponse;
	}
}