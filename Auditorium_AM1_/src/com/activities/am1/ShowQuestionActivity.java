package com.activities.am1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.adapters.am1.ListAnswerAdapter;
import com.adapters.am1.ListQuestionAdapter;
import com.models.am1.Answer;
import com.models.am1.Constant;
import com.models.am1.Question;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
public class ShowQuestionActivity extends ListActivity {

	TextView courseTextField  = null;
	TextView subjectTextField = null;
	TextView contentTextField = null;
	TextView authorTextField = null;
	TextView dateTextField = null;
	Button buttonAnswerQuestion = null;
	ListView listViewAnswers = null;
	ListAnswerAdapter adapterAnswers = null;
	ArrayList<Answer> answerList = new ArrayList<Answer>();
	Bundle bundleFinalLogin=null;
	Button imageButtonPositive= null;
	Button imageButtonNegative= null;
	TextView txtview_Rates= null;
	int rates=0;
	boolean FLAG_RATED=false;
	boolean toggleRate=false;
	boolean toggleDate=false;
	
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
		bundleFinalLogin =  getIntent().getExtras();
		// Do Not show the virtual keyboard only if the user press a editText
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//here you can get extras parameters from Intent
		Bundle extras = getIntent().getExtras();
		//here you can get the object Question and relatives parameters from extras
		Question questionPassed = (Question) extras.get("Question_Showed");
		String coursePassed  = questionPassed.getCourse();
		String subjectPassed = questionPassed.getSubject();
		String contentPassed = questionPassed.getContent();
		String authorPassed= questionPassed.getAuthor();
		String datePassed= questionPassed.getDate();
		//int indexPassed= questionPassed.getIndex();
		//int privacyPassed = questionPassed.getPrivacy();
		
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
		authorTextField = (TextView) findViewById(R.id.editText_Author);
		authorTextField.setText(authorPassed);
		dateTextField = (TextView) findViewById(R.id.editText_Date);
		dateTextField.setText(datePassed);
		txtview_Rates = (TextView) findViewById(R.id.txtview_rates);
		imageButtonNegative = (Button) findViewById(R.id.rate_negative);
		imageButtonPositive = (Button) findViewById(R.id.rate_positive);
		txtview_Rates.setText(String.valueOf(numberOfRatesPassed));
		
		listViewAnswers = getListView(); 
		
		rates= numberOfRatesPassed;
		

		//! get answer for the server Answer(int index, String answer,String author,String date,int numberOfRates)
		// create the list shorted by rates
		String datePassedtest= getDateData();
		
		for (int i = 0; i < 3; i++) {
			answerList.add(new Answer(i,"This is an example of answer nº "+i+1,"Berny", datePassedtest, i*2  ));
		}
		adapterAnswers = new ListAnswerAdapter(this, answerList, R.layout.element_list_answer);
		//pass  adapter to view
		this.setListAdapter(adapterAnswers);
			
	
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
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {    	
	    	super.onCreateOptionsMenu(menu);
	    	MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu_public, menu);   
	        menu.setGroupVisible(R.id.group_isteacher, false);
	       //change the text for adding an answer
	        MenuItem addAnswer = menu.findItem(R.id.menu_add_question);
	        addAnswer.setTitle("Add Answer");
	        
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.menu_add_question:
	    		goToAddAnswerActivity();
	    		return true;
	    	case R.id.menu_logout:
	    		goToLogoutActivity();
	    		return true;
	    	case R.id.submenu_date:
	    		sortByDate();
	    		return true;
	    	case R.id.submenu_rates:
	    		sortByRates();
	    		return true;
	    	default: 
	            return super.onOptionsItemSelected(item);
	    	}
	  	
	    }

	
	/**
	 * This method permits to show a specific Answer when a general user. 
	 * click on item (an answer) of the adapted list.
	 * @param l ListView 
	 * @param v View 
	 * @param position int 
	 * @param id long  
	 */
	@Override
	public void onListItemClick(ListView listViewAnswers, View v, int position, long id){
		super.onListItemClick(listViewAnswers, v, position, id);
		Answer answerCurrent = (Answer) getListAdapter().getItem(position);
		Intent showAnswerActivityIntent = new Intent(ShowQuestionActivity.this,ShowAnswerActivity.class);
		showAnswerActivityIntent.putExtra("Answer_Showed", answerCurrent);
		startActivity(showAnswerActivityIntent);
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
			switch (requestCode){
				
				case Constant.ADD_ANSWER:
					if (resultCode == RESULT_OK){
						Bundle extras = data.getExtras();
						Answer answer = (Answer) extras.get("answer");
						answerList.add(answer);
						adapterAnswers.notifyDataSetChanged();
					}
			}
		}
	}
	public void goToAddAnswerActivity() {
		// TODO Auto-generated method stub
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 */
		Intent buttonIntent = new Intent(ShowQuestionActivity.this, AddAnswerActivity.class);
		//Launch an activity for which you would like a result when it finished		
		buttonIntent.putExtras(bundleFinalLogin);
		this.startActivityForResult(buttonIntent, Constant.ADD_ANSWER);
	}
	
	public void goToLogoutActivity() {
		// TODO Auto-generated method stub
		//! send data logout to server
		new AlertDialog.Builder(this).setTitle("Logout from Auditorium Mobile").setMessage("Do you really want to logout and exit?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				//!Logout
				Intent goLogin = new Intent(ShowQuestionActivity.this, LoginActivity.class);
				startActivity(goLogin);
				finish();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				// nothing to be done
			}
		})
		.show();
	}
	
	public void sortByRates(){
		Collections.sort(answerList, new Comparator<Answer>(){
			public int compare(Answer a0, Answer a1) {
				// TODO Auto-generated method stub
				int rateA1=0;
				int rateA2=0;
				if(!toggleRate){
					rateA1=a0.getNumberOfRates();
					rateA2=a1.getNumberOfRates();
				}
				else {
					rateA1=a1.getNumberOfRates();
					rateA2=a0.getNumberOfRates();
				}
				
				if ( rateA2 > rateA1 ) {return 1;}
				else if ( rateA2 < rateA1 ) { return -1;} 
				else {return 0;}
			}
	    });
		toggleRate=!toggleRate;
		
		//list of question passed to adapter
		adapterAnswers = new ListAnswerAdapter(this, answerList, R.layout.element_list_answer);
		//pass  adapter to view
		this.setListAdapter(adapterAnswers);
	}

	public void sortByDate(){
		Collections.sort(answerList, new Comparator<Answer>(){
	
			public int compare(Answer q0t, Answer q1t) {
				// TODO Auto-generated method stub
				Answer q0=null;
				Answer q1=null;
				if(!toggleDate){
					q0=q0t;
					q1=q1t;
				}
				else {
					q0=q1t;
					q1=q0t;
				}
				
				String monthDayQ0=q0.getDate().substring(0, 2);
				String monthQ0 = q0.getDate().substring(3, 5);
				String yearQ0 = q0.getDate().substring(6, 10);
				String hourQ0 = q0.getDate().substring(14, 16);
				String minutesQ0 = q0.getDate().substring(17, 19);
				String secondsQ0 = q0.getDate().substring(20, 22);
				int monthDayintQ0 = Integer.valueOf(monthDayQ0.toString());
				int monthintQ0 = Integer.valueOf(monthQ0.toString());
				int yearintQ0 = Integer.valueOf(yearQ0.toString());
				int hourintQ0 = Integer.valueOf(hourQ0.toString());
				int minutesintQ0 = Integer.valueOf(minutesQ0.toString());
				int secondsintQ0 = Integer.valueOf(secondsQ0.toString());
				
				String monthDayQ1=q1.getDate().substring(0, 2);
				String monthQ1 = q1.getDate().substring(3, 5);
				String yearQ1 = q1.getDate().substring(6, 10);
				String hourQ1 = q1.getDate().substring(14, 16);
				String minutesQ1 = q1.getDate().substring(17, 19);
				String secondsQ1 = q1.getDate().substring(20, 22);
				int monthDayintQ1 = Integer.valueOf(monthDayQ1.toString());
				int monthintQ1 = Integer.valueOf(monthQ1.toString());
				int yearintQ1 = Integer.valueOf(yearQ1.toString());
				int hourintQ1 = Integer.valueOf(hourQ1.toString());
				int minutesintQ1 = Integer.valueOf(minutesQ1.toString());
				int secondsintQ1 = Integer.valueOf(secondsQ1.toString());
				
				if ( yearintQ1 > yearintQ0 ) {return 1;} else if ( yearintQ1 < yearintQ0 ) {return -1;}
				else if ( monthintQ1 > monthintQ0 ) {return 1;} else if ( monthintQ1 < monthintQ0 ) {return -1;}
				else if ( monthDayintQ1 > monthDayintQ0 ) {	return 1;} else if ( monthDayintQ1 < monthDayintQ0 ) {return -1;}
				else if ( hourintQ1 > hourintQ0 ) {	return 1;} else if ( hourintQ1 < hourintQ0 ) {return -1;}
				else if ( minutesintQ1 > minutesintQ0 ) {return 1;} else if ( minutesintQ1 < minutesintQ0 ) {return -1;	}
				else if ( secondsintQ1 > secondsintQ0 ) {return 1;} else if ( secondsintQ1 < secondsintQ0 ) {return -1;	}
				else {
		            return 0;
		        }
				
			}
	    });
		toggleDate=!toggleDate;
		//list of question passed to adapter
		adapterAnswers = new ListAnswerAdapter(this, answerList, R.layout.element_list_answer);
		//pass  adapter to view
		this.setListAdapter(adapterAnswers);
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
			setRate(1);
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
			setRate(-1);
		}
		else {
			Toast.makeText(ShowQuestionActivity.this,"YOU ALREADY RATED THIS QUESTION",	Toast.LENGTH_SHORT).show();
		}	
	}

	/**
	 * This method get the local time and return it formated "MM.DD.YYYY at HH:MM:SS" 
	 * @return date
	 */
	public String getDateData(){
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String date= today.format("%d.%m.%Y at %H:%M:%S");
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

