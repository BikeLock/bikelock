package com.understandinggeek.bikelock;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class AutoResponse extends Service {


    //the action fired by the Android system when an SMS was received
    private static final String RECEIVED_ACTION =
                              "android.provider.Telephony.SMS_RECEIVED";
    private static final String SENT_ACTION="SENT_SMS";
    private static final String DELIVERED_ACTION="DELIVERED_SMS";
    private static final String auto_reply_tag="[auto reply]";

	private List<TripEvent> events;
	private boolean active = false;
    private SmsManager smsManager;
    String requester ="15555215553";
    String reply="";
    SharedPreferences myprefs;

    @Override
    public void onCreate() {
        super.onCreate();
       // myprefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.v("BikeLock", "oncreate service");
      
    
        registerReceiver(sentReceiver, new IntentFilter(SENT_ACTION));
        registerReceiver(deliverReceiver,
                           new IntentFilter(DELIVERED_ACTION));

        IntentFilter filter = new IntentFilter(RECEIVED_ACTION);
        registerReceiver(receiver, filter);

        IntentFilter attemptedfilter = new IntentFilter(SENT_ACTION);
        registerReceiver(sender,attemptedfilter);
        
        
        
		this.smsManager = SmsManager.getDefault();
    	Log.v("BikeLock", "New Trip");
		
		active = true;
		
		events = new ArrayList<TripEvent>();

		//events.add(new TripEvent("First event\n"));
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				active = false;
			}
		}, 60000);
		// Just active for one minute at the moment
 
    }

    private BroadcastReceiver sender = new BroadcastReceiver(){
        @Override
        public void onReceive(Context c, Intent i) {
            if(i.getAction().equals(SENT_ACTION)) {
                if(getResultCode() != Activity.RESULT_OK) {
                    String recipient = i.getStringExtra("recipient");
                    requestReceived(recipient);
                }
            }
        }
    };
    BroadcastReceiver sentReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context c, Intent in) {
            switch(getResultCode()) {
                case Activity.RESULT_OK:
                    //sent SMS message successfully;
                    smsSent();
                    break;
                default:
                    //sent SMS message failed
                    smsFailed();
                    break;
             }
         }
    };

    public void smsSent() {
        // Toast.makeText(this, "SMS sent", Toast.LENGTH_SHORT);
    }
    public void smsFailed() {
      //  Toast.makeText(this, "SMS sent failed", Toast.LENGTH_SHORT);

    }
    public void smsDelivered() {
    //    Toast.makeText(this, "SMS delivered", Toast.LENGTH_SHORT);
    }

    BroadcastReceiver deliverReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context c, Intent in) {
            //SMS delivered actions
            smsDelivered();
        }
    };

    public void requestReceived(String f) {
        Log.v("ResponderService","In requestReceived");
        requester=f;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent in) {
            Log.v("ResponderService","On Receive");
            reply="";
            if(in.getAction().equals(RECEIVED_ACTION)) {
                Log.v("ResponderService","On SMS RECEIVE");

                Bundle bundle = in.getExtras();
                if(bundle!=null) {
                    Object[] pdus = (Object[])bundle.get("pdus");
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for(int i = 0; i<pdus.length; i++) {
                        Log.v("ResponderService","FOUND MESSAGE");
                        messages[i] =
                                SmsMessage.createFromPdu((byte[])pdus[i]);
                    }
                    for(SmsMessage message: messages) {
                        Log.v("ResponderService",message.getOriginatingAddress());
                        String incoming_text = message.toString();
                    	//requestReceived("15555215556");
                       requestReceived(message.getOriginatingAddress());
                       
	                   	if (active && !(incoming_text.contains(auto_reply_tag)) ) {
	               			events.add(new TripEvent(incoming_text));
	               	    	respond();
	                   		Log.v("BikeLock", "Incoming message:"+ message.toString());
	                   	}
                    }
                    
                    
                    
                }
            }
        }
    };

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    
	public boolean isActive() {
		return active;
	}

    public void respond() {
    	//requester ="15555215556";
        
    	 Log.v("BikeLock","respond");
        reply ="I'm riding my bike.\n"+ auto_reply_tag;
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(requester, null, reply, null, null);
        
        Intent sentIn = new Intent(SENT_ACTION);
        PendingIntent sentPIn = PendingIntent.getBroadcast(this,
                                                                0,sentIn,0);

        Intent deliverIn = new Intent(DELIVERED_ACTION);
        PendingIntent deliverPIn = PendingIntent.getBroadcast(this,
                                                           0,deliverIn,0);
        ArrayList<String> Msgs = sms.divideMessage(reply);
        ArrayList<PendingIntent> sentIns = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliverIns =
                                         new ArrayList<PendingIntent>();

        for(int i=0; i< Msgs.size(); i++) {
            sentIns.add(sentPIn);
            deliverIns.add(deliverPIn);
        }
        Log.v("ResponderService","send response");
    //    sms.sendTextMessage(requester, null, reply, null, null);
      //  sms.sendMultipartTextMessage(requester, null,
        //                                Msgs, sentIns, deliverIns);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(sender);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}