package com.paper.order.model;

public class ApproveOrderRequest {
	private String orderRequestId;
	private String status;
	private String userId;
	private String reason;

	public String getOrderRequestId() {
		return orderRequestId;
	}

	public void setOrderRequestId(String orderRequestId) {
		this.orderRequestId = orderRequestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
