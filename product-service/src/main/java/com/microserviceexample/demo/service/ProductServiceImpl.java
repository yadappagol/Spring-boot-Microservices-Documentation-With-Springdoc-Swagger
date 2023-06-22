package com.microserviceexample.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.microserviceexample.demo.dto.ProductRequest;
import com.microserviceexample.demo.dto.ProductResponse;
import com.microserviceexample.demo.entity.Product;
import com.microserviceexample.demo.exception.ProductServiceCustomException;
import com.microserviceexample.demo.repo.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public long addProduct(ProductRequest productRequest) {
		log.info("ProductServiceImpl | addProduct is called");

		Product product = Product.builder().productName(productRequest.getName()).quantity(productRequest.getQuantity())
				.price(productRequest.getPrice()).build();

		product = productRepository.save(product);

		log.info("ProductServiceImpl | addProduct | Product Created");
		log.info("ProductServiceImpl | addProduct | Product Id : " + product.getProductId());
		return product.getProductId();
	}

	@Override
	public ProductResponse getProductById(long productId) {

		log.info("ProductServiceImpl | getProductById is called");
		log.info("ProductServiceImpl | getProductById | Get the product for productId: {}", productId);

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductServiceCustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

		ProductResponse productResponse = new ProductResponse();

		BeanUtils.copyProperties(product, productResponse);

		log.info("ProductServiceImpl | getProductById | productResponse :" + productResponse.toString());

		return productResponse;
	}

	@Override
	public void reduceQuantity(long productId, long quantity) {

		log.info("Reduce Quantity {} for Id: {}", quantity, productId);

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductServiceCustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

		if (product.getQuantity() < quantity) {
			throw new ProductServiceCustomException("Product does not have sufficient Quantity",
					"INSUFFICIENT_QUANTITY");
		}

		product.setQuantity(product.getQuantity() - quantity);
		productRepository.save(product);
		log.info("Product Quantity updated Successfully");
	}

	@Override
	public void deleteProductById(long productId) {
		log.info("Product id: {}", productId);

		if (!productRepository.existsById(productId)) {
			log.info("Im in this loop {}", !productRepository.existsById(productId));
			throw new ProductServiceCustomException("Product with given with Id: " + productId + " not found:",
					"PRODUCT_NOT_FOUND");
		}
		log.info("Deleting Product with id: {}", productId);
		productRepository.deleteById(productId);

	}
}