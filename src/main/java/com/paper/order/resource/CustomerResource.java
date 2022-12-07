package com.paper.order.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paper.order.model.CreateCustomerRequest;
import com.paper.order.model.UpdateCustomerRequest;
import com.paper.order.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/createcustomer")
	public ResponseEntity<?> createOrder(@RequestBody CreateCustomerRequest request) {
		return this.customerService.createCustomer(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getcustomers")
	public ResponseEntity<?> getCustomers(@RequestParam String searchInput) {
		return this.customerService.getCustomers(searchInput);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getcustomer")
	public ResponseEntity<?> getCustomer(@RequestParam String customerId) {
		return this.customerService.getCustomer(customerId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PutMapping("/updatecustomer")
	public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request) {
		return this.customerService.updateCustomer(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@DeleteMapping("/deletecustomer")
	public ResponseEntity<?> deleteOrder(@RequestParam String customerId) {
		return this.customerService.deleteCustomer(customerId);
	}

}
