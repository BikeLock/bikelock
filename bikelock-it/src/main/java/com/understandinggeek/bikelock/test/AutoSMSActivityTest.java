package com.understandinggeek.bikelock.test;

import android.test.ActivityInstrumentationTestCase2;
import com.understandinggeek.bikelock.*;

public class AutoSMSActivityTest extends ActivityInstrumentationTestCase2<AutoSMSActivity> {

    public AutoSMSActivityTest() {
        super(AutoSMSActivity.class); 
    }

    public void testActivity() {
        AutoSMSActivity activity = getActivity();
        assertNotNull(activity);
    }
}

