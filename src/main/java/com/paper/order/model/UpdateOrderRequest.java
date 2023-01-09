package com.paper.order.model;

import java.util.Date;

public class UpdateOrderRequest {
	private String orderId;
	private String customerId;
	private Integer rollWeight;
	private Integer rollSize;
	private Integer cupSize;
	private String paperSupplier;
	private Date orderDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getRollWeight() {
		return rollWeight;
	}

	public void setRollWeight(Integer rollWeight) {
		this.rollWeight = rollWeight;
	}

	public Integer getRollSize() {
		return rollSize;
	}

	public void setRollSize(Integer rollSize) {
		this.rollSize = rollSize;
	}

	public Integer getCupSize() {
		return cupSize;
	}

	public void setCupSize(Integer cupSize) {
		this.cupSize = cupSize;
	}

	public String getPaperSupplier() {
		return paperSupplier;
	}

	public void setPaperSupplier(String paperSupplier) {
		this.paperSupplier = paperSupplier;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
