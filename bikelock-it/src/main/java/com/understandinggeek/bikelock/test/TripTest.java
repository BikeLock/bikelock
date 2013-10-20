package com.understandinggeek.bikelock.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.understandinggeek.bikelock.TripEvent;
import android.util.Log;

import android.test.AndroidTestCase;
import org.junit.Assert;

import com.understandinggeek.bikelock.Trip;

import android.util.Log;

@RunWith(JUnit4.class)
public class TripTest extends AndroidTestCase {
	
	
    public void testTripIsActive() throws Throwable {
    	Trip test_trip = new Trip(1000);	
        Assert.assertTrue(test_trip.isActive());
     }

    public void testTripTimesout() throws Throwable {
    	Trip test_trip = new Trip(1000);	
        Assert.assertTrue(test_trip.isActive());
        Thread.sleep(2000);
        Assert.assertFalse(test_trip.isActive());
        Assert.assertEquals(0, test_trip.countEvents() );
      //  test_trip.onRecieveTripEvent("An event");
        Assert.assertEquals(0, test_trip.countEvents() );
     }
  

    public void testRecordsEvents() throws Throwable {
    	Trip test_trip = new Trip(1000);	
    	Assert.assertEquals(0, test_trip.countEvents() );
    	//test_trip.onRecieveTripEvent("An event");
    	Assert.assertEquals(1, test_trip.countEvents() );
    	//test_trip.onRecieveTripEvent("An event");
    	Assert.assertEquals(2, test_trip.countEvents() );
    	
    	boolean event_recorded = false;
    	for(TripEvent item : test_trip.getEvents() ){
    	    event_recorded  = (item.getResponse_text().equals("An event"));
    	    if (event_recorded) break;
    	}
    	
    	Log.v("BikeLock", "Events: " + (test_trip.getEvents()));
		Assert.assertTrue(event_recorded);
    	
		
     }   
    
    
    

}
