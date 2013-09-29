package com.understandinggeek.bikelock.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.content.Intent;
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
public class CreateTripActivityTest extends
		ActivityInstrumentationTestCase2<CreateTripActivity> {
	private Solo solo;

	public CreateTripActivityTest() {
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
	public void testonCreate() {
		Log.v("BikeLock", "testonCreate");
		
		solo.assertCurrentActivity("It's not create trip", "CreateTripActivity");

		int test_duration = 52;
		solo.setProgressBar((ProgressBar) solo.getView(R.id.tripDurationSeek),test_duration);
		assertEquals("seekbar set ok",
				(((ProgressBar) solo.getView(R.id.tripDurationSeek))
						.getProgress()), test_duration);
		solo.clickOnButton("Start");		
		solo.assertCurrentActivity("It's trip", "TripActivity");
		
		final TextView duration_TextView = 	  (TextView) solo.getCurrentActivity().findViewById(R.id.estimatedDuration);

		solo.assertCurrentActivity("It's trip", "TripActivity");
		
		assertEquals("duration passed through ok", Integer.toString(test_duration),
				((TextView)duration_TextView).getText().toString());
		
		Log.v("BikeLock", ": " + ((TextView)duration_TextView).getText().toString());
		
		emulateIncomingSms();
		
		
		
	
		
	}
	
	/*
	@Test
	public void testcallsblocked() {
		Log.v("BikeLock", "testcallsblocked");
		solo.assertCurrentActivity("It's not create trip", "CreateTripActivity");
		
	}
	
	
	@Test
	public void testsmshandled() {
		Log.v("BikeLock", "testsmshandled");
		
		solo.assertCurrentActivity("It's not create trip", "CreateTripActivity");

	}
	*/
	
    public void emulateIncomingSms(){
        byte[][] pdus = null;
        pdus = new byte[1][];
        pdus[0] = hexStringToByteArray("07911326040000F0040B911346610089F60000208062917314080CC8F71D14969741F977FD07");

        Intent serviceIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
        serviceIntent.setClassName( "com.android.mms" ,
                "com.android.mms.transaction.SmsReceiverService" );
        serviceIntent.putExtra("pdus", pdus);
        serviceIntent.putExtra("format", "3gpp");

        Intent broadcastIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
        broadcastIntent.putExtra("pdus", pdus);        
        broadcastIntent.putExtra("format", "3gpp");

        solo.getCurrentActivity().getBaseContext().startService(serviceIntent);
        solo.getCurrentActivity().getBaseContext().sendBroadcast(broadcastIntent);
    }  
    
    
    public static byte[] hexStringToByteArray(String s) 
    {
        byte[] data = null;

        if(s.length() % 2 != 0)
        {
            s = "0" + s;
        }

        int len = s.length();
        data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) 
        {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }

        return data;
}
	
	
	
	

}
