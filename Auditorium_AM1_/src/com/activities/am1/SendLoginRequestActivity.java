package com.activities.am1;

import com.activities.am1.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


/**
 * SendLoginRequestActivity
 * Created on January 8th, 2013
 * @author Bernardo Plaza 
 */
public class SendLoginRequestActivity extends Activity {
	
	/**
	 * This Activity is used to send data options to the server and get the response from it
	 * allowing the user to finally login in the system and looking the messages.
	 */
	@Override
	public void onCreate (Bundle savedInstanceState){
		final Activity activity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_result);
		Bundle bundlefromlogin = getIntent().getExtras();
		TextView txtUser = (TextView) findViewById(R.id.User_res);
		TextView txtPassword = (TextView) findViewById(R.id.password_res);
		CheckBox checkBoxTeacher = (CheckBox) findViewById(R.id.chekBox_teacher_res);
		
		checkBoxTeacher.setChecked(true);
		txtUser.setText(bundlefromlogin.getString("User"));	
		txtPassword.setText(bundlefromlogin.getString("Password"));	
		
		if (bundlefromlogin.getBoolean("IsTeacher")==true){
			//checkBoxTeacher.setChecked(true);
		}
		else checkBoxTeacher.setChecked(false);
		
		//! send data to server and wait the response, if success go to List PublicQuestionActivity if not return
		int LoginResult=2;
		//LoginResult= getLoginData();
		
		switch (LoginResult){
		case 0:
			Toast.makeText(SendLoginRequestActivity.this,"CASE 1",	Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(SendLoginRequestActivity.this,"CASE 2",	Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(SendLoginRequestActivity.this,"LOGED IN JUMP ACTIVITY",	Toast.LENGTH_SHORT).show();
			//Create a bundle with the Data which is sent to the next activity ListQuestionsActivity
			Bundle bundleLogRes = new Bundle();
			bundleLogRes.putString("UserLoged", bundlefromlogin.getString("User"));
			bundleLogRes.putBoolean("IsTeacherLoged", bundlefromlogin.getBoolean("IsTeacher"));		
			Intent intent = new Intent (SendLoginRequestActivity.this, ListQuestionsActivity.class); //getApplicationContext() LoginActivity.this
			intent.putExtras(bundleLogRes);
			startActivity(intent);	 
			finish();
			break;
			
		default: 
			Toast.makeText(SendLoginRequestActivity.this,"CASE DEFAULT",	Toast.LENGTH_SHORT).show();
			break;
		}
		
	}	
	
	int getLoginData(){ 
		final ProgressDialog pd = ProgressDialog.show(this,	"Login in...","Please wait while trying to login", true, false);
		new Thread(new Runnable(){
			public void run(){
				waitingForLogin();
				pd.dismiss();
				}
			}).start();

	    return (2);
	}

    
	void waitingForLogin(){
		try {
            synchronized(this){
                wait(2000);
            }
        }
        catch(InterruptedException ex){                    
        }
		
	}
	
}