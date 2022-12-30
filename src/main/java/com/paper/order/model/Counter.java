package com.paper.order.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.COUNTER)
public class Counter {
	private int orderCount;
	private int customerCount;
	private int deliveryCount;
	private int rollCount;
	private int orderRequestCount;

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

}
