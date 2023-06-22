package com.microserviceexample.demo.service;

import com.microserviceexample.demo.dto.ProductRequest;
import com.microserviceexample.demo.dto.ProductResponse;

public interface ProductService {

	long addProduct(ProductRequest productRequest);

	ProductResponse getProductById(long productId);

	void reduceQuantity(long productId, long quantity);

	public void deleteProductById(long productId);
}