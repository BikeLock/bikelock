package com.understandinggeek.bikelock;

import java.util.Date;

public class TripEvent {
	private long timestamp;
	private String response_text;
	// private Contact origin;
	
	public TripEvent() {
		timestamp = System.currentTimeMillis();
	}
	
	public String getResponse_text() {
		return response_text;
	}
	
	public Date getTimestamp() {
		return new Date( timestamp);
	}

	public void setResponse_text(String response_text) {
		this.response_text = response_text;
	}

	
	
	
}
