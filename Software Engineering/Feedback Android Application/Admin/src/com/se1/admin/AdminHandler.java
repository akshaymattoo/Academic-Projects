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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Akshay Mattoo
 * Class Name  : Admin Handler
 * Description : This class is the next screen after the admin successfully logs in.
 *               It has two buttons View Details where admin can view his/her detials 
 *				 Update Details where admin can update the details.
 */
public class AdminHandler extends Activity{

	TextView view_det_text;
	TextView update_det_text;
	Button view_details;
	Button update_details;
	Button logout;
	Admin admin=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_handler);
		view_details = (Button)findViewById(R.id.view);
		update_details = (Button)findViewById(R.id.update_d);
		logout = (Button)findViewById(R.id.button1);
		view_det_text = (TextView)findViewById(R.id.logout);
		update_det_text = (TextView)findViewById(R.id.textView2);
		admin = (Admin) getIntent().getSerializableExtra("AdminObject");
		
		final Context context = this;
		
		//Assigns the clicklistner on view details button to show the next screen.
		view_details.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String result = getDetailsAdmin();
				if(result.length()>7)
				{
					Intent intent = new Intent(context, AdminViewDetails.class);
					intent.putExtra("Result", result);
	                startActivity(intent);
				}
			}
		});
		
		//Assigns the clicklistner on update details button to show the next screen.
		update_details.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdminUpdateActivity.class);
				intent.putExtra("AdminObject", admin);
                startActivity(intent);
			
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
	 * Method Name : getDetailsAdmin
	 * Description : This function get the details of the admin from the database.
	 * @return     : String value from the database.
	 */
	private String getDetailsAdmin()
	{

		String result="";
		BufferedReader reader=null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			//Makes a post request to the omega server.
			HttpPost httppost = new HttpPost(
					"http://omega.uta.edu/~axm5553/admin/viewDetails.php");
			
			// Adds the parameters username and password with the request.
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();			
			pairs.add(new BasicNameValuePair("username",admin.getUserName()));
			pairs.add(new BasicNameValuePair("password",admin.getPassword()));
			
			httppost.setEntity(new UrlEncodedFormEntity(pairs));
			// Response from the request.
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
			
		} catch (IOException e) {
			Log.e("Error", e.toString());
			Toast msg = Toast.makeText(AdminHandler.this,
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}
}
