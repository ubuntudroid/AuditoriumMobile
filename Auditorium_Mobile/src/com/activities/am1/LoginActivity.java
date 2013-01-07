package com.activities.am1;

import com.activities.am1.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LoginActivity extends Activity {
   
	 TextView registerScreen = null;
     TextView changeMail = null;
     TextView questionProblem = null;
     TextView tweeter= null;
     TextView email= null; 
     TextView password= null;
     Button buttonS= null;
     
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);              
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        changeMail = (TextView) findViewById(R.id.Change);
        questionProblem = (TextView) findViewById(R.id.QoP_Req);
        tweeter= (TextView) findViewById(R.id.Tweet); 
                 
        Button buttonS= (Button) findViewById(R.id.btnLogin);
        
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
        	
        }
        
        
        // Listening to Sign in button
        buttonS.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//save data and send it 
				//show wait
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
    }
    
     
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
			
			// Switching to Register screen
			Intent i = new Intent(getApplicationContext(), ListPublicQuestionActivity.class);
			startActivity(i);
			/*Create login obj 
			login sign = new login(login,password);

			//Create a new Intent with a source and destination  
			Intent chekBoxIntent = new Intent(AddQuestionActivity.this, ListPublicQuestionActivity.class);

			//There is the obj login to pass to the other activity (destination) 
			chekBoxIntent.putExtra("question", question);

			//Set result with primitive constant of Android 
			setResult(RESULT_OK,chekBoxIntent);

			/*the activity are allocated as a stack memory so if you don't termin it and you tip come
			 back button the activity is still there */
			//finish();
		}
	}
    
    /*Function to display simple Alert Dialog 
     * 
     * Set Dialog Title, message, icon and button
     * when is pressed the application is closed */
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
    
    
    
    
}//end