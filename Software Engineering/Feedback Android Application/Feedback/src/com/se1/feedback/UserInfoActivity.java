package com.se1.feedback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * Class Name  : UserInfoActivity
 * Description : This class is reposible for takig the information about the user and putting it in database
 * @author Akshay
 *
 */
public class UserInfoActivity extends Activity {

	EditText name;
	EditText phone_number;
	EditText email_id;
	DatePicker dpResult;
	Button proceed;
	
	private ImageButton ib;
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private EditText date;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		//Make the UI for the screen
		name = (EditText)findViewById(R.id.name);
		phone_number = (EditText)findViewById(R.id.phone_number);
		email_id = (EditText)findViewById(R.id.email_id);
		proceed = (Button)findViewById(R.id.proceed);
		ib = (ImageButton) findViewById(R.id.dateButton);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		date = (EditText) findViewById(R.id.date);
		date.setKeyListener(null);
		final Context context = this;
		//This event click event listener id for the date dialog
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		
		});
		//This click event listner is for the button clicked proceed.
		proceed.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//validations for the name and email
				
				if(!Util.isValidText(String.valueOf(name.getText()))){
					name.setError("Enter valid name.");
					return;
				}
				if(!Util.isEmailValid(String.valueOf(email_id.getText()))){
					email_id.setError("Enter valid email id");
					return;
				}
				
				
				String userId= UUID.randomUUID().toString();
				String result =insertUserDetails(userId);
				
				if(result.trim().equals("Success")){
					Intent intent = new Intent(context, RateRestaurant.class);
					intent.putExtra("UserId", userId);//Appends the userid and passes to the other screen
					intent.putExtra("name", String.valueOf(name.getText()));//appends the username
	                startActivity(intent); 
	                
	                name.setText("");
					phone_number.setText("");
					email_id.setText("");
					date.setText("");
				}else{
					Toast msg = Toast.makeText(UserInfoActivity.this,
							"error in database", Toast.LENGTH_SHORT);
					msg.show();
				}
				
			}
		
		});
	}
	

	/**
	 * Method Name : insertUserDetails
	 * Description : This method is responsible for connecting to database and putting the users information in the database.
	 * @return	   : A string returned from the database.
	 */
	public String insertUserDetails(String userId)
	{
		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://omega.uta.edu/~axm5553/admin/insertUser.php");
					//"http://10.0.2.2/mysql/insertUser.php");
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			if(String.valueOf(phone_number.getText()).equals(""))
				phone_number.setText("0");
			 
			
			if(String.valueOf(date.getText()).equals(""))
				date.setText("0000-00-00");
			
			pairs.add(new BasicNameValuePair("userid",userId));
			pairs.add(new BasicNameValuePair("name",String.valueOf(name.getText())));
			pairs.add(new BasicNameValuePair("number",String.valueOf(phone_number.getText())));
			pairs.add(new BasicNameValuePair("email",String.valueOf(email_id.getText())));
			pairs.add(new BasicNameValuePair("date",String.valueOf(date.getText())));
			
			
			httppost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			InputStream is = entity.getContent();

			reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				//System.out.println("line :" + line);
				sb.append(line + "\n");
			}
			
			is.close();
			result =sb.toString();
			Log.i("INFO", result);
		} catch (IOException e) {
			Log.e("Error", e.toString());
			Toast msg = Toast.makeText(UserInfoActivity.this,
					e.toString(), Toast.LENGTH_SHORT);
			msg.show();
		}finally{
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	return result;
		
	}
	 
	//This code is responsible for the creating a dialog box for the datepicker
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			date.setText(selectedYear+"-"+(selectedMonth+1) +"-"+ selectedDay);
		}
	};

}
