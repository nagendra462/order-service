package com.paper.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.paper.order.service.PaperService;

@Service
public class PaperServiceImpl implements PaperService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> getUniqueValues() {
		return null;
	}

}
