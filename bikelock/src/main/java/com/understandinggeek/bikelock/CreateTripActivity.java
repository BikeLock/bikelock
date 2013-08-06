package com.understandinggeek.bikelock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import android.widget.TextView; 

import static java.lang.Long.valueOf;

public class CreateTripActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.create_trip);
  	
        Button startButton = (Button) findViewById(R.id.startButton);
     
        SeekBar tripDuration = (SeekBar) findViewById(R.id.tripDurationSeek); 
    
        startButton.setOnClickListener(new StartTripClickListener(valueOf(tripDuration.getProgress())));

    
        final TextView tripDurationValue = (TextView)findViewById(R.id.tripDurationText); 

        tripDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 
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
        });  


    
    }
}
