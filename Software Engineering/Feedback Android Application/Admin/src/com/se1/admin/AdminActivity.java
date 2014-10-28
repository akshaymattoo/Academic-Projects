package com.se1.admin;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.se1.admin.R;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @author Akshay Mattoo
 * Class Name  : AdminActivity
 * Description : This is the main class from where the program starts.
 * 				 The page has the login page and signup button
 */

public class AdminActivity extends Activity {

	Button login;
	Button sign_up;
	EditText user_name;
	EditText password;
	Admin admin;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * This is a android method where the activity starts
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager);
		//Strict policies set for the ginger bread 
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		login = (Button) findViewById(R.id.login);
		sign_up = (Button)findViewById(R.id.signup);
		user_name = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		admin = Admin.getInstance();
		
		final Context context = this;
		
		//Assigns a clicklistner to the login button
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String result =checkUserPassword();
				String message="";
				
				if(result.trim().equals("Success")){
					user_name.setText("");
					password.setText("");
					user_name.requestFocus();
					Intent intent = new Intent(context, AdminHandler.class);//Changes the screen to handler screen on success of login
					intent.putExtra("AdminObject", admin);//Appends the admin object and passes to the other screen
	                startActivity(intent);
				}
				else{
					//Builds a toast message on error of login.
					if(result.trim().contains("MySQL"))
						message="Database is down";
					else
						message="Not a valid Username , Password.";
					Toast msg = Toast.makeText(AdminActivity.this,
							message, Toast.LENGTH_LONG);
					msg.show();
				}
			}
		});
		
		//Assigns a clicklistner to the signup button.
		sign_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdminSignupActivity.class);
                startActivity(intent); 
			}
		});
	}
 
	/**
	 * Method Name : checkUserPassword
	 * Description : This method is responsible for connecting to database and checking whether the user is present in database or not.
	 * @return	   : A string returned from the database.
	 */
	public String checkUserPassword()
	{
		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://omega.uta.edu/~axm5553/admin/checkUserPassword.php");
			//  http://10.0.2.2/mysql/checkUserPassword.php
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			admin.setUserName(String.valueOf(user_name.getText()));
			admin.setPassword(String.valueOf(password.getText()));
			
			pairs.add(new BasicNameValuePair("username",String.valueOf(user_name.getText())));
			pairs.add(new BasicNameValuePair("password",String.valueOf(password.getText())));
			
			httppost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			InputStream is = entity.getContent();

			reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println("line :" + line);
				sb.append(line + "\n");
			}
			
			is.close();
			result =sb.toString();
			Log.i("INFO",result);
		} catch (IOException e) {
			Log.e("Error", e.toString());
			Toast msg = Toast.makeText(AdminActivity.this,
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
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manager, menu);
		return true;
	}

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
