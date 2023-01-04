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

import com.paper.order.model.CreateDeliveryRequest;
import com.paper.order.model.UpdateDeliveryRequest;
import com.paper.order.service.DeliveryService;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryResource {

	@Autowired
	private DeliveryService deliveryService;

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/createdelivery")
	public ResponseEntity<?> createDelivery(@RequestBody CreateDeliveryRequest request) {
		return this.deliveryService.createDelivery(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getdeliveries")
	public ResponseEntity<?> getDeliveries() {
		return this.deliveryService.getDeliveries();
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getdeliveriesbycustomer")
	public ResponseEntity<?> getDeliveriesByCustomerId(@RequestParam String customerId) {
		return this.deliveryService.getDeliveriesByCustomerId(customerId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getdelivery")
	public ResponseEntity<?> getDelivery(@RequestParam String deliveryId) {
		return this.deliveryService.getDelivery(deliveryId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getdeliverybyorderid")
	public ResponseEntity<?> getDeliveryByOrderId(@RequestParam String orderId) {
		return this.deliveryService.getDeliveryByOrderId(orderId);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PutMapping("/updatedelivery")
	public ResponseEntity<?> updateDelivery(@RequestBody UpdateDeliveryRequest request) {
		return this.deliveryService.updateDelivery(request);
	}

	@CrossOrigin(value = "http://localhost:3000")
	@DeleteMapping("/deletedelivery")
	public ResponseEntity<?> deleteDelivery(@RequestParam String deliveryId) {
		return this.deliveryService.deleteDelivery(deliveryId);
	}

}
