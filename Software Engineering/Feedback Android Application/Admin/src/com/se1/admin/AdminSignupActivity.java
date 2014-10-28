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

import com.se1.admin.util.Util;
import com.se1.admin.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Akshay Mattoo
 * Class Name  : AdminSignupActivity
 * Description : This class is responsible for the showing up the sign up screen for registering the admin.
 */
public class AdminSignupActivity extends Activity {

	EditText first_name;
	EditText last_name;
	EditText user_name;
	EditText password;
	EditText confirm_password;
	EditText phone_number;
	EditText email_id;
	Button sign_up;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_signup);
		
		first_name = (EditText)findViewById(R.id.firstname);
		last_name = (EditText)findViewById(R.id.lastname);
		user_name = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		confirm_password = (EditText)findViewById(R.id.confirm_password);
		phone_number = (EditText)findViewById(R.id.phone_number);
		email_id = (EditText)findViewById(R.id.email_id);
		sign_up = (Button)findViewById(R.id.signup);
		final Context context = this;
		
		//Assigns the clicklistner signup button.
		sign_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String message="";
				
				//Validation for the proper input from the admin.
				if(!Util.isValidText(String.valueOf(first_name.getText()))){
					first_name.setError("Enter First name properly.Allowed 3 to 15 characters with (.)and( _ )");
					return;
				}
				
				if(!Util.isValidText(String.valueOf(last_name.getText()))){
					last_name.setError("Enter last name properly.Allowed 3 to 15 characters with (.)and( _ )");
					return;
				}
				
				if(!Util.isValidText(String.valueOf(user_name.getText()))){
					user_name.setError("Enter user name properly.Allowed 3 to 15 characters with (.)and( _ )");
					return;
				}
				
				if(!Util.isValidPassword(String.valueOf(password.getText()))){
					password.setError("Enter password properly.Allowed 3 to 15 characters with (.) ( _ )( @ )");
					return;
				}
				
				if(!String.valueOf(password.getText()).equals(String.valueOf(confirm_password.getText()))){
					confirm_password.setError("Password doesnot match");
					return;
				}
				
				if(!Util.isNumberValid(String.valueOf(phone_number.getText()))){
					phone_number.setError("Enter a valid phone number");
					return;
				}
				
				if(!Util.isEmailValid(String.valueOf(email_id.getText()))){
					email_id.setError("Enter a valid email id");
					return;
				}
				
				
				String result =registerManager();
				
				if(result.trim().equals("Success")){
					first_name.setText("");
					last_name.setText("");
					user_name.setText("");
					password.setText("");
					confirm_password.setText("");
					phone_number.setText("");
					email_id.setText("");
				
					message="Admin registered successfully";
					Toast msg = Toast.makeText(AdminSignupActivity.this,
							message, Toast.LENGTH_SHORT);
					msg.show();
					Intent intent = new Intent(context, AdminActivity.class);
	                startActivity(intent); 
				}
			}
		});
	}
	
	/**
	 * Method Name : registerManager
	 * Description : This method registers the admin to the database.
	 * @return     : String from the database.
	 */
	public String registerManager()
	{
		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://omega.uta.edu/~axm5553/admin/insertManager.php");
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			
			pairs.add(new BasicNameValuePair("firstname",String.valueOf(first_name.getText())));
			pairs.add(new BasicNameValuePair("lastname",String.valueOf(last_name.getText())));
			pairs.add(new BasicNameValuePair("username",String.valueOf(user_name.getText())));
			pairs.add(new BasicNameValuePair("password",String.valueOf(password.getText())));
			pairs.add(new BasicNameValuePair("number",String.valueOf(phone_number.getText())));
			pairs.add(new BasicNameValuePair("email",String.valueOf(email_id.getText())));
			
			
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
			Toast msg = Toast.makeText(AdminSignupActivity.this,
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
	 

}
