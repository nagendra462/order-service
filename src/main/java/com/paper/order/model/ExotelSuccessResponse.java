package com.paper.order.model;

public class ExotelSuccessResponse extends ExotelResponse{
	public ExotelSuccessResponse Call;
	public String Sid;
	public String AccountSid;
	public String To;
	public String From;
	public String Status;
	public ExotelSuccessResponse(int httpStatus) {
	}
  }