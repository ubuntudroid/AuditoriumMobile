package com.activities.am1;

import de.dresden.mobilisauditorium.android.client.proxy.Answer;
import com.models.am1.Constant;

import de.dresden.mobilisauditorium.android.client.Values;
import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.postAnswerRequest;
import de.dresden.mobilisauditorium.android.client.proxy.postAnswerResponse;
import de.dresden.mobilisauditorium.android.engine.Singleton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
/**
 * AddAnswerActivity
 * Create a new answer for the previous selected question. The activity needs the index of the question
 * and takes the text of the contentTextField which will be published.
 * Created on November 26, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * Edited by Bernardo Plaza
 * Last edited on January 24th, 2013
 */

public class AddAnswerActivity extends Activity {
	Button   buttonSubmitnAnswer = null;
	EditText contentTextField  = null;
	ScrollView scrollViewGeneralView = null;
	Bundle bundleAddAwnser = null;
	String answerAuthor=null;
	
	Singleton mSingleton = null;
	WaitingAnimation animation = null;
	int question_index = 0;
	
	/**
	 * Create a new answer for the previous selected question. The activity needs the index of the question
	 * and takes the text of the contentTextField which will be published.
	 * @param savedInstanceState Bundle used to get the question_index
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_answer);
		mSingleton = Singleton.getInstance();
		contentTextField     = (EditText) findViewById(R.id.editText_ContentAnswer_AddQuestion);
		buttonSubmitnAnswer = (Button)   findViewById(R.id.button_SubmitAnswer_AnswerQuestionActivity);
		bundleAddAwnser = getIntent().getExtras();
		question_index = bundleAddAwnser.getInt("question_index");
		buttonSubmitnAnswer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				manageButton(v);
			}
		});
		
	}
	/**
	 * Called when buttonSubmitnAnswer  is pressed check if there is a MINIMUN_ANSWER_SIZE, if does a Answer object 
	 * is created using the text of contentTextField the author logged in the system and the actual date.
	 * Once the answer is published it automatically returns to ShowQuestionActivity
	 *  
	 */
	public void manageButton(View v) {
		// TODO Auto-generated method stub
		String answerContent = contentTextField.getText().toString();

		/*We don't want empty fields so we have to check on subject,course and content field */
		if(answerContent.length()< Constant.MINIMUM_ANSWER_SIZE){
			/*show a message for a long(or short)period */
			Toast.makeText(this, R.string.errorAnswerContent, Toast.LENGTH_LONG).show();
		}
		else{

			/*Create an obj Question with three field Answer(int index, String answer,String author,String date,int numberOfRates)*/
			Toast.makeText(getApplicationContext(), String.valueOf(question_index), Toast.LENGTH_SHORT);
			Answer answer = new Answer();
			answer.setAnswer(answerContent);
			answer.setDate(getDateData());
			answer.setRate(0);
			answer.setQuestion_index(question_index);
			answer.setAnswer_index(0);
			String user = mSingleton.userProfile.getNickname();
			answer.setUser(user);
			animation = new WaitingAnimation(this);
			mSingleton.OutStub.sendXMPPBean(new postAnswerRequest(answer));
		}
	}
	
	/** The response object from Singleton is received and processed.
	 *  If the message is posted successfully its changes to the activity ShowQuestionActivity
	 *  if not, Toast is showed.
	 */
	private Handler onReceived = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		Log.i("AddAnswerActivity", "Message came!" );

    		if (msg.obj instanceof postAnswerResponse){
    			Log.i("AddAnswerActivity", "Message is response!" );
    			animation.cancel(true);
    			Log.i("AddAnswerActivity","Animation canceled!" );
    			postAnswerResponse response = (postAnswerResponse)msg.obj;
    			switch(response.getErrortype()){
    			case Values.AnswerPostedSuccessfully:{
    				Intent intent = new Intent(AddAnswerActivity.this,ShowQuestionActivity.class);
    				setResult(RESULT_OK, intent);
    				finish();
    				break;
    			}
    			case Values.AnswerWasNotAdded:{
    				Toast.makeText(getApplicationContext(), "Failed to post the answer!", Toast.LENGTH_SHORT).show();
    				break;
    			}
    			}
    		}
    	}
    };
    

    /** 
     * Launched any time to start interacting with the user. The mSingleton is updated 
     */
    @Override
    protected void onResume(){
    	super.onResume();
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
	
	/**
	 * This method get the local time and return it formated "MM.DD.YYYY at HH:MM:SS" 
	 * @return date
	 */
	public String getDateData(){
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String date=today.format("%d.%m.%Y at %H:%M:%S");
		return date;
	}
	
	/**
	 * kill the activity if back button is pressed
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
