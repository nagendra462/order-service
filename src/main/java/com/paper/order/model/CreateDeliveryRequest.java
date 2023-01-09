package com.paper.order.model;

import java.util.Date;

public class CreateDeliveryRequest {
	private String orderId;
	private Date deliveryDate;
	private String status;
	private int rollWeight;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRollWeight() {
		return rollWeight;
	}

	public void setRollWeight(int rollWeight) {
		this.rollWeight = rollWeight;
	}

}
