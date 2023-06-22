package com.microserviceexample.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceexample.demo.dto.ProductRequest;
import com.microserviceexample.demo.dto.ProductResponse;
import com.microserviceexample.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product/")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	@PostMapping("/")
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

		log.info("ProductController | addProduct is called");

		log.info("ProductController | addProduct | productRequest : " + productRequest.toString());

		long productId = productService.addProduct(productRequest);
		return new ResponseEntity<>(productId, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {

		log.info("ProductController | getProductById is called");

		log.info("ProductController | getProductById | productId : " + productId);

		ProductResponse productResponse = productService.getProductById(productId);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@PutMapping("/reduceQuantity/{id}")
	public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {

		log.info("ProductController | reduceQuantity is called");

		log.info("ProductController | reduceQuantity | productId : " + productId);
		log.info("ProductController | reduceQuantity | quantity : " + quantity);

		productService.reduceQuantity(productId, quantity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void deleteProductById(@PathVariable("id") long productId) {
		productService.deleteProductById(productId);
	}
}