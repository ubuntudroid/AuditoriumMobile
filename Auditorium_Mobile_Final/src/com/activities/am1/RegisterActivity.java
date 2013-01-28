package com.activities.am1;

import com.activities.am1.R;

import de.dresden.mobilisauditorium.android.client.Values;
import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.registerNewUserRequest;
import de.dresden.mobilisauditorium.android.client.proxy.registerNewUserResponse;
import de.dresden.mobilisauditorium.android.engine.Singleton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LoginActivity
 * Register a new user in the Auditorium system, pretending to seems as much 
 * as is possible to the web site.
 * An user name, an email addres and  password are needed. 
 * Created on January 8th, 2013
 * @author Bernardo Plaza 
 */
public class RegisterActivity  extends Activity {
    
	TextView loginScreen = null;
    TextView forgot = null;
    TextView instruct = null;
    TextView user= null;
    TextView emailReg = null;
    TextView passReg = null;
    TextView confirmPassReg = null;
    boolean isTeacherReg= false;
    boolean validEmail = false;
    CheckBox chkLogAsTeacherRegister = null;
    Button buttonReg= null;
    
    Singleton mSingleton = null;
    WaitingAnimation animation = null;
   
    /**
     * Register a new user in the Auditorium system, pretending to seems as much 
     * as is possible to the web site.
     * An user name, an email addres and  password are needed. 
     *  
     */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        mSingleton = Singleton.getInstance();
        loginScreen = (TextView) findViewById(R.id.link_to_register);
        forgot =(TextView) findViewById(R.id.ForgotPass);
        instruct =(TextView) findViewById(R.id.ConfirmInst);
        user =(TextView) findViewById(R.id.reg_user_name);
        emailReg =(TextView) findViewById(R.id.reg_email);
        passReg =(TextView) findViewById(R.id.reg_password);
        confirmPassReg =(TextView) findViewById(R.id.reg_conf_password);
        buttonReg = (Button) findViewById(R.id.btnRegister);
        chkLogAsTeacherRegister =(CheckBox)findViewById (R.id.chekBox_teacher_reg);
        
        // Listening to Register button
        buttonReg.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//save data and send it 
				//show wait
				manageRegButton(v);
			}
		});
        
        
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
        
     // Listening to question
        forgot.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				forgot.setText(Html.fromHtml("<a href=\"https://auditorium.inf.tu-dresden.de/users/password/new\">Forgot your password?</a>"));
				forgot.setMovementMethod(LinkMovementMethod.getInstance());
			}
		});
        
     // Listening to question
        instruct.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				instruct.setText(Html.fromHtml("<a href=\"https://auditorium.inf.tu-dresden.de/users/confirmation/new\">Didn't receive confirmation instructions?</a>"));
				instruct.setMovementMethod(LinkMovementMethod.getInstance());
			}
		});
        
    }
	
	/** Handler is responsible for asynchronous receiving the response from server while
	 * waiting animation is running! Here is where the response is being processed and the GUI
	 * of the activity is updated
	 */
	 private Handler onRegisteredReceived = new Handler(){
	    	@Override
	    	public void handleMessage(Message msg){
	    		Log.i("RegisterActivity", "Message came!" );

	    		if (msg.obj instanceof registerNewUserResponse){
	    			Log.i("RegisterActivity", "Message is response!" );
	    			animation.cancel(true);
	    			Log.i("RegisterActivity","Animation canceled!" );
	    			registerNewUserResponse response = (registerNewUserResponse)msg.obj;
	    			int err = response.getErrortype();
	    			switch(err){
					case Values.RegistrationSuccess:{
						mSingleton.Online = true;
						Intent listPublicQuestionActivityIntent = new Intent(RegisterActivity.this, ListQuestionsActivity.class); 
						startActivity(listPublicQuestionActivityIntent);
						finish();
						break;
					}
					case Values.UserAlreadyExists:{
						Toast.makeText(getApplicationContext(), "Such account already exists!", Toast.LENGTH_LONG).show();
						break;
					}
					default:{
	    				Toast.makeText(getApplicationContext(), "Unknown Error!", Toast.LENGTH_LONG).show();
	    				break;
					}
	    		}
	    		}
	    	}
	    };
	    @Override
	    protected void onStart(){
	    	super.onStart();
	    	mSingleton.registerHandler(onRegisteredReceived);
	    }
	    
	    /** 
	     * Called when an activity is going into the background, but has not been killed. The mSingleton is updated 
	     */
	    @Override
	    protected void onPause(){
	    	super.onPause();
	    	mSingleton.unregisterHandler(onRegisteredReceived);
	    }
	
	/**
	 * Called when RegButton is pressed    
	 * first check if the email address seems to be a real one, then check if there is not any empty field
	 * finally sends the user information to the server and runs the activity ListQuestionActitivy 
	 */
	public void manageRegButton(View v) {
		// TODO Auto-generated method stub
    	    	
		String userText = user.getText().toString();
		String emailRegText   = emailReg.getText().toString();
		String passwordRegText = passReg.getText().toString();
		String confirmPassRegText = confirmPassReg.getText().toString();
		validEmail = isValidEmail(emailRegText);
		if (!validEmail) {
			Toast.makeText(getApplicationContext(), "Email is incorrect!", Toast.LENGTH_SHORT).show();
		}
		/*We don't want empty fields so we have to check on subject,course and content field */
		if(userText.equals("")|| emailRegText.equals("") || passwordRegText.equals("")|| confirmPassRegText.equals("") ){
			/*show a message for a long(or short)period */
			Toast.makeText(this, R.string.errorEmptyString, Toast.LENGTH_LONG).show();
		
		} else if (passwordRegText.equals(confirmPassRegText)){
			animation = new WaitingAnimation(this);
			animation.execute();
			
			mSingleton.userProfile.setEmailAddress(emailRegText);
			mSingleton.userProfile.setPassword(passwordRegText);
			mSingleton.userProfile.setNickname(userText);
			
			registerNewUserRequest request = new registerNewUserRequest();
			request.setEmailAddress(emailRegText);
			request.setUsername(userText);
			request.setPassword(passwordRegText);
			if(isTeacherReg){
				request.setAccountType(1);
				mSingleton.userProfile.setAccountType(1);
			}else{
				request.setAccountType(0);
				mSingleton.userProfile.setAccountType(0);
			}
			mSingleton.OutStub.sendXMPPBean(new registerNewUserRequest(userText,emailRegText,passwordRegText,0,mSingleton.registrationID));
		
		}
		
		else{
			//we are in this point when password is not equal, a message is showed
			Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_LONG).show();
		}
	}

	 /**
     * Update the variable isTeacher if the CheckBox is checked or unchecked 
     * 
     */ 
	public void  manageCheckBoxTeacherRegister(View v) {
			//if is checked there is an advertisement message
			if (((CheckBox) v).isChecked()) {
				//toggle teacher to send it 
				isTeacherReg = true;
			}
			else isTeacherReg=false;
		} 
	 
	/**
	 * Check if the email seems to be real such as xxx@xxx.x
	 * and don't use unallowed characters. Does not check if is really an existing email address! 
	 */
	public final static boolean isValidEmail(CharSequence target) {
		    if (target == null) {
		        return false;
		    } else {
		        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		    }
		}
	
	/**
	 * kill the activity if back button is pressed
	 */
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
		    if ((keyCode == KeyEvent.KEYCODE_BACK))
		    {
		        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
		        startActivity(intent);
		        finish();
		    }
		    return super.onKeyDown(keyCode, event);
		}


}