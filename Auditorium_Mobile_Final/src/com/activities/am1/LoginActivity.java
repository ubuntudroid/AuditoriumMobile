package com.activities.am1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




import de.dresden.mobilisauditorium.android.client.Values;
import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.logInRequest;
import de.dresden.mobilisauditorium.android.client.proxy.logInResponse;
import de.dresden.mobilisauditorium.android.client.proxy.sendNotificationServiceIDRequest;
import de.dresden.mobilisauditorium.android.engine.Singleton;
import de.tudresden.inf.rn.mobilis.mxa.MXAController;
/**
 * LoginActivity
 * First activity of the application  
 * Log in in the Auditorium system, pretending to seems as much 
 * as is possible to the web site.
 * An user name and  password are needed. 
 * Created on January 8th, 2013
 * @author Bernardo Plaza 
 */
public class LoginActivity extends Activity {
   
	 TextView registerScreen = null;
     TextView changeMail = null;
     TextView questionProblem = null;
     TextView tweeter= null;
     EditText user= null; 
     EditText password= null;
     Button buttonS= null;
     CheckBox chkLogAsTeacher = null;
     
     Singleton mSingleton = null;
     WaitingAnimation login = null;
     boolean isTeacher=false;
     
    /**
    * First activity of the application  
   	* Log in in the Auditorium system, pretending to seems as much 
   	* as is possible to the web site.
   	* An user name and  password are needed. 
   	*  
   	*/ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);    
        
        /* Initialization of Singleton object and MXA library which are responsible for connection
         * initialization is done only one time here, so in all other activities to use a connection 
         * an instance of singleton object has to be gotten!
         */
        
        mSingleton = Singleton.getInstance();
		mSingleton.mMXAController = MXAController.get();
		mSingleton.mMXAController.connectMXA(getApplication().getApplicationContext(), mSingleton);

		registerScreen = (TextView) findViewById(R.id.link_to_register);
        changeMail = (TextView) findViewById(R.id.Change);
        questionProblem = (TextView) findViewById(R.id.QoP_Req);
        tweeter= (TextView) findViewById(R.id.Tweet); 
        chkLogAsTeacher =(CheckBox)findViewById (R.id.chekBox_teacher);       
        buttonS= (Button) findViewById(R.id.btnLogin);
        user = (EditText) findViewById (R.id.EditText_user_login);
        password = (EditText) findViewById (R.id.EditText_password);
        
        addListeners();
        
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
               
	}
    
	/** Handler is responsible for asynchronous receiving the response from server while
	 * waiting animation is running! Here is where the response is being processed and the GUI
	 * of the activity is updated
	 */
	private Handler onReceived = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		if (msg.obj instanceof logInResponse){
    			login.cancel(true);
    			logInResponse response = (logInResponse)msg.obj;
    			int err = response.getErrortype();
    			switch(err){
				case Values.LoggedInSuccessfully:{
					mSingleton.userProfile.setNickname(response.getNickname());
					Toast.makeText(getApplicationContext(), mSingleton.userProfile.getNickname(), Toast.LENGTH_SHORT);
					mSingleton.Online = true;
					Intent listQuestionsActivityIntent = new Intent(LoginActivity.this, ListQuestionsActivity.class);
					setResult(RESULT_OK);
					startActivity(listQuestionsActivityIntent);
					finish();
					break;
				}
				case Values.LogInfoIncorrect:{
					Toast.makeText(getApplicationContext(), "Logging data is incorrect!", Toast.LENGTH_SHORT).show();
					break;
				}
				case Values.UserDoesntExist:{
					Toast.makeText(getApplicationContext(), "Such account doesn't exist!", Toast.LENGTH_SHORT).show();
					break;
				}
				default:{
					Toast.makeText(getApplicationContext(), "Unknown error!", Toast.LENGTH_SHORT).show();
					break;
				}
    		}
    	}
    	}
    };
    
    @Override
    protected void onStart(){
    	super.onStart();
    	mSingleton.registerHandler(onReceived);
    }
    
    /** 
     * Called when an activity is going into the background, but has not been killed. The mSingleton is updated 
     */
    @Override
    protected void onPause(){
    	super.onPause();
    	mSingleton.unregisterHandler(onReceived);
    }
	
    /**Function to display simple Alert Dialog 
     * Set Dialog Title, message, icon and button
     * when is pressed the application is closed 
     * 
     */
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
   
    /**
     * Update the variable isTeacher if the CheckBox is checked or unchecked 
     * 
     */
    public void  manageCheckBoxTeacher(View v) {
		//if is checked there is an advertisement message
		if (((CheckBox) v).isChecked()) {
			//toggle teacher to send it 
			isTeacher = true;
		}
		else isTeacher=false;
	}    
    
    /**
     * Add the different listeners of the activity
     */
    public void addListeners() {

//        // Listening to Sign in button
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
				finish();
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
    
    /**
     * manageLoginButton check first if there is some text in the different fields
     * then a bundle element is created to send the information to the other activity
     * which will communicate with the server.  
     * @param v
     */
    public void manageLoginButton(View V) { //myClickMethod manageLoginButton(View v)
		// TODO Auto-generated method stub
    	
    	String userText  = user.getText().toString();
		String passwordText = password.getText().toString();
			
		/*We don't want empty fields so we have to check on subject,course and content field */
		if(userText.equals("") || passwordText.equals("") ){
			/*show a message for a long(or short)period */
			Toast.makeText(this, R.string.errorEmptyString, Toast.LENGTH_LONG).show();
		}
		
		else{

			mSingleton.userProfile.setEmailAddress(userText);
			mSingleton.userProfile.setPassword(passwordText);
			login = new WaitingAnimation(this);
			login.execute();
			mSingleton.OutStub.sendXMPPBean(new logInRequest(userText,passwordText,0,mSingleton.registrationID));
			
			//Create a bundle with the Data which is sent to the next activity
		}
	}
    
}//end