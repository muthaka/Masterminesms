package auto.masterminesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent)  {
		Bundle bundle = intent.getExtras();
		SmsMessage[] mess= null;
		String str="";
		if (bundle !=null){
			Object[] pdus= (Object []) bundle.get("pdus");
			mess = new SmsMessage[pdus.length];
			for (int i=0; i<pdus.length; i++){
				mess[i]= SmsMessage.createFromPdu((byte []) pdus[i]);
				str += "SMS From" + mess[i].getOriginatingAddress();
				str += " :";
				str += mess[i].getMessageBody().toString();
				str +="\n";
			}
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction("SMS_RECEIVED_ACTION");
			broadcastIntent.putExtra("sms", str);
			context.sendBroadcast(broadcastIntent);
		}
	}
}
