package com.understandinggeek.bikelock.ui;

import com.understandinggeek.bikelock.AutoResponse;
import com.understandinggeek.bikelock.R;
import com.understandinggeek.bikelock.Trip;
import com.understandinggeek.bikelock.ui.TripActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.telephony.SmsManager;
import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
import android.widget.SeekBar;

import android.widget.TextView; 


public class CreateTripActivity extends Activity {
	 SeekBar tripDuration;
	 Trip trip;
	public void startTrip(View v) {
      //  this.predictedDuration = predictedDuration;
        Intent i = new Intent(this, TripActivity.class);
        i.putExtra("ESTIMATED_DURATION", (Integer.toString(tripDuration.getProgress())));
        startActivity(i); 
        trip = new Trip(v.getContext(), tripDuration.getProgress() * 10);
     //   trip.onCreate();
       // trip.onRecieveTripEvent("This is the response\n");
        
        
       
        
   //          
 //valueOf(tripDuration.getProgress());

	}

	public void startRecievers() {
        try {

            // Start service
            Intent svc = new Intent(this, AutoResponse.class);
            this.startService(svc);
            
       
        }
        catch (Exception e) {
            Log.e("onCreate", "service creation problem", e);
        }
        
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.create_trip);
        tripDuration = (SeekBar) findViewById(R.id.tripDurationSeek); 
        
       final TextView tripDurationValue = (TextView) findViewById(R.id.tripDurationText); 
       startRecievers();
       

     
       
       tripDuration.setOnSeekBarChangeListener(
    		   new SeekBar.OnSeekBarChangeListener() { 
    	    		@Override 
    	    		public void onProgressChanged(SeekBar seekBar, int progress, 
    	    		  boolean fromUser) { 
    	    		 // TODO Auto-generated method stub 
    	    			tripDurationValue.setText(String.valueOf(progress)); 
    	    			
    	    		} 
    	    		
    	    		@Override 
    	    		public void onStartTrackingTouch(SeekBar seekBar) { 
    	    		 // TODO Auto-generated method stub 
    	    		} 
    	    		
    	    		@Override 
    	    		public void onStopTrackingTouch(SeekBar seekBar) { 
    	    		 // TODO Auto-generated method stub 
    	    		} 
    	        }
    		   
    		   );  


    
    }
}
