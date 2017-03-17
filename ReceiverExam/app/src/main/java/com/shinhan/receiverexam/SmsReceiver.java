package com.shinhan.receiverexam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    public static final String TAG = "SmsReceiver";
    @Override
    public void onReceive(Context context, Intent intent) { //수신한 sms가 넘어온다
         // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG,"onReceive()--------------!!!!!!!!");
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);
        if(messages != null && messages.length > 0){ //수신된 메세지가 있다면
            String sender = messages[0].getOriginatingAddress();
            String contents = messages[0].getMessageBody().toString();
            Log.i(TAG, "sender :"+sender+",contents:"+contents);
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[])bundle.get("puds");
        SmsMessage[] messages = new SmsMessage[objs.length];
        for(int i=0; i<objs.length; i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i], format);
            }
            else{
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
            }
        }
    }
}
