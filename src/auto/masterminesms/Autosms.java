package auto.masterminesms;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import auto.masterminesms.R;

import android.database.sqlite.SQLiteDatabase;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class Autosms extends Activity{
 
	Button save;
	TextView to, message;
	EditText datepck,timepck;
	CheckBox chkbox;
	SQLiteDatabase mydb;
	String too, messagge,date,time;
	
	Calendar smscalend = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto);
		
		save = (Button) findViewById(R.id.btsave);
		to = (TextView) findViewById(R.id.edtato);
		message = (TextView) findViewById(R.id.edtmessg);
		timepck = (EditText) findViewById(R.id.tmpicker);
		datepck = (EditText) findViewById(R.id.dtpicker);
		chkbox = (CheckBox) findViewById(R.id.chckbx);
		
		
		
		final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		    	smscalend.set(Calendar.YEAR, year);
		    	smscalend.set(Calendar.MONTH, monthOfYear);
		    	smscalend.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        DateLabel();
		    }

			private void DateLabel() {
				// TODO Auto-generated method stub
				 String formatt;
				    formatt= "MM/dd/yy"; 
				    SimpleDateFormat sdf = new SimpleDateFormat(formatt, Locale.US);

				    datepck.setText(sdf.format(smscalend.getTime()));
			}

		};
		
		
		 datepck.setOnClickListener(new View.OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            new DatePickerDialog(Autosms.this, d, smscalend
		                    .get(Calendar.YEAR), smscalend.get(Calendar.MONTH),
		                    smscalend.get(Calendar.DAY_OF_MONTH)).show();
		        }
		    });
		
		
		final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				smscalend.set(Calendar.HOUR_OF_DAY, hourOfDay);
				smscalend.set(Calendar.MINUTE, minute);
				//TimeLabel();
			}

			/*private void TimeLabel() {
				// TODO Auto-generated method stub
				

			    timepck.setText(hourOfDay + ":" + selectedMinute);
			} */
			};

		  
		  
		
         timepck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new TimePickerDialog(Autosms.this, t, smscalend
						.get(Calendar.HOUR_OF_DAY), smscalend
						.get(Calendar.MINUTE), true).show();
				
			}
		});
		
		
	
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					mydb= openOrCreateDatabase("smsinfo", MODE_PRIVATE,null);
					mydb.execSQL("CREATE TABLE IF  NOT EXISTS AutoSms (ID INTEGER PRIMARY KEY, Too TEXT, Message TEXT, Time TEXT, Date TEXT);");
				      }
					catch(Exception e){
			        Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG).show();
			        
			          }
				// TODO Auto-generated method stub
				too= to.getText().toString();
				messagge = message.getText().toString();
				time = timepck.getText().toString();
				date= datepck.getText().toString();
				
				mydb.execSQL("INSERT INTO AutoSms (Too, Message, Time, Date) VALUES('"+too+"','"+messagge+"','"+time+"','"+date+"')");
			}
			
		});
		
		
		
	}
	
	
	
	
	
	
}
