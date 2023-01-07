package com.paper.order.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.paper.order.model.ExotelFailureResponse;
import com.paper.order.model.ExotelResponse;
import com.paper.order.model.ExotelSuccessResponse;
import com.paper.order.model.SmsRequest;
import com.paper.order.service.SmsService;

import javax.naming.ldap.ExtendedResponse;

import com.google.gson.Gson;

import okhttp3.Credentials;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class SmsServiceImpl implements SmsService {

	@Value("${sms.senderId}")
	private String senderId;

	@Value("${sms.token}")
	private String token;

	@Value("${sms.endpoint}")
	private String smsEndPoint;

	public static String customerNumber = "EXOSMS";
	public static String agentNumber = "7842576878";
	public static String url = "http://my.exotel.com/";
	public static String exotelSid = "suchiit1";
	public static String apiid = "92584ebde2b5be1166be8691c9c78acba57d0beb48935e7c";
	public static String apitoken = "4b7ee38f903a7763fe6141f85e0a0eafa9fdbe95d455b621";
	public static String msg = "This is a test message powered by Exotel. Report abuse to +918088919888 -Exotel";

	@Override
	public void triggerSms() {
		
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("From", customerNumber)
				.addFormDataPart("To",agentNumber )
				.addFormDataPart("Body",msg).build();
		

		String credentials = Credentials.basic(apiid, apitoken);

		Request request = new Request.Builder()
				.url(String.format(smsEndPoint, exotelSid)).method("POST", body)
				.addHeader("Authorization", credentials).addHeader("Content-Type", "application/json").build();

		try {
			Response response = client.newCall(request).execute();
			Gson connect = new Gson();
			String res = null;
			try {
				res = response.body().string();
				System.out.println("response - "+ res) ;
			} catch (IOException error) {
				error.printStackTrace();
			}

			ExotelResponse SuccessResponse = connect.fromJson(res, ExotelResponse.class);

			int status = response.code();
			System.out.println(status);

			if (status == 200) {
				ExotelSuccessResponse cust = new ExotelSuccessResponse(0);
				System.out.println(cust);
			} else {
				ExotelFailureResponse cust = new ExotelFailureResponse(0);
				System.out.println(cust);
			}
		} catch (Exception error) {
			error.printStackTrace();
		}

	}

}
