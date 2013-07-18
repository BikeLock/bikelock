package com.understandinggeek.bikelock.test;

import android.app.Instrumentation;
import android.telephony.SmsManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import com.understandinggeek.bikelock.*;

public class AutoSMSActivityTest extends ActivityInstrumentationTestCase2<AutoSMSActivity> {

    public AutoSMSActivityTest() {
        super(AutoSMSActivity.class); 
    }
    //this is some kind of crappy unit test i guess...
    public void testActivity() {
        //insturmentation allows me to interact with the app
        Instrumentation instrumentation = this.getInstrumentation();
        AutoSMSActivity activity = this.getActivity();

    }
}

