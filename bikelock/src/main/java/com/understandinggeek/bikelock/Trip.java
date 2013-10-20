/**
 * 
 */
package com.understandinggeek.bikelock;

import java.util.*;

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
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;



/**
 * @author duncanwalker
 * 
 */
public class Trip extends Service  {
	private long predictedDuration;
	private List<TripEvent> events;
	private boolean active = false;
    private SmsManager smsManager;
    private String phoneNumber;
    private Context app;
    private static final String auto_reply_tag="[auto reply]";

    
    
    //the action fired by the Android system when an SMS was received
    private static final String RECEIVED_ACTION =
                              "android.provider.Telephony.SMS_RECEIVED";
    private static final String SENT_ACTION="SENT_SMS";
    private static final String DELIVERED_ACTION="DELIVERED_SMS";

    String requester;
    String reply="";
    SharedPreferences myprefs;
    
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
                        requestReceived(message.getOriginatingAddress());
                    }
                    respond();
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    	Log.v("BikeLock", "creating service");
       // myprefs = PreferenceManager.getDefaultSharedPreferences(this);

        registerReceiver(sentReceiver, new IntentFilter(SENT_ACTION));
        registerReceiver(deliverReceiver,
                           new IntentFilter(DELIVERED_ACTION));

        IntentFilter filter = new IntentFilter(RECEIVED_ACTION);
        registerReceiver(receiver, filter);

        IntentFilter attemptedfilter = new IntentFilter(SENT_ACTION);
        registerReceiver(sender,attemptedfilter);
        
    	Log.v("BikeLock", "recievers registered");
    }



    public void smsSent() {
        Toast.makeText(this, "SMS sent", Toast.LENGTH_SHORT);
    }
    public void smsFailed() {
        Toast.makeText(this, "SMS sent failed", Toast.LENGTH_SHORT);

    }
    public void smsDelivered() {
        Toast.makeText(this, "SMS delivered", Toast.LENGTH_SHORT);
    }

    BroadcastReceiver deliverReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context c, Intent in) {
            //SMS delivered actions
            smsDelivered();
        }
    };





    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public void respond() {
        Log.v("ResponderService","Responding to " + requester);
        reply = 
                           "Thank you for your message. I am busy now."
                           + "I will call you later.";
        SmsManager sms = SmsManager.getDefault();
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

        sms.sendMultipartTextMessage(requester, null,
                                        Msgs, sentIns, deliverIns);
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
    
    
	public Trip( long duration) {
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
		}, duration);
		
	
	}

	
    public void requestReceived(String f) {
        Log.v("ResponderService","In requestReceived");
        requester=f;
    }

    
	public Trip( Context context, long duration) {

		this(duration);
		this.app = context;
	}
	
	

	public List<TripEvent> getEvents() {
		if (events == null)
			return Collections.emptyList();
		return events;
	}
	
	public int countEvents() {
		return events.size();
	}


	
	



	public void onReceive(Context context, Intent in) {
		Log.v("ResponderService","On Receive");
        String reply="I'm riding my bike.\n"+ auto_reply_tag;
        if(in.getAction().equals(RECEIVED_ACTION)) {
            Log.v("ResponderService","On SMS RECEIVE");
            Log.v("BikeLock", "Incoming message:"+ in.getExtras().toString());
            Bundle bundle = in.getExtras();
            if(bundle!=null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i = 0; i<pdus.length; i++) {
                    Log.v("ResponderService","FOUND MESSAGE");
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                for(SmsMessage message: messages) {
                	String incoming_text = message.toString();
                	
                	if (active && !(incoming_text.contains(auto_reply_tag)) ) {
            			events.add(new TripEvent(incoming_text));
            	    	sendReply((message.getOriginatingAddress()), reply);
                		Log.v("BikeLock", "Incoming message:"+ message.toString());
                	}
                } 
        	
            }
        }
		
	}

	private void sendReply(String destination,String message) {
			try {
	            smsManager.sendTextMessage(destination, null, message, null, null);
	            Log.v("BikeLock", message);
	            
	           // Toast.makeText(view.getContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            //Toast.makeText(view.getContext(),"SMS faild, please try again later!", Toast.LENGTH_LONG).show();
	            e.printStackTrace();
	        }
		
	}
	
	
   

	public boolean isActive() {
		return active;
	}




}
