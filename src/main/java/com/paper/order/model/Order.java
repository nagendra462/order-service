package com.paper.order.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.ORDERS)
public class Order {
	@Id
	private String id;
	private Date orderDate;
	private String customerId;
	private String rollWeight;
	private String rollSize;
	private String cupSize;
	private String paperSupplier;
	private String orderId;
	private String rollId;
	@CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private String lastModifiedDate;
	private String createdBy;
	private String status;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRollId() {
		return rollId;
	}

	public void setRollId(String rollId) {
		this.rollId = rollId;
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

}
