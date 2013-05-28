package com.understandinggeek.bikelock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageClickListener implements View.OnClickListener {

    private SmsManager smsManager;
    private String phoneNumber;

    public SendMessageClickListener(SmsManager smsManager,String phoneNumber) {
        this.smsManager = smsManager;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences((Activity) view.getContext());
        //to fix once I'm worked out where to put strings like this
        String sms = sharedPrefs.getString("prefReplyText", "I'm cycling");

        try {
            smsManager.sendTextMessage(phoneNumber, null, sms, null, null);
            Toast.makeText(view.getContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(),"SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
