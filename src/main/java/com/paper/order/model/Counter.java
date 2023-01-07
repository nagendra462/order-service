package com.paper.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.COUNTER)
public class Counter {
	@Id
	private String id;
	private int orderCount;
	private int customerCount;
	private int deliveryCount;
	private int rollCount;
	private int orderRequestCount;
	private int paymentCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public int getDeliveryCount() {
		return deliveryCount;
	}

	public void setDeliveryCount(int deliveryCount) {
		this.deliveryCount = deliveryCount;
	}

	public int getRollCount() {
		return rollCount;
	}

	public void setRollCount(int rollCount) {
		this.rollCount = rollCount;
	}

	public int getOrderRequestCount() {
		return orderRequestCount;
	}

	public void setOrderRequestCount(int orderRequestCount) {
		this.orderRequestCount = orderRequestCount;
	}

	public int getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(int paymentCount) {
		this.paymentCount = paymentCount;
	}

}
