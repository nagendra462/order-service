package com.paper.order.model;

public enum Status {
	PENDING("PENDING"), ACCEPTED("ACCEPTED"), PROCESSING("PROCESSING"), COMPLETED("COMPLETED"),
	OUT_FOR_DELIVERY("OUT FOR DELIVERY");

	private String status;

	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
