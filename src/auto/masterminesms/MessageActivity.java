package auto.masterminesms;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {
	String too, msg;
    IntentFilter intentfill;
	Button Send;
	EditText To, Message;
	
	private BroadcastReceiver intentrec = new BroadcastReceiver (){
		public void onReceive(Context cont, Intent inte){
			TextView smsshow = (TextView) findViewById(R.id.txtreceiv);
			smsshow.setText(inte.getExtras().getString("sms"));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		intentfill = new IntentFilter();
		intentfill.addAction("SMS_RECEIVED_ACTION");
		Send = (Button) findViewById(R.id.sendd);

		Send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				To = (EditText) findViewById(R.id.phonee);
				Message = (EditText) findViewById(R.id.messagee);
				too = To.getText().toString();
				msg = Message.getText().toString();
				
				 if ((too.toString() != null) && (msg == null)){
					Toast.makeText(null, "INPUT MESSAGE", Toast.LENGTH_SHORT).show();
				}
				else if ((too.toString() == null) && (msg != null)){
					Toast.makeText(null, "INPUT PHONE", Toast.LENGTH_SHORT).show();
				}
				else if ((too.toString() != null) && (msg != null)){
					Sendsms(too, msg);
				}
				
				
				To.setText("");
				Message.setText("");
			}
		});

	}
	
	protected void onResume(){
		registerReceiver(intentrec, intentfill);
		super.onResume();
	}
	
	protected void onPause(){
		unregisterReceiver(intentrec);
		super.onPause();
	}

	public void Sendsms(String phonenumber, String Messag) {
		String SENT="SMS_SENT";
		String DELIVERED="SMS_DELIVERED";
		PendingIntent sentPI  = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI= PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		
		registerReceiver(new BroadcastReceiver (){
		public void onReceive(Context arg0, Intent arg1){
			switch (getResultCode ())
			{
			case Activity.RESULT_OK:
				Toast.makeText(getBaseContext(), "SMS SENT", Toast.LENGTH_SHORT).show();
				break;
				
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				Toast.makeText(getBaseContext(), "GENERIC FAILURE", Toast.LENGTH_SHORT).show();
				break;
				
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				Toast.makeText(getBaseContext(), "NO Service", Toast.LENGTH_SHORT).show();
				break;
				
			case SmsManager.RESULT_ERROR_NULL_PDU:
				Toast.makeText(getBaseContext(), "Null PDUT", Toast.LENGTH_SHORT).show();
				break;
				
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				Toast.makeText(getBaseContext(), "Radio OFF", Toast.LENGTH_SHORT).show();
				break;
			}
		}
				
		
		}, new IntentFilter(SENT));
		
		registerReceiver(new BroadcastReceiver(){
			public void onReceive(Context arg0, Intent arg1){
				switch (getResultCode()) 
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_SHORT).show();
					break;
					
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not Delivered", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));
		
		
		SmsManager sms = SmsManager.getDefault();
		too = phonenumber;
		msg = Messag;
		sms.sendTextMessage(too, null, msg, sentPI, deliveredPI);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}

}
