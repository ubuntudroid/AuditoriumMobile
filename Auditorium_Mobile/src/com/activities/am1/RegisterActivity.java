package com.activities.am1;

import com.activities.am1.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    
	TextView loginScreen = null;
    TextView forgot = null;
    TextView instruct = null;
    TextView user= null;
    TextView emailReg = null;
    TextView passReg = null;
    TextView confirmPassReg = null;
    
    Button buttonReg= null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        loginScreen = (TextView) findViewById(R.id.link_to_register);
        forgot =(TextView) findViewById(R.id.ForgotPass);
        instruct =(TextView) findViewById(R.id.ConfirmInst);
        user =(TextView) findViewById(R.id.reg_user_name);
        emailReg =(TextView) findViewById(R.id.reg_user_name);
        passReg =(TextView) findViewById(R.id.reg_password);
        confirmPassReg =(TextView) findViewById(R.id.reg_conf_password);
        Button buttonReg = (Button) findViewById(R.id.btnRegister);
        
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
	
	public void manageRegButton(View v) {
		// TODO Auto-generated method stub
    	    	
		String userText = user.getText().toString();
		String emailRegText  = emailReg.getText().toString();
		String passwordRegText = passReg.getText().toString();
		String confirmPassRegText = confirmPassReg.getText().toString();
		
		/*We don't want empty fields so we have to check on subject,course and content field */
		if(userText.equals("")|| emailRegText.equals("") || passwordRegText.equals("")|| confirmPassRegText.equals("") ){
			/*show a message for a long(or short)period */
			Toast.makeText(this, R.string.errorEmptyString, Toast.LENGTH_LONG).show();
		
		} else if (passwordRegText.equals(confirmPassRegText)){
			Toast.makeText(this, R.string.Registering, Toast.LENGTH_LONG).show();
			/*Create reg obj 
			login sign = new reg(user,email,pass);

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
		
		else{
			//we are in this point when password is not equal, a message is showed
			Toast.makeText(this, R.string.errorLogin, Toast.LENGTH_LONG).show();
		}
	}
}