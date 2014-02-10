package auto.masterminesms;
import auto.masterminesms.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
public class Menu extends Activity{
    Button sendsms, autosms, viewsms;
    SQLiteDatabase mydb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		sendsms = (Button) findViewById(R.id.btnsendsms);
		autosms = (Button) findViewById(R.id.btnautosms);
		viewsms = (Button) findViewById(R.id.btnviewsms);
		
		try{
			mydb= openOrCreateDatabase("smsinfo", MODE_PRIVATE,null);
			mydb.execSQL("CREATE TABLE IF  NOT EXISTS AutoSms (ID INTEGER PRIMARY KEY, Too TEXT, Message TEXT, Time TEXT, Date TEXT);");
		      }
			catch(Exception e){
	        Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);
	          }
		
		sendsms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent sendint = new Intent("auto.masterminesms.SENDSMS");
				startActivity(sendint);
			}
		});
		
		autosms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent autoint = new Intent("auto.masterminesms.AUTOSMS");
				startActivity(autoint);
			}
		});
		
		viewsms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try{
			           
		            Cursor c  = mydb.rawQuery("SELECT * FROM  AutoSms", null);
		            int count = c.getCount();
		            c.moveToFirst();
		            TableLayout smstablelayout = new TableLayout(getApplicationContext());
		            smstablelayout.setVerticalScrollBarEnabled(true);
		            TableRow smstableRow;
		            TextView tto, ttext, mtext,  mmess, tmtext, ttime, dtext, ddate;
		                    
		                     
		                    
		 
		                   
		                    smstableRow = new TableRow(getApplicationContext());
		                    tto = new TextView(getApplicationContext());
		                    tto.setText("To");
		                    tto.setTextColor(Color.RED);
		                    tto.setPadding(20, 20, 20, 20);
		                    tto.setTypeface(null, Typeface.BOLD);
		                    smstableRow.addView(tto);
		                    
		                    mmess = new TextView(getApplicationContext());
		                    mmess.setText("Message");
		                    mmess.setTextColor(Color.RED);
		                    mmess.setPadding(20, 20, 20, 20);
		                    mmess.setTypeface(null, Typeface.BOLD);
		                    smstableRow.addView(mmess);
		                    
		                    ttime = new TextView(getApplicationContext());
		                    ttime.setText("Time");
		                    ttime.setTextColor(Color.RED);
		                    ttime.setPadding(20, 20, 20, 20);
		                    ttime.setTypeface(null, Typeface.BOLD);
		                    smstableRow.addView(ttime);
		                    
		                    ddate = new TextView(getApplicationContext());
		                    ddate.setText("Date");
		                    ddate.setTextColor(Color.RED);
		                    ddate.setPadding(20, 5, 0, 5);
		                    ttime.setTypeface(null, Typeface.BOLD);
		                    smstableRow.addView(ddate);
		                    smstablelayout.addView(smstableRow);
		                    
		                    for(Integer j=0; j<count; j++)
		                    {
		                    	smstableRow = new TableRow(getApplicationContext());
		                    	ttext = new TextView(getApplicationContext());
		                    	ttext.setText(c.getString(c.getColumnIndex("too")));
		                    	ttext.setPadding(20, 20, 20, 20);
		                    	smstableRow.addView(ttext);
		                    	mtext = new TextView(getApplicationContext());
		                    	mtext.setText(c.getString(c.getColumnIndex("messagge")));
		                    	mtext.setPadding(20, 20, 20, 20);
		                    	smstableRow.addView(mtext);
		                    	tmtext = new TextView(getApplicationContext());
		                    	tmtext.setText(c.getString(c.getColumnIndex("time")));
		                    	tmtext.setPadding(20, 20, 20, 20);
		                    	smstableRow.addView(tmtext);
		                    	dtext = new TextView(getApplicationContext());
		                    	dtext.setText(c.getString(c.getColumnIndex("date")));
		                    	dtext.setPadding(20, 20, 20, 20);
		                    	smstableRow.addView(dtext);
		                    	smstablelayout.addView(smstableRow);
		                    	c.moveToNext();
		                    }
		                    setContentView(smstablelayout);
		           		   
		            
		         }catch(Exception e){
		            Toast.makeText(getApplicationContext(), "Error encountered.", Toast.LENGTH_LONG);
		        }
			 mydb.close();
			}
		});
		
	}

}
