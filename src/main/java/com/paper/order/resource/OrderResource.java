package com.paper.order.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@PostMapping("/createorder")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
		return this.orderService.createOrder(request);
	}
	
	@GetMapping("/getorders")
	public ResponseEntity<?> getOrders(){
		return this.orderService.getOrders();
	}
	
	@PutMapping("/updateorder")
	public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request){
		return this.orderService.updateOrder(request);
	}
	
	@DeleteMapping("/deleteorder")
	public ResponseEntity<?> deleteOrder(@RequestParam String orderId){
		return this.orderService.deleteOrder(orderId);
	}
	
}
