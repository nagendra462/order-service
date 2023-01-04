package com.paper.order.service;

import org.springframework.http.ResponseEntity;

import com.paper.order.model.CreateDeliveryRequest;
import com.paper.order.model.UpdateDeliveryRequest;

public interface DeliveryService {

	ResponseEntity<?> createDelivery(CreateDeliveryRequest request);

	ResponseEntity<?> getDeliveries();

	ResponseEntity<?> getDelivery(String deliveryId);

	ResponseEntity<?> getDeliveryByOrderId(String orderId);

	ResponseEntity<?> updateDelivery(UpdateDeliveryRequest request);

	ResponseEntity<?> deleteDelivery(String deliveryId);

	ResponseEntity<?> getDeliveriesByCustomerId(String customerId);

}
