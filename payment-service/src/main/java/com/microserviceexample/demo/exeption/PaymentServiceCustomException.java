package com.microserviceexample.demo.exeption;

import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentServiceCustomException extends RuntimeException {

	private final String errorCode;

	public PaymentServiceCustomException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}