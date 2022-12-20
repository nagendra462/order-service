package com.paper.order.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.paper.order.model.Counter;
import com.paper.order.model.CreateCustomerRequest;
import com.paper.order.model.Customer;
import com.paper.order.model.UpdateCustomerRequest;
import com.paper.order.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> createCustomer(CreateCustomerRequest request) {
		Query query = new Query();
		Counter counter = this.mongoTemplate.findOne(query, Counter.class);
		if (counter == null) {
			counter = new Counter();
		}
		int custCount = counter.getCustomerCount() + 1;
		Customer customer = new Customer();
		BeanUtils.copyProperties(request, customer);
		customer.setCustomerId("C-" + custCount);
		this.mongoTemplate.save(customer);
		Update update= new Update();
		update.set("customerCount", custCount);
		this.mongoTemplate.updateFirst(query, update, Counter.class);
		return new ResponseEntity<>("Customer successfully created with customerId- " + customer.getCustomerId(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCustomers(String searchInput) {
		Query query = new Query();
		if (StringUtils.isNotEmpty(searchInput)) {
			query = this.getSearchQuery(searchInput);
		}
		List<Customer> customers = this.mongoTemplate.find(query, Customer.class);
		if (!CollectionUtils.isEmpty(customers)) {
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getCustomer(String customerId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("customerId").is(customerId));
		Customer customer = this.mongoTemplate.findOne(query, Customer.class);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Customer(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> updateCustomer(UpdateCustomerRequest request) {
		Query query = new Query();
		query.addCriteria(Criteria.where("customerId").is(request.getCustomerId()));
		Customer customer = this.mongoTemplate.findOne(query, Customer.class);
		if (customer != null) {
			if (request.getFirstName() != null) {
				customer.setFirstName(request.getFirstName());
			}
			if (request.getLastName() != null) {
				customer.setLastName(request.getLastName());
			}
			if (request.getEmail() != null) {
				customer.setEmail(request.getEmail());
			}
			if (request.getPhone() != null) {
				customer.setPhone(request.getPhone());
			}
			if (request.getAddress() != null) {
				customer.setAddress(request.getAddress());
			}
			this.mongoTemplate.save(customer);
			return new ResponseEntity<>("Customer " + customer.getCustomerId() + " is successfully updated",
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No Customer found with Id- " + request.getCustomerId(), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> deleteCustomer(String customerId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("customerId").is(customerId));
		Customer customer = this.mongoTemplate.findOne(query, Customer.class);
		if (customer != null) {
			this.mongoTemplate.remove(customer);
			return new ResponseEntity<>("Customer " + customerId + " is successfully deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No Customer found with Id-" + customerId, HttpStatus.NOT_FOUND);
		}
	}

	private Query getSearchQuery(String searchInput) {
		Query query = new Query();
		List<Criteria> criterias = new LinkedList<>();
		Criteria searchCriteria = new Criteria();
		searchCriteria.orOperator(
				Criteria.where("customerId")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("name")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("email")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("address.city")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("address.state")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("address.country")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("address.line")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("address.zipCode")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				Criteria.where("phone")
						.regex(Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		criterias.add(searchCriteria);
		if (!CollectionUtils.isEmpty(criterias)) {
			Criteria criteria = new Criteria();
			criteria.andOperator(criterias.stream().toArray(Criteria[]::new));
			query.addCriteria(criteria);
		}
		return query;
	}
}
