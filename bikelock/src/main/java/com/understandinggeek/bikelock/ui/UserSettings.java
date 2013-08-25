package com.understandinggeek.bikelock.ui;

import com.understandinggeek.bikelock.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserSettings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.settings);
 
    }
}
