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

import com.paper.order.model.AddPaymentRequest;
import com.paper.order.model.ApproveOrderRequest;
import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.UpdateOrderRequest;
import com.paper.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/paper/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/createorderrequest")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
		return this.orderService.createOrderRequest(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/orderapproval")
	public ResponseEntity<?> approveOrder(@RequestBody ApproveOrderRequest request) {
		return this.orderService.approveOrder(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getorders")
	public ResponseEntity<?> getOrders(@RequestParam(required = false) String searchInput) {
		return this.orderService.getOrders(searchInput);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getrequestedorders")
	public ResponseEntity<?> getRequestedOrders(@RequestParam(required = false) String searchInput) {
		return this.orderService.getRequestedOrders(searchInput);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getorder")
	public ResponseEntity<?> getOrder(@RequestParam String orderId) {
		return this.orderService.getOrder(orderId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getordersbycustomer")
	public ResponseEntity<?> getOrderByCustomer(@RequestParam String customerId,
			@RequestParam(required = false) String searchInput) {
		return this.orderService.getOrderByCustomerId(customerId, searchInput);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PutMapping("/updateorder")
	public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request) {
		return this.orderService.updateOrder(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@DeleteMapping("/deleteorder")
	public ResponseEntity<?> deleteOrder(@RequestParam String orderId) {
		return this.orderService.deleteOrder(orderId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/addpayment")
	public ResponseEntity<?> addPaymentDetails(@RequestBody AddPaymentRequest request) {
		return this.orderService.addPaymentDetails(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@DeleteMapping("/deletepayment")
	public ResponseEntity<?> deletePayment(@RequestParam String paymentId) {
		return this.orderService.deletePayment(paymentId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getpayments")
	public ResponseEntity<?> getPayments() {
		return this.orderService.getPayments();
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getpaymentsbyorder")
	public ResponseEntity<?> getPaymentsByOrderId(@RequestParam String orderId) {
		return this.orderService.getPaymentByOrderId(orderId);
	}

}
