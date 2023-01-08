package com.paper.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

import com.paper.order.constants.OrderConstants;
import com.paper.order.model.AddPaymentRequest;
import com.paper.order.model.AmountMapper;
import com.paper.order.model.ApproveOrderRequest;
import com.paper.order.model.Counter;
import com.paper.order.model.CreateOrderRequest;
import com.paper.order.model.Order;
import com.paper.order.model.OrderRequest;
import com.paper.order.model.PaymentDetails;
import com.paper.order.model.Status;
import com.paper.order.model.UpdateOrderRequest;
import com.paper.order.service.OrderService;
import com.paper.order.service.SmsService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SmsService smsService;

	@Override
	public ResponseEntity<?> createOrderRequest(CreateOrderRequest request) {
//		this.smsService.triggerSms();
		OrderRequest order = new OrderRequest();
		BeanUtils.copyProperties(request, order);
		Query query = new Query();
		Counter counter = this.mongoTemplate.findOne(query, Counter.class);
		if (counter == null) {
			counter = new Counter();
		}
		int orderRequestCount = counter.getOrderRequestCount() + 1;
		order.setOrderRequestId("WOR-" + orderRequestCount);
		order.setStatus(Status.PENDING.getStatus());
		this.mongoTemplate.save(order);
		Update update = new Update();
		update.set("orderRequestCount", orderRequestCount);
		this.mongoTemplate.updateFirst(query, update, Counter.class);
		return new ResponseEntity<>("Order Request successfully created with Id- " + order.getOrderRequestId(),
				HttpStatus.OK);
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
	public ResponseEntity<?> getRequestedOrders(String searchInput) {
		Query query = new Query();
		if (StringUtils.isNotEmpty(searchInput)) {
			query = this.getSearchQuery(searchInput);
		}
		query.addCriteria(Criteria.where("status").ne(Status.ACCEPTED));
		query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
		List<OrderRequest> orders = this.mongoTemplate.find(query, OrderRequest.class);
		if (!CollectionUtils.isEmpty(orders)) {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getOrderByCustomerId(String customerId, String searchInput) {
		Query query = new Query();
		if (StringUtils.isNotEmpty(searchInput)) {
			query = this.getSearchQuery(searchInput);
		}
		query.addCriteria(Criteria.where("customerId").is(customerId));
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

	@Override
	public ResponseEntity<?> approveOrder(ApproveOrderRequest request) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderRequestId").is(request.getOrderRequestId()));
		Update update = new Update();
		update.set("status", request.getStatus());
		this.mongoTemplate.updateFirst(query, update, OrderRequest.class);
		if (request.getStatus().equals(Status.ACCEPTED.getStatus())) {
			OrderRequest orderRequest = this.mongoTemplate.findOne(query, OrderRequest.class);
			Order order = new Order();
			BeanUtils.copyProperties(orderRequest, order, "orderRequestId");
			Query orderQuery = new Query();
			Counter counter = this.mongoTemplate.findOne(orderQuery, Counter.class);
			if (counter == null) {
				counter = new Counter();
			}
			int orderCount = counter.getOrderCount() + 1;
			int rollCount = counter.getRollCount() + 1;
			order.setOrderId("WO-" + orderCount);
			order.setRollId("R-" + rollCount);
			order.setStatus(Status.ACCEPTED.getStatus());
			order.setAcceptedBy(request.getUserId());
			order.setTotalAmount(this.calculateOrderCost(orderRequest.getCupSize(), orderRequest.getRollWeight()));
			this.mongoTemplate.save(order);
			Update counterUpdate = new Update();
			update.set("orderCount", orderCount);
			update.set("rollCount", rollCount);
			this.mongoTemplate.updateFirst(query, counterUpdate, Counter.class);
			return new ResponseEntity<>("Order successfully created with Id- " + order.getOrderId(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Unable to create order as order request is not accepted", HttpStatus.OK);
	}

	private int calculateOrderCost(String cupSize, String rollWeight) {
		Query query = new Query();
		AmountMapper mapper = this.mongoTemplate.findOne(query, AmountMapper.class);
		if (mapper != null) {
			Map<String, String> valueMap = mapper.getValues();
			if (valueMap.containsKey(cupSize)) {
				for (Map.Entry<String, String> entry : valueMap.entrySet()) {
					if (entry.getKey().equals(cupSize)) {
						int cost = Integer.parseInt(entry.getValue());
						return cost * Integer.parseInt(rollWeight);
					}
				}
			}
		}
		return 0;
	}

	@Override
	public ResponseEntity<?> addPaymentDetails(AddPaymentRequest request) {
		if (StringUtils.isEmpty(request.getOrderId())) {
			return new ResponseEntity<>("Order is not found", HttpStatus.NOT_FOUND);
		}
		Query query = new Query();
		Counter counter = this.mongoTemplate.findOne(query, Counter.class);
		query.addCriteria(Criteria.where("orderId").is(request.getOrderId()));
		Order order = this.mongoTemplate.findOne(query, Order.class);
		if (order == null) {
			return new ResponseEntity<>("Order is not found", HttpStatus.NOT_FOUND);
		}
		int paymentCount = counter.getPaymentCount();
		PaymentDetails payment = new PaymentDetails();
		BeanUtils.copyProperties(request, payment);
		payment.setPaymentId("P-" + (counter.getPaymentCount() + 1));
		int amountPaid = order.getAmountPaid();
		amountPaid += payment.getAmount();
		order.setAmountPaid(amountPaid);
		order.setPaymentPending(order.getTotalAmount() - amountPaid);
		counter.setPaymentCount(paymentCount + 1);
		this.mongoTemplate.save(counter);
		this.mongoTemplate.save(order);
		this.mongoTemplate.save(payment);
		return new ResponseEntity<>("Payment details added successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deletePayment(String paymentId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("paymentId").is(paymentId));
		PaymentDetails payment = this.mongoTemplate.findOne(query, PaymentDetails.class);
		if (payment != null) {
			String orderId = payment.getOrderId();
			Query orderQuery = new Query();
			query.addCriteria(Criteria.where("orderId").is(orderId));
			Order order = this.mongoTemplate.findOne(orderQuery, Order.class);
			if (order != null) {
				int amountPaid = order.getAmountPaid();
				amountPaid = amountPaid - payment.getAmount();
				order.setAmountPaid(amountPaid);
				order.setPaymentPending(order.getTotalAmount() - amountPaid);
				this.mongoTemplate.save(order);
			}
			this.mongoTemplate.remove(payment);
			return new ResponseEntity<>("Payment " + paymentId + " is successfully deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No payment found with Id-" + paymentId, HttpStatus.NOT_FOUND);
		}
	}

}
