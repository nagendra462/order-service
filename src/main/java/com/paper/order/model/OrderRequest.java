package com.paper.order.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.ORDER_REQUESTS)
public class OrderRequest {
	@Id
	private String id;
	private Date orderDate;
	private String customerId;
	private int rollWeight;
	private int rollSize;
	private int cupSize;
	private String paperSupplier;
	private String orderRequestId;
	@CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private String lastModifiedDate;
	private String createdBy;
	private String status;
	private String reason;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getRollWeight() {
		return rollWeight;
	}

	public void setRollWeight(int rollWeight) {
		this.rollWeight = rollWeight;
	}

	public int getRollSize() {
		return rollSize;
	}

	public void setRollSize(int rollSize) {
		this.rollSize = rollSize;
	}

	public int getCupSize() {
		return cupSize;
	}

	public void setCupSize(int cupSize) {
		this.cupSize = cupSize;
	}

	public String getPaperSupplier() {
		return paperSupplier;
	}

	public void setPaperSupplier(String paperSupplier) {
		this.paperSupplier = paperSupplier;
	}

	public String getOrderRequestId() {
		return orderRequestId;
	}

	public void setOrderRequestId(String orderRequestId) {
		this.orderRequestId = orderRequestId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
