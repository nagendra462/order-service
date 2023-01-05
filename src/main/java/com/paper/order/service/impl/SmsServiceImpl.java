package com.paper.order.service.impl;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paper.order.model.SmsRequest;
import com.paper.order.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Value("${sms.senderId}")
	private String senderId;

	@Value("${sms.token}")
	private String token;

	@Value("${sms.endpoint}")
	private String smsEndPoint;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void triggerSms() {
		SmsRequest request = new SmsRequest();
		request.setFrom(senderId);
		request.setTo("7842576878");
		request.setBody("This is a test message powered by Exotel. Report abuse to +918088919888 -Exotel");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("authorization", "Basic " + token);
		HttpEntity<SmsRequest> requestEntity = new HttpEntity<>(request, headers);
		this.restTemplate.postForEntity(smsEndPoint, requestEntity, Object.class);
	}

}
