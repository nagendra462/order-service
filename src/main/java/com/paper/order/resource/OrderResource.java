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

import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.UpdateOrderRequest;
import com.paper.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderResource {
	
	@Autowired
	private OrderService orderService;

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/createorder")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
		return this.orderService.createOrder(request);
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getorders")
	public ResponseEntity<?> getOrders(@RequestParam String searchInput){
		return this.orderService.getOrders(searchInput);
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getorder")
	public ResponseEntity<?> getOrder(@RequestParam String orderId){
		return this.orderService.getOrder(orderId);
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@PutMapping("/updateorder")
	public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request){
		return this.orderService.updateOrder(request);
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@DeleteMapping("/deleteorder")
	public ResponseEntity<?> deleteOrder(@RequestParam String orderId){
		return this.orderService.deleteOrder(orderId);
	}
	
}
