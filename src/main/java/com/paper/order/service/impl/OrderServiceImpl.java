package com.paper.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.Order;
import com.paper.order.model.UpdateOrderRequest;
import com.paper.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> createOrder(CreateOrderRequest request) {
		Order order = new Order();
		BeanUtils.copyProperties(request, order);
		Query query = new Query();
		long count = this.mongoTemplate.count(query, Order.class);
		order.setOrderId(request.getCustomerName() + "-" + (count + 1));
		return new ResponseEntity<>(
				"Order successfully created with orderId- " + this.mongoTemplate.save(order).getOrderId(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getOrders() {
		Query query = new Query();
		List<Order> orders = this.mongoTemplate.find(query, Order.class);
		if (!CollectionUtils.isEmpty(orders)) {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> updateOrder(UpdateOrderRequest request) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(request.getOrderId()));
		Order order = this.mongoTemplate.findOne(query, Order.class);
		if (order != null) {
			if (!request.getCustomerName().isEmpty()) {
				order.setCustomerName(request.getCustomerName());
			}
			if (request.getCupSize() != null) {
				order.setCupSize(request.getCupSize());
			}
			if (request.getPaperSupplier() != null) {
				order.setPaperSupplier(request.getPaperSupplier());
			}
			if (request.getRollSize() != null) {
				order.setRollSize(request.getRollSize());
			}
			if (request.getRollWeight() != null) {
				order.setRollWeight(request.getRollWeight());
			}
			if (request.getOrderDate() != null) {
				order.setOrderDate(request.getOrderDate());
			}

			this.mongoTemplate.save(order);
			return new ResponseEntity<>("Order " + order.getOrderId() + " is successfully updated", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No order found with Id- " + request.getOrderId(), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> deleteOrder(String orderId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		Order order = this.mongoTemplate.findOne(query, Order.class);
		if (order != null) {
			this.mongoTemplate.remove(order);
			return new ResponseEntity<>("Order " + orderId + " is successfully deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No order found with Id-" + orderId, HttpStatus.NOT_FOUND);
		}
	}
}
