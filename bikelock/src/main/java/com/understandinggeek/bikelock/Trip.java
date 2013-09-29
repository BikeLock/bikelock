/**
 * 
 */
package com.understandinggeek.bikelock;

import java.util.*;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;


/**
 * @author duncanwalker
 * 
 */
public class Trip extends BroadcastReceiver {
	private long predictedDuration;
	private List<TripEvent> events;
	private boolean active = false;
    private SmsManager smsManager;
    private String phoneNumber;
    private Context app;

	public Trip( long duration) {
		this.smsManager = SmsManager.getDefault();
    	Log.v("BikeLock", "New Trip");
		
		active = true;
		
		events = new ArrayList<TripEvent>();

		//events.add(new TripEvent("First event\n"));
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				active = false;
			}
		}, duration);
	}
	
	public Trip( Context context, long duration) {

		this(duration);
		this.app = context;
	}
	
	

	public List<TripEvent> getEvents() {
		if (events == null)
			return Collections.emptyList();
		return events;
	}
	
	public int countEvents() {
		return events.size();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		onRecieveTripEvent("This is the response\n");
	}

	public void onRecieveTripEvent(String message) {
		if (active) {
			events.add(new TripEvent(message));

	    	Log.v("BikeLock", "Event: " + message);
				    	
	        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences((Activity) app);
	        //to fix once I'm worked out where to put strings like this
	     //   String sms = sharedPrefs.getString("prefReplyText", "I'm cycling");
	        String sms = "text";
	        try {
	            smsManager.sendTextMessage("5556", null, sms, null, null);
	            Log.v("BikeLock", sms);
	           // Toast.makeText(view.getContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            //Toast.makeText(view.getContext(),"SMS faild, please try again later!", Toast.LENGTH_LONG).show();
	            e.printStackTrace();
	        }
		}
	}

	public boolean isActive() {
		return active;
	}

}
