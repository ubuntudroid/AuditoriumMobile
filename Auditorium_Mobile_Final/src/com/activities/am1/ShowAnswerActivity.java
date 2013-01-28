package com.activities.am1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.dresden.mobilisauditorium.android.client.Values;
import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.Answer;
import de.dresden.mobilisauditorium.android.client.proxy.rateAnswerRequest;
import de.dresden.mobilisauditorium.android.client.proxy.rateAnswerResponse;
import de.dresden.mobilisauditorium.android.engine.Singleton;

/**
 * ShowAnswerActivity
 * Default method to create an activity in which there has been obtained the extras parameters 
 * from Intent control (if the button Submit Question is pushed) and  has been taken the object Question 
 * and related parameters from extras.
	 * Finally each field of the question is field with the parameters obtained.
 * Created on January 15th, 2013
 * @author Bernardo Plaza
 * 
 */
public class ShowAnswerActivity extends Activity {
	TextView answerTextField  = null;
	TextView dateTextField = null;
	TextView authorTextField = null;
	Button imageButtonPositive= null;
	Button imageButtonNegative= null;
	TextView txtview_Rates= null;
	int rates=0;
	boolean FLAG_RATED=false;
	
	
	Singleton mSingleton = null;
	WaitingAnimation animation = null;

	Answer answerPassed = null;

	/**
	 * Default method to create an activity in which there has been obtained the extras parameters 
	 * from Intent control (if the button Submit Question is pushed) and  has been taken the object Question 
	 * and related parameters from extras.
	 * Finally each field of the question is field with the parameters obtained.
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_answer);
		mSingleton = Singleton.getInstance();
		//here you can get extras parameters from Intent
		Bundle extras = getIntent().getExtras();
		//here you can get the object Question and relatives parameters from extras
		answerPassed = (Answer) extras.get("Answer_Showed");
		String contentPassed  = answerPassed.getAnswer();
		String authorPassed= answerPassed.getUser();
		String datePassed= answerPassed.getDate();
		int numberOfRatesPassed= answerPassed.getRate();

		//assign to the id of the object the field of the Question object
		answerTextField = (TextView) findViewById(R.id.editText_Answer);
		answerTextField.setText(contentPassed); 
		authorTextField = (TextView) findViewById(R.id.editText_Author_answer);
		authorTextField.setText(authorPassed);
		dateTextField = (TextView) findViewById(R.id.editText_Date_answer);
		dateTextField.setText(datePassed);
		txtview_Rates = (TextView) findViewById(R.id.txtview_rates_answer);
		imageButtonNegative = (Button) findViewById(R.id.rate_negative_answer);
		imageButtonPositive = (Button) findViewById(R.id.rate_positive_answer);
		txtview_Rates.setText(String.valueOf(numberOfRatesPassed));

		rates= numberOfRatesPassed;


		imageButtonPositive.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				mSingleton.OutStub.sendXMPPBean(new rateAnswerRequest(mSingleton.userProfile.getEmailAddress(), answerPassed.getQuestion_index(), answerPassed.getAnswer_index(), +1));
			}
		});

		imageButtonNegative.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				mSingleton.OutStub.sendXMPPBean(new rateAnswerRequest(mSingleton.userProfile.getEmailAddress(), answerPassed.getQuestion_index(), answerPassed.getAnswer_index(), -1));
			}
		});


	} 

	/** Handler is responsible for asynchronous receiving the response from server while
	 * waiting animation is running! Here is where the response is being processed and the GUI
	 * of the activity is updated
	 */
	private Handler onReceived = new Handler(){
		@Override
		public void handleMessage(Message msg){
			Log.i("ShowAnswerActivity", "Message came!" );

			if (msg.obj instanceof rateAnswerResponse){
				Log.i("ShowAnswerActivity", "Message is response!" );
				Log.i("ShowAnswerActivity","Animation canceled!" );
				rateAnswerResponse response = (rateAnswerResponse)msg.obj;
				txtview_Rates.setText(String.valueOf(response.getErrortype()));
				/*switch(response.getErrortype()){
	    			case Values.AlreadyRatedPositive:{
	    				Toast.makeText(getApplicationContext(), "You already have rated this answer!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			case Values.AlreadyRatedNegative:{
	    				Toast.makeText(getApplicationContext(), "You already have unrated this answer!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			case Values.RatedFailed:{
	    				Toast.makeText(getApplicationContext(), "Raiting operation failed!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			default:{
	    				txtview_Rates.setText(String.valueOf(response.getErrortype()));
	    				break;
	    			}

	    		}

	    		}*/

			}
		}
	};



	@Override
	protected void onResume(){
		super.onResume();
		mSingleton.registerHandler(onReceived);
	}

	@Override
	protected void onPause(){
		super.onPause();
		mSingleton.unregisterHandler(onReceived);
	}

	/**
	 * This method ask the server if the user loged in have already voted for this question
	 * @param newRate
	 * @return FLAG_RATED
	 */
	public boolean checkiFRated(int newRate){

		//! ask the server if the user already voted this
		return(FLAG_RATED);
	}

	/**
	 * This method send the rate information to the server
	 * @param rateToServer
	 * @return resultOfRate
	 */
	public int setRate(int rateToServer){
		int resultOfRate=1;
		//! send rate to server and check if ok.
		FLAG_RATED=true;
		return resultOfRate;
	}

	/**
	 * This method makes a positive rate
	 * @param rateToServer
	 * @return resultOfRate
	 */
	public void ratedPositive(View arg0){
		/*	if (!checkiFRated(1)){
			Toast.makeText(ShowAnswerActivity.this,"RATED POSSITIVE",	Toast.LENGTH_SHORT).show();
			rates++;
			txtview_Rates.setText(String.valueOf(rates));	
			setRate(1);
		}
		else {
			Toast.makeText(ShowAnswerActivity.this,"YOU ALREADY RATED THIS ANSWER",	Toast.LENGTH_SHORT).show();
		}	*/
		//	animation = new WaitingAnimation(this);
		//	animation.execute();

	}

	/**
	 * This method makes a negative rate
	 * @param rateToServer
	 * @return resultOfRate
	 */
	public void ratedNegative(View arg0){
		/*if (!checkiFRated(-1)){
			Toast.makeText(ShowAnswerActivity.this,"RATED NEGATIVE",Toast.LENGTH_SHORT).show();
			rates--;
			txtview_Rates.setText(String.valueOf(rates));
			setRate(-1);
		}
		else {
			Toast.makeText(ShowAnswerActivity.this,"YOU ALREADY RATED THIS ANSWER",	Toast.LENGTH_SHORT).show();
		}	*/
		//	animation = new WaitingAnimation(this);
		//	animation.execute();

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

