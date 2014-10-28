package com.se1.admin;

import com.se1.admin.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * @author Akshay Mattoo
 * Class Name  : AdminViewDetails
 * Description : This class displays information about the admin ,getting the data from the database.
 */
public class AdminViewDetails extends Activity {

	TextView first_name;
	TextView first_name_set;
	TextView last_name;
	TextView last_name_set;
	TextView user_name;
	TextView user_name_set;
	TextView phone_number;
	TextView phone_number_set;
	TextView email;
	TextView email_set;
	TextView admin_details;
	Button logout;
	String result;
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_admin_view_details);
    	
    	first_name = (TextView)findViewById(R.id.v_first_name);
    	first_name_set = (TextView)findViewById(R.id.v_fn);
    	last_name = (TextView)findViewById(R.id.v_last_name);
    	last_name_set = (TextView)findViewById(R.id.v_ln);
    	user_name = (TextView)findViewById(R.id.v_user_name);
    	user_name_set = (TextView)findViewById(R.id.v_un);
    	phone_number = (TextView)findViewById(R.id.v_phone_number);
    	phone_number_set = (TextView)findViewById(R.id.v_pn);
    	email = (TextView)findViewById(R.id.v_email);
    	email_set = (TextView)findViewById(R.id.v_em);
    	admin_details = (TextView)findViewById(R.id.admin_details);
    	logout = (Button)findViewById(R.id.Logout);
    	
    	final Context context = this;
    	result = (String) getIntent().getSerializableExtra("Result");
    	String [] resultArray= result.split(";");
    	
    	//sets the text retrieved from the database on the screen.
    	first_name_set.setText(resultArray[0]);
    	last_name_set.setText(resultArray[1]);
    	user_name_set.setText(resultArray[2]);
    	phone_number_set.setText(resultArray[3]);
    	email_set.setText(resultArray[4]);
    	
    	//Assign the onclick listner to on the logout button to get back to the login screen
    	logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdminActivity.class);
                startActivity(intent);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_view_details, menu);
        return true;
    }
    
}
