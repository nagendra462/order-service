package com.paper.order.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.UNIQUE_VALUES)
public class UniqueValues {
	@Id
	@JsonIgnore
	private String id;
	private Map<String, List<String>> values;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, List<String>> getValues() {
		return values;
	}

	public void setValues(Map<String, List<String>> values) {
		this.values = values;
	}

}
