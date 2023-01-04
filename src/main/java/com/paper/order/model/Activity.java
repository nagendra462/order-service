package com.paper.order.model;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.ACTIVITY_LOG)
public class Activity {
	@Id
	private String id;
	private String activity;
	private Map<String, String> details;
	@CreatedDate
	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivityName(String activity) {
		this.activity = activity;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setActivityDetails(Map<String, String> details) {
		this.details = details;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
