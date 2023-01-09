package com.paper.order.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.paper.order.model.CreateDeliveryRequest;
import com.paper.order.model.Delivery;
import com.paper.order.model.Order;
import com.paper.order.model.Status;
import com.paper.order.model.UpdateDeliveryRequest;
import com.paper.order.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> createDelivery(CreateDeliveryRequest request) {

		Query query = new Query();
		Counter counter = this.mongoTemplate.findOne(query, Counter.class);
		if (counter == null) {
			counter = new Counter();
		}
		int deliveryCount = counter.getDeliveryCount() + 1;
		Delivery delivery = new Delivery();
		BeanUtils.copyProperties(request, delivery);
		delivery.setDeliveryId("D-" + deliveryCount);
		delivery.setStatus(Status.PENDING.getStatus());
		this.mongoTemplate.save(delivery);
		Update update = new Update();
		update.set("deliveryCount", deliveryCount);
		this.mongoTemplate.updateFirst(query, update, Counter.class);
		return new ResponseEntity<>("Delivery successfully created with deliveryId- " + delivery.getDeliveryId(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getDeliveries() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
		List<Delivery> deliveries = this.mongoTemplate.find(query, Delivery.class);
		if (!CollectionUtils.isEmpty(deliveries)) {
			return new ResponseEntity<>(deliveries, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getDelivery(String deliveryId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("deliveryId").is(deliveryId));
		Delivery delivery = this.mongoTemplate.findOne(query, Delivery.class);
		if (delivery != null) {
			return new ResponseEntity<>(delivery, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Delivery(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getDeliveryByOrderId(String orderId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		Delivery delivery = this.mongoTemplate.findOne(query, Delivery.class);
		if (delivery != null) {
			return new ResponseEntity<>(delivery, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Delivery(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> updateDelivery(UpdateDeliveryRequest request) {
		Query query = new Query();
		query.addCriteria(Criteria.where("deliveryId").is(request.getDeliveryId()));
		Delivery delivery = this.mongoTemplate.findOne(query, Delivery.class);
		if (delivery != null) {
			if (!request.getOrderId().isEmpty()) {
				delivery.setOrderId(request.getOrderId());
			}
			if (request.getDeliveryDate() != null) {
				delivery.setDeliveryDate(request.getDeliveryDate());
			}
			if (request.getStatus() != null) {
				delivery.setStatus(request.getStatus());
				if (request.getStatus().equals(Status.DELIVERED.getStatus())) {
					Query orderQuery = new Query();
					orderQuery.addCriteria(Criteria.where("orderId").is(delivery.getOrderId()));
					Order order = this.mongoTemplate.findOne(orderQuery, Order.class);
					int pendingRoll = order.getRemainingRollWeight();
					pendingRoll += delivery.getRollWeight();
					order.setRemainingRollWeight(pendingRoll);
					order.setUtilizedRollWeight(order.getRollWeight() - pendingRoll);
					this.mongoTemplate.save(order);
				}
			}
			this.mongoTemplate.save(delivery);
			return new ResponseEntity<>("Delivery " + delivery.getDeliveryId() + " is successfully updated",
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No delivery found with Id- " + request.getDeliveryId(), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> deleteDelivery(String deliveryId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("deliveryId").is(deliveryId));
		Delivery delivery = this.mongoTemplate.findOne(query, Delivery.class);
		if (delivery != null) {
			if (delivery.getStatus().equals(Status.DELIVERED.getStatus())) {
				return new ResponseEntity<>("Cannot delete delivery which is already delivered", HttpStatus.OK);
			}
			this.mongoTemplate.remove(delivery);
			return new ResponseEntity<>("Delivery " + deliveryId + " is successfully deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No Delivery found with Id-" + deliveryId, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> getDeliveriesByCustomerId(String customerId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("customerId").in(customerId));
		query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
		List<Delivery> deliveries = this.mongoTemplate.find(query, Delivery.class);
		if (!CollectionUtils.isEmpty(deliveries)) {
			return new ResponseEntity<>(deliveries, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
	}

}
