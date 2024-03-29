package com.paper.order.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paper.order.constants.CollectionConstants;

@Document(collection = CollectionConstants.AMOUNT_VALUES)
public class AmountMapper {
	@Id
	@JsonIgnore
	private String id;
	private Map<String, Double> values;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Double> getValues() {
		return values;
	}

	public void setValues(Map<String, Double> values) {
		this.values = values;
	}

}
