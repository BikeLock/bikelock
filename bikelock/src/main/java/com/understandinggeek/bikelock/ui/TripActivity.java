package com.understandinggeek.bikelock.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.understandinggeek.bikelock.R;

public class TripActivity extends Activity {
	private String duration;
	  TextView estimatedDuration; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip);
		
		estimatedDuration = (TextView) findViewById(R.id.estimatedDuration);
		Bundle extras = getIntent().getExtras();
		// I guess there should be some proper exception handling, this is a bit quick and dirty
		if (extras == null) {
		    return;
		    }
		// Get data via the key
		String ed = getIntent().getStringExtra("ESTIMATED_DURATION");
		if (ed != null) {
			 
		} 
		estimatedDuration = (TextView) findViewById(R.id.estimatedDuration); 
		estimatedDuration.setText(ed);

	}
	
	public String getDuration() {
		
		return duration;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip, menu);
		return true;
	}
	
	
	public void stopChronometer(View v) {
		
		super.onBackPressed();
	}

}
