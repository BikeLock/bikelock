package com.understandinggeek.bikelock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import static java.lang.Long.valueOf;

public class CreateTripActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       Button startButton = (Button) findViewById(R.id.startButton);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.tripDuration);

        startButton.setOnClickListener(new StartTripClickListener(valueOf(numberPicker.getValue())));
    }
}
