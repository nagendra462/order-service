package com.paper.order.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.paper.order.model.Counter;
import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.Customer;
import com.paper.order.model.Order;
import com.paper.order.model.Status;
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
		Counter counter = this.mongoTemplate.findOne(query, Counter.class);
		if (counter == null) {
			counter = new Counter();
		}
		int orderCount = counter.getOrderCount() + 1;
		int rollCount = counter.getRollCount() + 1;
		order.setOrderId("WO-" + orderCount);
		order.setRollId("R-" + rollCount);
		order.setStatus(Status.PENDING.getStatus());
		this.mongoTemplate.save(order);
		Update update = new Update();
		update.set("orderCount", orderCount);
		update.set("rollCount", rollCount);
		this.mongoTemplate.updateFirst(query, update, Counter.class);
		return new ResponseEntity<>("Order successfully created with orderId- " + order.getOrderId(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getOrders(String searchInput) {
		Query query = new Query();
		if (StringUtils.isNotEmpty(searchInput)) {
			query = this.getSearchQuery(searchInput);
		}
		query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

		List<Order> orders = this.mongoTemplate.find(query, Order.class);
		if (!CollectionUtils.isEmpty(orders)) {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> updateOrder(UpdateOrderRequest request) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(request.getOrderId()));
		Order order = this.mongoTemplate.findOne(query, Order.class);
		if (order != null) {
			if (!request.getCustomerId().isEmpty()) {
				order.setCustomerId(request.getCustomerId());
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

	private Query getSearchQuery(String searchInput) {
		Query query = new Query();
		List<Criteria> criterias = new LinkedList<>();
		Criteria searchCriteria = new Criteria();
		searchCriteria.orOperator(
				Criteria.where("orderId")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("paperSupplier")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("customerId")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		criterias.add(searchCriteria);
		if (!CollectionUtils.isEmpty(criterias)) {
			Criteria criteria = new Criteria();
			criteria.andOperator(criterias.stream().toArray(Criteria[]::new));
			query.addCriteria(criteria);
		}
		return query;
	}

	@Override
	public ResponseEntity<?> getOrder(String orderId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		Order order = this.mongoTemplate.findOne(query, Order.class);
		if (order != null) {
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Order(), HttpStatus.OK);
		}
	}

}
