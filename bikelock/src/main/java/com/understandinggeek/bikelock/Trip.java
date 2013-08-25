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
    private Chronometer chronometer;
    private long predictedDuration;
	 private  List<TripEvent> events;

     //removed the dependency on the context here so that trip doesn't have to know about the app.
	 public Trip(Chronometer tripTimer, long predictedDuration) {
         this.chronometer = tripTimer;
         this.predictedDuration = predictedDuration;
	     tripTimer.start();
	 }
	 
	 public Trip() {
		// TODO Auto-generated constructor stub
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
