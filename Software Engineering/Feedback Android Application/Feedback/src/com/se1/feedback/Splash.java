package com.se1.feedback;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
/**
 * @author Akshay Mattoo
 * This class is responsible for splash screen which comes before the application 
 */
public class Splash extends Activity {
	MediaPlayer voice;
protected void onCreate (Bundle VidhyadharVenkatraman){
	super.onCreate(VidhyadharVenkatraman);
	setContentView(R.layout.splash);
	voice= MediaPlayer.create(Splash.this, R.raw.feedback);
	voice.start();
	Thread t1=new Thread()
	{
		public void run(){
			try{
				sleep(3000);
			}catch (InterruptedException e){
					e.printStackTrace();
				}finally {
					Intent i1=new Intent("com.se1.feedback.USERINFOACTIVITY");
					startActivity(i1);
			}
		}
	};
	t1.start();
}
}
