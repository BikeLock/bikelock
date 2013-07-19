/**
 * 
 */
package com.understandinggeek.bikelock;
import java.util.*;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Chronometer;
/**
 * @author duncanwalker
 *
 */
public class Trip extends BroadcastReceiver  {
	 private long predicted_duration;
	 private  List<TripEvent> events;
	 private Chronometer duration;


	 public Trip(Context application, long predicted_duration) {
		 this.predicted_duration = predicted_duration; 
		 duration = new Chronometer(application);
	     duration.start();
	 }
	 
	 public Map getResponses() {
		 // a map of responses sent by the app
		Map<Date,String> responses = new HashMap<Date,String>();
	    for (TripEvent event : events) {
	    	responses.put(event.getTimestamp(), event.getResponse_text());	
	    }
	    return responses;
	 }
	 
	 
	  @Override
	  public void onReceive(Context context, Intent intent) {
		  // make a new TripEvent to handle the incoming call/sms
	  
	  }

}
