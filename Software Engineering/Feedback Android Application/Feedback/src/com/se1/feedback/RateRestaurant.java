package com.se1.feedback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.net.Uri;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
/**
 * 
 * @author Akshay
 *	ClassName : RateRestaurant
 *  Description: This class is responsible for taking the user input for the restaurant and then puts it in database.
 *  If any of the user rating are below 2 then a sms notification is sent to the manager. 
 */
public class RateRestaurant extends Activity {
	RatingBar music_rating;
	RatingBar service_rating;
	RatingBar food_rating;
	RatingBar ambience_rating;
	
	Button rate;
	Button averageRating;
	EditText comments;
	
	String userId;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_restaurant);
		// Here the UI is being build
		music_rating = (RatingBar)findViewById(R.id.music_rating);
		music_rating.setRating(1.0f);
		
		service_rating = (RatingBar)findViewById(R.id.service_rating);
		service_rating.setRating(1.0f);
		
		food_rating = (RatingBar)findViewById(R.id.food_rating);
		food_rating.setRating(1.0f);
		
		ambience_rating = (RatingBar)findViewById(R.id.ambience_rating);
		ambience_rating.setRating(1.0f);
		
		rate = (Button)findViewById(R.id.rate);
		averageRating = (Button)findViewById(R.id.avgRating);
		comments = (EditText)findViewById(R.id.comments);
		userId =  getIntent().getSerializableExtra("UserId").toString();
		name = getIntent().getSerializableExtra("name").toString();
		final Context context = this;
		
		//This is a click event listener on the submit button which puts the data in the database. 
		rate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String message="";
				String result =insertUserRatings(userId);
				
				//After sucessful insertion of data this condition occurs
				if(result.trim().equals("Success")){
					//Here the sms content is being set
					String sms=name+" gave the rating for ";
					if(music_rating.getRating()<2.0 )
						sms+="Music ,";
					if(service_rating.getRating()<2.0)
						sms+="Service ,";
					if(food_rating.getRating()<2.0)
						sms+="Food ,";
					if(ambience_rating.getRating()<2.0)
						sms+="Ambience";
					
					
					if(music_rating.getRating()<2.0 || service_rating.getRating()<2.0 || food_rating.getRating()<2.0 || ambience_rating.getRating()<2.0)
					{
						String number =getNumberAdmin();
						String [] numberArray = number.split(";");
						for(String no:numberArray){
							sendSMS(no,sms+" below rating 2");
						}
						//sendEmail(sms+" below rating 2");
					}
					//sms content set is completed
					music_rating.setRating(0);
					service_rating.setRating(0);
					food_rating.setRating(0);
					ambience_rating.setRating(0);
					message="ThankYou for your ratings.";
					Toast msg = Toast.makeText(RateRestaurant.this,
							message, Toast.LENGTH_SHORT);
					msg.show();
					//This puts the screen again to the user Info page
					Intent intent = new Intent(context, UserInfoActivity.class);
	                startActivity(intent); 
				}else{
					Toast msg = Toast.makeText(RateRestaurant.this,
							"Error in posting ratings", Toast.LENGTH_SHORT);
					msg.show();
				}
				
			}
		
		});
		
		//Assign the onclick listner to on the average button to the webpage for the average rating
		averageRating.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://omega.uta.edu/~axm5553/admin/averagerating.php");
				Intent intent=new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
			}
			
		});
		
		
	}


	/**
	 * Method Name : insertUserRatings
	 * Description : This method is responsible for connecting to database and putting the users rating in the database.
	 * @return	   : A string returned from the database.
	 */
	public String insertUserRatings(String userId)
	{
		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://omega.uta.edu/~axm5553/admin/rating.php");
					//"http://10.0.2.2/mysql/rating.php");
			
			//Here the parameters are being sent along the url
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			pairs.add(new BasicNameValuePair("userid",userId));
			pairs.add(new BasicNameValuePair("musicRating",String.valueOf(music_rating.getRating())));
			pairs.add(new BasicNameValuePair("serviceRating",String.valueOf(service_rating.getRating())));
			pairs.add(new BasicNameValuePair("foodRating",String.valueOf(food_rating.getRating())));
			pairs.add(new BasicNameValuePair("ambienceRating",String.valueOf(ambience_rating.getRating())));
			pairs.add(new BasicNameValuePair("comments",String.valueOf(comments.getText())));
			
			
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
			//Success message 
			result =sb.toString();
			Log.i("INFO", result);
		} catch (IOException e) {
			Log.e("Error", e.toString());
			Toast msg = Toast.makeText(RateRestaurant.this,
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
	/**
	 * Method Name: sendSMS
	 * Description : This code is responsible for sending the sms
	 * @param phoneNumber
	 * @param message
	 */
	private void sendSMS(String phoneNumber, String message)
	{
	       SmsManager sms = SmsManager.getDefault();
	       sms.sendTextMessage(phoneNumber, null, message, null, null);
	}
	
	
	/**
	 * Method Name: getNumberAdmin
	 * Description : This method is responsible for getting the phone numbers from the admin table
	 * @return
	 */
	public String getNumberAdmin()
	{
		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet(
					"http://omega.uta.edu/~axm5553/admin/getNumber.php");
					//"http://10.0.2.2/mysql/rating.php");
			
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
			//Success message 
			result =sb.toString();
			result = result.substring(0,result.length()-1);
			Log.i("INFO", result);
		} catch (IOException e) {
			Log.e("Error", e.toString());
			Toast msg = Toast.makeText(RateRestaurant.this,
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
	
	
	/**
	 * MethodName: sendEmail
	 * Description : This email is for sending an email.
	 * @param message
	 */
	protected void sendEmail(String message) {
	      Log.i("INFO","inside email");
	     
	      String[] recipients = {"adityachandrashekar25@gmail.com"};
	      Intent email = new Intent(Intent.ACTION_SEND);
	      // prompts email clients only
	    

	      email.putExtra(Intent.EXTRA_EMAIL, recipients);
	      email.putExtra(Intent.EXTRA_SUBJECT, "Notification email from the user.");
	      email.setType("plain/text");
	      email.putExtra(Intent.EXTRA_TEXT, message);

	      try {
		    // the user can choose the email client
	         startActivity(email);
	         Log.i("INFO","After email activity");
	     
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(RateRestaurant.this, "No email client installed.",
	        		 Toast.LENGTH_LONG).show();
	      }
	   }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rate_restaurant, menu);
		return true;
	}

}
