package org.zbc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver {

	private String TAG = "SMSReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
			String sender = message.getOriginatingAddress();
			// String header= message.get
			String content = message.getMessageBody();

			if (content.contains("Homework") && content.contains(".txt")) {
				abortBroadcast();// here dectects sms belonging to this project,
									// the aboard it to make
									// other apps not be able to see it 
			}
			Toast.makeText(context,
					"Sms From Sender  " + sender + " : " + content,
					Toast.LENGTH_SHORT).show();
		}
	}
}
