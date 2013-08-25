package com.understandinggeek.bikelock.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import com.understandinggeek.bikelock.AutoSMSActivity;
import com.understandinggeek.bikelock.ui.CreateTripActivity;

public class CreateTripActivityTest extends ActivityInstrumentationTestCase2<CreateTripActivity> {

    public CreateTripActivityTest() {
        super(CreateTripActivity.class);
    }
    //this is some kind of crappy unit test i guess...
    public void testActivity() {
        //insturmentation allows me to interact with the app
        Instrumentation instrumentation = this.getInstrumentation();
        CreateTripActivity activity = this.getActivity();

        sendKeys("30");
        sendKeys("ENTER");
        
	
    }
}

