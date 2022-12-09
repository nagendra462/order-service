package com.paper.order.service;

import org.springframework.http.ResponseEntity;

public interface PaperService {

	ResponseEntity<?> getUniqueValues();

	ResponseEntity<?> getCounts();
}
