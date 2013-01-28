package com.activities.am1;

import java.util.ArrayList;

import com.adapters.am1.ListAnswerAdapter;
import com.models.am1.Answer;
import com.models.am1.Constant;
import com.models.am1.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ShowQuestionActivity
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * updated by Bernardo Plaza
 * last updated on January 10th, 2013
 */
public class ShowQuestionActivity extends Activity {
	TextView courseTextField  = null;
	TextView subjectTextField = null;
	TextView contentTextField = null;
	Button buttonAnswerQuestion = null;
	ListView listViewAnswers = null;
	ListAnswerAdapter adapterAnswers = null;
	ArrayList<Answer> answerList = new ArrayList<Answer>();
	
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
		setContentView(R.layout.activity_show_question);
		
		//here you can get extras parameters from Intent
		Bundle extras = getIntent().getExtras();
		//here you can get the object Question and relatives parameters from extras
		Question questionPassed = (Question) extras.get("Question_Showed");
		String coursePassed  = questionPassed.getCourse();
		String subjectPassed = questionPassed.getSubject();
		String contentPassed = questionPassed.getContent();
		String authorPassed= questionPassed.getAuthor();
		int numberOfRatesPassed= questionPassed.getNumberOfRates();
		//assign to the id of the object the field of the Question object
		//courseTextField = (EditText) findViewById(R.id.editText_CourseQuestion_ShowQuestion);
		courseTextField = (TextView) findViewById(R.id.editText_CourseQuestion_ShowQuestion);
		courseTextField.setText(coursePassed); 
		subjectTextField = (TextView) findViewById(R.id.editText_SubjectQuestion_ShowQuestion);
		//subjectTextField = (EditText) findViewById(R.id.editText_SubjectQuestion_ShowQuestion);
		subjectTextField.setText(subjectPassed);
		//contentTextField = (EditText) findViewById(R.id.editText_ContentQuestion_ShowQuestion);
		contentTextField = (TextView) findViewById(R.id.editText_ContentQuestion_ShowQuestion);
		contentTextField.setText(contentPassed);
		contentTextField = (TextView) findViewById(R.id.editText_Author);
		contentTextField.setText(authorPassed);
		txtview_Rates = (TextView) findViewById(R.id.txtview_rates);
		imageButtonNegative = (Button) findViewById(R.id.rate_negative);
		imageButtonPositive = (Button) findViewById(R.id.rate_positive);
		txtview_Rates.setText(String.valueOf(numberOfRatesPassed));
		
		rates= numberOfRatesPassed;
		

		buttonAnswerQuestion = (Button) findViewById(R.id.button_Answer_AnswerQuestionActivity);
		//Configure  listener for the button on click event
		buttonAnswerQuestion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				manageButton(v);
			}
		});
		listViewAnswers = (ListView) findViewById(R.id.listview_ShowQuestionActivity);

		for (int i = 0; i < 15; i++) {
			answerList.add(new Answer("Answer"+i));
		}
		adapterAnswers = new ListAnswerAdapter(this, answerList, R.layout.element_list_answer);
		listViewAnswers.setAdapter(adapterAnswers);
			
	
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
	/** This method pop the results(extras) from the Intent and use those to create a new object Answer to add at list.
	 * @param requestCode int
	 * @param resultCode int 
	 * @param data Intent 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("ShowQuestionActivity", "come back from AddAnswerActivity");
		/* This control is important to don't crash the system when you open the form
		 *  of the question and click on previous button without submit nothing and the 
		 *  data(intent)is null */
		if(data!=null){
			/*pop the result passed with Intent in the other activity making a cast because 
			 * the type returned is an Object*/
			Bundle extras = data.getExtras();
			Answer answer = (Answer) extras.get("answer");
			//			String answerResult  = answer.getAnswer();
			//Add a new element to the list
			answerList.add(answer);
			adapterAnswers.notifyDataSetChanged();
		}
	}
	public void manageButton(View v) {
		// TODO Auto-generated method stub
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 */
		Intent buttonIntent = new Intent(ShowQuestionActivity.this, AddAnswerActivity.class);
		//Launch an activity for which you would like a result when it finished		
		this.startActivityForResult(buttonIntent, Constant.ADD_PUBLIC_QUESTION);
	}

	public boolean checkiFRated(int newRate){
		
		//! ask the server if the user already voted this
		return(FLAG_RATED);
	}
	
	public int setRate(int rateToServer){
		int resultOfRate=1;
		//! send rate to server and check if ok.
		FLAG_RATED=true;
		return resultOfRate;
	}
	
	public void ratedPositive(View arg0){
		if (!checkiFRated(1)){
			Toast.makeText(ShowQuestionActivity.this,"RATED POSSITIVE",	Toast.LENGTH_SHORT).show();
			rates++;
			txtview_Rates.setText(String.valueOf(rates));	
			setRate(Constant.POSITIVE_RATE);
		}
		else {
			Toast.makeText(ShowQuestionActivity.this,"YOU ALREADY RATED THIS QUESTION",	Toast.LENGTH_SHORT).show();
		}	
	}
	
	public void ratedNegative(View arg0){
		if (!checkiFRated(-1)){
			Toast.makeText(ShowQuestionActivity.this,"RATED NEGATIVE",Toast.LENGTH_SHORT).show();
			rates--;
			txtview_Rates.setText(String.valueOf(rates));
			setRate(Constant.NEGATIVE_RATE);
		}
		else {
			Toast.makeText(ShowQuestionActivity.this,"YOU ALREADY RATED THIS QUESTION",	Toast.LENGTH_SHORT).show();
		}	
	}

}

