package com.activities.am1;

import com.models.am1.Answer;
import com.models.am1.Constant;

import android.os.Bundle;
import android.text.format.Time;
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
 * Created on November 26, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * Edited by Bernardo Plaza
 * Last edited on January 18th, 2013
 */

public class AddAnswerActivity extends Activity {
	Button   buttonSubmitnAnswer = null;
	EditText contentTextField  = null;
	ScrollView scrollViewGeneralView = null;
	Bundle bundleAddAwnser = null;
	String answerAuthor=null;
	/**
	 * Default method to create an activity in which there is control if the button Submit Answer is pushed
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_answer);
		contentTextField     = (EditText) findViewById(R.id.editText_ContentAnswer_AddQuestion);
		buttonSubmitnAnswer = (Button)   findViewById(R.id.button_SubmitAnswer_AnswerQuestionActivity);
		bundleAddAwnser = getIntent().getExtras();
		answerAuthor = bundleAddAwnser.getString("UserLoged");
		buttonSubmitnAnswer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				manageButton(v);
			}
		});
		
	}
	/**
	 * This method permits on first thing to do a control if there are an empty field as a content of answer and 
	 * finally to create a new object Answer.
	 * This object is passed to another activity through the use of an  intent  and  it is looking forward to the results.
	 * Finally the activity is  terminated and when the user push the come back button, the activity is deallocated.  
	 * @param v View
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

			String dateNewAnser= getDateData();
			/*Create an obj Question with three field Answer(int index, String answer,String author,String date,int numberOfRates)*/
			Answer answer = new Answer(Constant.NEW_ANSWER, answerContent, answerAuthor ,dateNewAnser,0  );

			/*Create a new Intent with a source and destination */
			Intent addAnswerIntent = new Intent(AddAnswerActivity.this, ShowQuestionActivity.class);

			/*There is the obj Question to pass to the other activity (destination)*/
			addAnswerIntent.putExtra("answer", answer);

			/*Set result with primitive constant of Android*/
			setResult(RESULT_OK,addAnswerIntent);

			/*the activity are allocated as a stack memory so if you don't termin it and you tip come
        			 back button the activity is still there */
			finish();
		}
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
