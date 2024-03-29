package com.paper.order.service;

import org.springframework.http.ResponseEntity;

import com.paper.order.model.AddPaymentRequest;
import com.paper.order.model.ApproveOrderRequest;
import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.UpdateOrderRequest;

public interface OrderService {

	ResponseEntity<?> createOrderRequest(CreateOrderRequest request);

	ResponseEntity<?> getOrders(String searchInput);

	ResponseEntity<?> getRequestedOrders(String searchInput);

	ResponseEntity<?> updateOrder(UpdateOrderRequest request);

	ResponseEntity<?> deleteOrder(String orderId);

	ResponseEntity<?> getOrder(String orderId);

	ResponseEntity<?> approveOrder(ApproveOrderRequest request);

	ResponseEntity<?> getOrderByCustomerId(String customerId, String searchInput);

	ResponseEntity<?> addPaymentDetails(AddPaymentRequest request);

	ResponseEntity<?> deletePayment(String paymentId);

	ResponseEntity<?> getPayments();

	ResponseEntity<?> getPaymentByOrderId(String orderId);

}
