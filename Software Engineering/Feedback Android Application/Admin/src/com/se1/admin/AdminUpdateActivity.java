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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * @author Akshay Mattoo
 * Class Name  : AdminUpdateActivity
 * Description : This class displays the update screen where the admin can update the details.
 */
public class AdminUpdateActivity extends Activity {

	EditText phone_number;
	EditText email_id;
	EditText comments;
	Button update;
	Button logout;
	Admin admin;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_update);
		
		phone_number = (EditText)findViewById(R.id.phone_number);
		email_id = (EditText)findViewById(R.id.email_id);
		comments = (EditText)findViewById(R.id.comments);
		update = (Button)findViewById(R.id.update);
		logout = (Button)findViewById(R.id.button1);
		//Deserializes the object admin to get the username .
		admin = (Admin) getIntent().getSerializableExtra("AdminObject");
		
		final Context context = this;
		//Assigns the clicklistner on the update button
		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String message="";
				//Validations for the proper user input
				if(!Util.isNumberValid(String.valueOf(phone_number.getText()))){
					phone_number.setError("Please enter valid phone number.Requires 10 digits");
					return;
				}
				
				if(!Util.isEmailValid(String.valueOf(email_id.getText()))){
					email_id.setError("Please enter valid email id");
					return;
				}
				
				if(!Util.isCommentsValid(String.valueOf(comments.getText()))){
					comments.setError("Enter proper comments.Atleast 10 characters");
					return;
				}
				
				String result =updateInformationForManager();
				
				if(result.trim().equals("Success")){
					phone_number.setText("");
					email_id.setText("");
					comments.setText("");
					
					message="Details updated successfully";
					Toast msg = Toast.makeText(AdminUpdateActivity.this,
							message, Toast.LENGTH_SHORT);
					msg.show();
					
					Intent intent = new Intent(context, AdminHandler.class);//Goes back to the handler screen on sucessful update
					intent.putExtra("AdminObject", admin);
	                startActivity(intent);
					
				}
					
			}
		});
		
		//Assign the onclick listner to on the logout button to get back to the login screen
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdminActivity.class);
                startActivity(intent);
			}
		});
	}

	/**
	 * Method Name : updateInformationForManager
	 * Description : This method takes the input from the user and updates the information in the database.
	 * @return     : String from the database
	 */
	public String updateInformationForManager()
	{
		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://omega.uta.edu/~axm5553/admin/updateManager.php");
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			
			pairs.add(new BasicNameValuePair("username",admin.getUserName()));
			pairs.add(new BasicNameValuePair("password",admin.getPassword()));
			pairs.add(new BasicNameValuePair("number",String.valueOf(phone_number.getText())));
			pairs.add(new BasicNameValuePair("email",String.valueOf(email_id.getText())));
			
			//Log.i("INFO","username"+user.getUser_name()+"  password"+user.getPassword()+"phone "+String.valueOf(phone_number.getText())+" email "+String.valueOf(email_id.getText()));
			
			
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
			
		} catch (IOException e) {
			Log.e("Error", e.toString());
			Toast msg = Toast.makeText(AdminUpdateActivity.this,
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manager_update, menu);
		return true;
	}

}
