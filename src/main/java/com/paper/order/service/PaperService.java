package com.paper.order.service;

import org.springframework.http.ResponseEntity;

import com.paper.order.model.ApproveRequest;

public interface PaperService {

	ResponseEntity<?> getUniqueValues();

	ResponseEntity<?> getCounts();

	ResponseEntity<?> loginAuthentication(String userName, String password);

	ResponseEntity<?> getPendingAccounts();

	ResponseEntity<?> accountApproval(ApproveRequest request);
}
