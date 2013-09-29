package com.understandinggeek.bikelock;

import java.util.Date;

public class TripEvent {
	private long timestamp;
	private String response_text;
	// private Contact origin;
	
	public TripEvent() {
		timestamp = System.currentTimeMillis();
	}
	
	public TripEvent(String response) {
		timestamp = System.currentTimeMillis();
		response_text =response;
	}
	
	public String toString() {
		return ("\n Time:" + (new Date(timestamp)).toString() 
				+"\n response: "+ response_text
				+"\n details: TBC");
		
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
