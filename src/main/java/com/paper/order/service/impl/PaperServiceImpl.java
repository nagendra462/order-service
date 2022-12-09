package com.paper.order.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.paper.order.model.Customer;
import com.paper.order.model.Order;
import com.paper.order.model.UniqueValues;
import com.paper.order.service.PaperService;

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
		return new ResponseEntity<>(counter, HttpStatus.OK);
	}

}
