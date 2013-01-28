package com.activities.am1;

import com.activities.am1.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
/**
 * LoginActivity
 * Created on January 8th, 2013
 * @author Bernardo Plaza 
 */
public class LoginActivity extends Activity {
   
	 TextView registerScreen = null;
     TextView changeMail = null;
     TextView questionProblem = null;
     TextView tweeter= null;
     EditText email= null; 
     EditText password= null;
     Button buttonS= null;
     CheckBox chkLogAsTeacher = null;
     
     boolean isTeacher=false;
    
    /**
    * This activity is the first one which the application runs when it started
 	* Is used to log in in the Auditorium system, pretending to seems as much 
 	* as is possible to the web site.
 	* An email address and the password is needed. 
 	*  
 	*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);              
        registerScreen = (TextView) findViewById(R.id.link_to_register);
        changeMail = (TextView) findViewById(R.id.Change);
        questionProblem = (TextView) findViewById(R.id.QoP_Req);
        tweeter= (TextView) findViewById(R.id.Tweet); 
        chkLogAsTeacher =(CheckBox)findViewById (R.id.chekBox_teacher);       
        buttonS= (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById (R.id.EditText_emailAddress);
        password = (EditText) findViewById (R.id.EditText_password);
        
        /*The application only runs if there is Internet connection in the device*/
        
        /* Detecting Internet connection status*/
        
        Boolean isInternetPresent = false;
        ConnectionDetect conndetection = new ConnectionDetect(getApplicationContext());
        
        // get Internet status
        isInternetPresent = conndetection.isConnectingToInternet();
        
        // check for Internet status
        if (!isInternetPresent) {
        	// Internet connection is not present
        	 // Ask user to connect to Internet
        	showAlertDialog(LoginActivity.this, "No Internet Connection",
                    "Please enable Internet connection.", false);
        	
        } //end of checking Internet status  
        
        
        addListeners();
        
        	
	}
    
    /**
     * manageLoginButton check first if there is some text in the different fields
     * then a bundle element is created to send the information to the other activity
     * which will communicate with the server.  
     * @param v
     */
    public void manageLoginButton(View v) {
		// TODO Auto-generated method stub
    	    	
    	String emailText  = email.getText().toString();
		String passwordText = password.getText().toString();
		
		/*We don't want empty fields so we have to check on subject,course and content field */
		if(emailText.equals("") || passwordText.equals("") ){
			/*show a message for a long(or short)period */
			Toast.makeText(this, R.string.errorEmptyString, Toast.LENGTH_LONG).show();
		}
		
		else{

			Toast.makeText(this, R.string.LoginIn, Toast.LENGTH_LONG).show();
			
			//Create a bundle with the Data which is sent to the next activity
			Bundle bundleLog = new Bundle();
			bundleLog.putString("Email", emailText);
			bundleLog.putString("Password", passwordText);
			bundleLog.putBoolean("IsTeacher", isTeacher);
			
			Intent intent = new Intent (LoginActivity.this, SendLoginRequestActivity.class); //getApplicationContext() LoginActivity.this
			intent.putExtras(bundleLog);
			startActivity(intent);	
			
			//finish();
		}
	}
    
    /* Display an Alert Dialog 
     * 
     * Set Dialog Title, message, icon and button
     * when is pressed the application is closed 
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
 
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	finish();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
    
    public void  manageCheckBoxTeacher(View v) {
		//if is checked there is an advertisement message
		if (((CheckBox) v).isChecked()) {
			//toggle teacher to send it 
			isTeacher = true;
		}
		else isTeacher=false;
	}    
 
    public void addListeners() {
    	
/* Listeners of the clickable items such us button and hlinks*/
        
        // Listening to Sign in button
        buttonS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// ! show wait until 
				manageLoginButton(v);
			}
		});
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
               
        // Listening to change mail
        changeMail.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				changeMail.setText(Html.fromHtml("<a href=\"mailto:auditorium@inftex.net?Subject=Registered with wrong email address.\">We can change your email address for you!</a>"));
				changeMail.setMovementMethod(LinkMovementMethod.getInstance());
			}
		});
        
     // Listening to question
        questionProblem.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				questionProblem.setText(Html.fromHtml("<a href=\"mailto:auditorium@inftex.net\">You can send us an email (german or english)</a>"));
				questionProblem.setMovementMethod(LinkMovementMethod.getInstance());
			}
		});
        
       // Listening to twitter
        tweeter.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				tweeter.setText(Html.fromHtml("<a href=\"https://twitter.com/_auditorium\">to @_auditorium</a>"));
				tweeter.setMovementMethod(LinkMovementMethod.getInstance());
			}
		});
    
        chkLogAsTeacher.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				manageCheckBoxTeacher(v);
			}
		});
    }
    
    
}//end