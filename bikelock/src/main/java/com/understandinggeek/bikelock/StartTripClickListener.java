package com.understandinggeek.bikelock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class StartTripClickListener implements View.OnClickListener {

    private Long predictedDuration;

    public StartTripClickListener(Long predictedDuration) {
        this.predictedDuration = predictedDuration;
    }

    @Override
    public void onClick(View view) {
        Trip trip = new Trip(new Chronometer(view.getContext()), predictedDuration);

    }
}
