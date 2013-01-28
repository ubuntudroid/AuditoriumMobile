package com.activities.am1;

import java.util.ArrayList;

import com.adapters.am1.ListAnswerAdapter;
import com.models.am1.Answer;
import com.models.am1.Constant;
import com.models.am1.Question;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ShowAnswerActivity
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
		
		//here you can get extras parameters from Intent
		Bundle extras = getIntent().getExtras();
		//here you can get the object Question and relatives parameters from extras
		Answer answerPassed = (Answer) extras.get("Answer_Showed");
		String contentPassed  = answerPassed.getAnswer();
		String authorPassed= answerPassed.getAuthor();
		String datePassed= answerPassed.getDate();
		int numberOfRatesPassed= answerPassed.getNumberOfRates();
		
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
				ratedPositive(arg0);
			}
		});
		
		imageButtonNegative.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				ratedNegative(arg0);
			}
		});
		
		
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
		if (!checkiFRated(1)){
			Toast.makeText(ShowAnswerActivity.this,"RATED POSSITIVE",	Toast.LENGTH_SHORT).show();
			rates++;
			txtview_Rates.setText(String.valueOf(rates));	
			setRate(1);
		}
		else {
			Toast.makeText(ShowAnswerActivity.this,"YOU ALREADY RATED THIS ANSWER",	Toast.LENGTH_SHORT).show();
		}	
	}
	
	/**
	 * This method makes a negative rate
	 * @param rateToServer
	 * @return resultOfRate
	 */
	public void ratedNegative(View arg0){
		if (!checkiFRated(-1)){
			Toast.makeText(ShowAnswerActivity.this,"RATED NEGATIVE",Toast.LENGTH_SHORT).show();
			rates--;
			txtview_Rates.setText(String.valueOf(rates));
			setRate(-1);
		}
		else {
			Toast.makeText(ShowAnswerActivity.this,"YOU ALREADY RATED THIS ANSWER",	Toast.LENGTH_SHORT).show();
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
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}

}

