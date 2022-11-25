package com.paper.order.service;

import org.springframework.http.ResponseEntity;

import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.UpdateOrderRequest;

public interface OrderService {

	ResponseEntity<?> createOrder(CreateOrderRequest request);

	ResponseEntity<?> getOrders();

	ResponseEntity<?> updateOrder(UpdateOrderRequest request);

	ResponseEntity<?> deleteOrder(String orderId);
}
