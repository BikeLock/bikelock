package com.understandinggeek.bikelock.test;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import android.telephony.PhoneNumberUtils;

import static com.understandinggeek.bikelock.R.id.tripDurationSeek;
import static java.lang.Integer.toString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class CreateTripActivityTest extends ActivityInstrumentationTestCase2<CreateTripActivity> {
    public static final String START = "Start";
    private Solo solo;
    public static final Integer JOURRNEY_DURATION = 52;

    public CreateTripActivityTest() {
		super(CreateTripActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() {
		solo.finishOpenedActivities();
	}

	@Test
	public void shouldSetJourneyDuration() {
        assertCurrentActivityIs("CreateTripActivity");

        solo.setProgressBar(getProgressBar(tripDurationSeek), JOURRNEY_DURATION);
        assertThat((getProgressBar(tripDurationSeek)).getProgress(), is(JOURRNEY_DURATION));
		solo.clickOnButton(START);
        //something flaky here. If I don't have this assertion the textView is null. Timing issue/Race condition?
        assertCurrentActivityIs("TripActivity");

        assertThat(getTextView(R.id.estimatedDuration).getText().toString(), is((JOURRNEY_DURATION.toString())));
	}

    @Test
	public void testonCreate() {
		emulateIncomingSms("15555215556","hi");
	}

	/*
	@Test
	public void testcallsblocked() {
		Log.v("BikeLock", "testcallsblocked");
		solo.assertCurrentActivityIs("It's not create trip", "CreateTripActivity");
		
	}
	
	
	@Test
	public void testsmshandled() {
		Log.v("BikeLock", "testsmshandled");
		
		solo.assertCurrentActivityIs("It's not create trip", "CreateTripActivity");

	}
	*/
	
	public void startRecievers() {
        try {

            // Start service
            Intent svc = new Intent(solo.getCurrentActivity(), com.understandinggeek.bikelock.AutoResponse.class);
            solo.getCurrentActivity().startService(svc);
          
            Log.v("BikeLock", "service started");
        }
        catch (Exception e) {
            Log.e("onCreate", "service creation problem", e);
        }
        
	}
	
	
    public void emulateIncomingSms(String sender, String message){
    	try {
    		 byte[][] pdus = null;
    	        pdus = new byte[1][];
    	     //  pdus[0] = hexStringToByteArray("07911326040000F0040B911346610089F60000208062917314080CC8F71D14969741F977FD07");
    	       // pdus[0] = hexStringToByteArray("069110090000F111000A9210299232900000AA0CC8F71D14969741F977FD07");
    	        
    	        pdus[0] =createPDU(sender, message);
    	        
    	        Intent serviceIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
    	        serviceIntent.setClassName( "com.android.mms" ,
    	                "com.android.mms.transaction.SmsReceiverService" );
    	        serviceIntent.putExtra("pdus", pdus);
    	        serviceIntent.putExtra("format", "3gpp");
    	     
    	        Intent broadcastIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
    	        broadcastIntent.putExtra("pdus",pdus );        
    	        broadcastIntent.putExtra("format", "3gpp"); 
    	        
    	        

    	        // context.startService(intent);
    	        
    	        solo.getCurrentActivity().getBaseContext().startService(serviceIntent);
    	        solo.getCurrentActivity().getBaseContext().sendBroadcast(broadcastIntent);
    	} catch (Exception e) {
        	Log.v("BikeLock", e.toString());
        }
       
    }  
    
    
    private static byte[] hexStringToByteArray(String s) 
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
    
    
    
    private static byte[]  createPDU( String sender,
            String body) {
        byte[] pdu = null;
        byte[] scBytes = PhoneNumberUtils
                .networkPortionToCalledPartyBCD("0000000000");
        byte[] senderBytes = PhoneNumberUtils
                .networkPortionToCalledPartyBCD(sender);
        int lsmcs = scBytes.length;
        byte[] dateBytes = new byte[7];
        Calendar calendar = new GregorianCalendar();
        dateBytes[0] = reverseByte((byte) (calendar.get(Calendar.YEAR)));
        dateBytes[1] = reverseByte((byte) (calendar.get(Calendar.MONTH) + 1));
        dateBytes[2] = reverseByte((byte) (calendar.get(Calendar.DAY_OF_MONTH)));
        dateBytes[3] = reverseByte((byte) (calendar.get(Calendar.HOUR_OF_DAY)));
        dateBytes[4] = reverseByte((byte) (calendar.get(Calendar.MINUTE)));
        dateBytes[5] = reverseByte((byte) (calendar.get(Calendar.SECOND)));
        dateBytes[6] = reverseByte((byte) ((calendar.get(Calendar.ZONE_OFFSET) + calendar
                .get(Calendar.DST_OFFSET)) / (60 * 1000 * 15)));
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            bo.write(lsmcs);
            bo.write(scBytes);
            bo.write(0x04);
            bo.write((byte) sender.length());
            bo.write(senderBytes);
            bo.write(0x00);
            bo.write(0x00); // encoding: 0 for default 7bit
            bo.write(dateBytes);
            try {
                String sReflectedClassName = "com.android.internal.telephony.GsmAlphabet";
                Class cReflectedNFCExtras = Class.forName(sReflectedClassName);
                Method stringToGsm7BitPacked = cReflectedNFCExtras.getMethod(
                        "stringToGsm7BitPacked", new Class[] { String.class });
                stringToGsm7BitPacked.setAccessible(true);
                byte[] bodybytes = (byte[]) stringToGsm7BitPacked.invoke(null,
                        body);
                bo.write(bodybytes);
            } catch (Exception e) {
            	Log.v("BikeLock", pdu.toString());
            	
            }

            pdu = bo.toByteArray();
        } catch (IOException e) {
        	Log.v("BikeLock", pdu.toString());
        }

     
        
        return pdu;
       
    }

    private static byte reverseByte(byte b) {
        return (byte) ((b & 0xF0) >> 4 | (b & 0x0F) << 4);
    }


    public ProgressBar getProgressBar(int id) {
        return (ProgressBar) solo.getView(id);
    }

    public void assertCurrentActivityIs(String activityName) {
        solo.assertCurrentActivity("Current activity is not "+activityName, activityName);
    }

    private TextView getTextView(int textViewId) {
        return (TextView) solo.getCurrentActivity().findViewById(textViewId);
    }
}
