package com.understandinggeek.bikelock.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import android.test.ActivityInstrumentationTestCase2;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.understandinggeek.bikelock.*;
import com.understandinggeek.bikelock.ui.*;
import com.understandinggeek.bikelock.R;
import com.jayway.android.robotium.solo.Solo;


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
	public void startTimer() {

		solo.assertCurrentActivity("It's not create trip", "CreateTripActivity");

		int test_duration = 52;
		solo.setProgressBar((ProgressBar) solo.getView(R.id.tripDurationSeek),
				test_duration);
		assertEquals("seekbar set ok",
				(((ProgressBar) solo.getView(R.id.tripDurationSeek))
						.getProgress()), test_duration);
		solo.clickOnButton("Start");
		solo.assertCurrentActivity("It's not trip", "TripActivity");
		assertEquals("duration passed through ok", test_duration,
				((TextView) solo.getView(R.id.estimated_duration)));

		;

	}

}
