package com.understandinggeek.bikelock;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AutoSMSActivity extends Activity {
 
	Button buttonSend;
	EditText textPhoneNo;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_sms);

        buttonSend = (Button) findViewById(R.id.buttonSend);
		textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        buttonSend.setOnClickListener(new SendMessageClickListener(SmsManager.getDefault(), textPhoneNo.toString()));
	}

    /** Called when the user clicks the Settings button */
	public void displaySettings(View view) {
	    Intent intent = new Intent(this, UserSettings.class);
	    startActivity(intent);
	}

    public Button getButtonSend() {
        return buttonSend;
    }
}
