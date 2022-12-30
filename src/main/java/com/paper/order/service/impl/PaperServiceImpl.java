package com.paper.order.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.paper.order.model.ApproveRequest;
import com.paper.order.model.Customer;
import com.paper.order.model.Delivery;
import com.paper.order.model.Order;
import com.paper.order.model.Status;
import com.paper.order.model.UniqueValues;
import com.paper.order.service.PaperService;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class PaperServiceImpl implements PaperService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> getUniqueValues() {
		Query query = new Query();
		UniqueValues values = this.mongoTemplate.findOne(query, UniqueValues.class);
		if (values != null) {
			return new ResponseEntity<>(values, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new UniqueValues(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getCounts() {
		Map<String, Long> counter = new LinkedHashMap<>();
		Query query = new Query();
		long orderCount = this.mongoTemplate.count(query, Order.class);
		counter.put("orderCount", orderCount);
		long customerCount = this.mongoTemplate.count(query, Customer.class);
		counter.put("customerCount", customerCount);
		long deliveryCount = this.mongoTemplate.count(query, Delivery.class);
		counter.put("deliveryCount", deliveryCount);
		return new ResponseEntity<>(counter, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> loginAuthentication(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return new ResponseEntity<>("Username or Password should not be blank", HttpStatus.BAD_REQUEST);
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(username));
		if (this.mongoTemplate.count(query, Customer.class) == 0) {
			return new ResponseEntity<>("Email doesn't exist", HttpStatus.BAD_REQUEST);
		}
		query.addCriteria(Criteria.where("password").is(password));
		Customer customer = this.mongoTemplate.findOne(query, Customer.class);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
		}
	}

	@Override
	public ResponseEntity<?> getPendingAccounts() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(Status.PENDING.getStatus()));
		return new ResponseEntity<>(this.mongoTemplate.find(query, Customer.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> accountApproval(ApproveRequest request) {
		Query query = new Query();
		query.addCriteria(Criteria.where("customerId").is(request.getCustomerId()));
		Update update = new Update();
		update.set("status", request.getStatus());
		this.mongoTemplate.updateFirst(query, update, Customer.class);
		return new ResponseEntity<>("Account" + request.getStatus().toLowerCase() + " successfully", HttpStatus.OK);
	}

}
