package com.paper.order.service;

import org.springframework.http.ResponseEntity;

import com.paper.order.model.CreateCustomerRequest;
import com.paper.order.model.UpdateCustomerRequest;

public interface CustomerService {

	ResponseEntity<?> createCustomer(CreateCustomerRequest request);

	ResponseEntity<?> getCustomers(String searchInput);
	
	ResponseEntity<?> getCustomer(String customerId);

	ResponseEntity<?> updateCustomer(UpdateCustomerRequest request);

	ResponseEntity<?> deleteCustomer(String customerId);
}
