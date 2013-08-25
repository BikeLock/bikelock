package com.understandinggeek.bikelock.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import android.test.ActivityInstrumentationTestCase2;

//import android.view.View;
import android.widget.ProgressBar;
//import android.widget.SeekBar;
import android.widget.TextView;
//import com.understandinggeek.bikelock.*;
import com.understandinggeek.bikelock.ui.*;
import com.understandinggeek.bikelock.R;
import com.jayway.android.robotium.solo.Solo;
import android.util.Log;

@RunWith(JUnit4.class)
public class TripTest extends
		ActivityInstrumentationTestCase2<CreateTripActivity> {
	private Solo solo;

	public TripTest() {
		super(CreateTripActivity.class);
	}

	// @BeforeClass
	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	
	}

	// @AfterTest
	@Override
	public void tearDown() {
		solo.finishOpenedActivities();

	}

	@Test
	public void testStartTimer() {

		solo.assertCurrentActivity("It's not create trip", "CreateTripActivity");

		int test_duration = 52;
		solo.setProgressBar((ProgressBar) solo.getView(R.id.tripDurationSeek),
				test_duration);
		assertEquals("seekbar set ok",
				(((ProgressBar) solo.getView(R.id.tripDurationSeek))
						.getProgress()), test_duration);
		solo.clickOnButton("Start");
		solo.assertCurrentActivity("It's not trip", "TripActivity");
		
		final TextView exampleTextView = 	  (TextView) solo.getCurrentActivity().findViewById(R.id.estimatedDuration);

		Log.v("BikeLock", ": " + ((TextView)exampleTextView).getText().toString());
		 
		assertEquals("duration passed through ok", Integer.toString(test_duration),
				
				((TextView)exampleTextView).getText().toString());
 
		

	}

}
