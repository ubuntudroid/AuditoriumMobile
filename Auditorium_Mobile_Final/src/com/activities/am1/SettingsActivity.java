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

import android.view.KeyEvent;
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
 * SettingsActivity
 * This activity is used to enable or disable notifications
 * Created on January 14th, 2013
 * @author Bernardo Plaza 
 * last update on January 23th, 2013 
 */
public class SettingsActivity extends Activity {
   
     CheckBox chkPublishQuestion = null;
     CheckBox chkPublishAnswer = null;
     CheckBox chkRate = null;
     boolean questionStatus=true;
     boolean answerStatus=true;
     boolean rateStatus=true;   
     Singleton mSingleton = null;
     
     
    /**
    * This activity is used to enable or disable notifications
   	*  
   	*/ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);    
       mSingleton = Singleton.getInstance();
       
        
        chkPublishQuestion = (CheckBox) findViewById(R.id.chk_settings_question);
        chkPublishAnswer = (CheckBox) findViewById(R.id.chk_settings_answer);
        chkRate = (CheckBox) findViewById(R.id.chk_settings_rate);

        questionStatus = mSingleton.question_notification;
        answerStatus = mSingleton.answer_notification;
        rateStatus = mSingleton.rate_notification;
        //!get settings
        if (questionStatus) {
			chkPublishQuestion.setChecked(true);
		}
		else{
			chkPublishQuestion.setChecked(false);
		}
        
        if (answerStatus) {
			chkPublishAnswer.setChecked(true);
		}
		else{
			chkPublishAnswer.setChecked(false);
		}
        
        if (rateStatus) {
        	chkRate.setChecked(true);
     	}
        else{
        	chkRate.setChecked(false);
        }
        
        chkPublishQuestion.setOnClickListener(new OnClickListener() {
     			public void onClick(View v) {
     				managechkPublishQuestion(v);
     			}
     		});	        
        
        chkPublishAnswer.setOnClickListener(new OnClickListener() {
 			public void onClick(View v) {
 				managechkPublishAnswer(v);
 			}
 		});	 
        
        chkRate.setOnClickListener(new OnClickListener() {
 			public void onClick(View v) {
 				managechkRate (v);
 			}
 		});	 
        
               
	}

	public void  managechkPublishQuestion(View v) {
		 
		if (((CheckBox) v).isChecked()) {
			//((CheckBox) v).setText("Enabled: Someone publish a question");
			//ENABLE notification  publish question
			mSingleton.question_notification = true;
		}
		//DISABLE notification  publish question
		else{
			//((CheckBox) v).setText("Disabled: Someone publish a question");
			mSingleton.question_notification = false;
			}
	}    
	
	public void  managechkPublishAnswer(View v) {
		 
		if (((CheckBox) v).isChecked()) {
			//((CheckBox) v).setText("Enabled: Someone publish a answer");
			//ENABLE notification  publish answer
			mSingleton.answer_notification = true;
		}
		//DISABLE notification  publish answer
		else{
			//((CheckBox) v).setText("Disabled: Someone publish a answer");
			mSingleton.answer_notification = false;;
			}
	}    
	
	public void  managechkRate(View v) {
		if (((CheckBox) v).isChecked()) {
			//((CheckBox) v).setText("Enabled: Someone rate you");
			//ENABLE notification  publish rate
		}
		//DISABLE notification  publish question
		else{
			//((CheckBox) v).setText("Disabled: Someone rate you");
			}
	}  
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
    
    
}//end