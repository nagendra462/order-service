package com.paper.order.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paper.order.model.ApproveRequest;
import com.paper.order.model.LoginRequest;
import com.paper.order.model.ReportFilter;
import com.paper.order.service.PaperService;

@RestController
@RequestMapping("/api/v1/paper")
public class PaperResource {

	@Autowired
	private PaperService paperService;

	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/unique")
	public ResponseEntity<?> getUniqueValues() {
		return this.paperService.getUniqueValues();
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/counts")
	public ResponseEntity<?> getCounts() {
		return this.paperService.getCounts();
	}

	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		return this.paperService.loginAuthentication(request.getUsername(), request.getPassword());
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@GetMapping("/getpendingaccounts")
	public ResponseEntity<?> getPendingAccounts() {
		return this.paperService.getPendingAccounts();
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/accountapproval")
	public ResponseEntity<?> accountApproval(@RequestBody ApproveRequest request) {
		return this.paperService.accountApproval(request);
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping("/reports")
	public ResponseEntity<?> generateReports(@RequestBody ReportFilter request) {
		return this.paperService.generateReport(request);
	}
}
