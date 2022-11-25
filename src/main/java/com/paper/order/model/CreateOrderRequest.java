package com.paper.order.model;

public class CreateOrderRequest {
	private String customerName;
	private String rollWeight;
	private String rollSize;
	private String cupSize;
	private String paperSupplier;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRollWeight() {
		return rollWeight;
	}

	public void setRollWeight(String rollWeight) {
		this.rollWeight = rollWeight;
	}

	public String getRollSize() {
		return rollSize;
	}

	public void setRollSize(String rollSize) {
		this.rollSize = rollSize;
	}

	public String getCupSize() {
		return cupSize;
	}

	public void setCupSize(String cupSize) {
		this.cupSize = cupSize;
	}

	public String getPaperSupplier() {
		return paperSupplier;
	}

	public void setPaperSupplier(String paperSupplier) {
		this.paperSupplier = paperSupplier;
	}

}
