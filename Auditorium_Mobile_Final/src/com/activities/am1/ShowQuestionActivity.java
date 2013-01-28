package com.activities.am1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapters.am1.ListAnswerAdapter;
import com.models.am1.Constant;

import de.dresden.mobilisauditorium.android.client.Values;
import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.Answer;
import de.dresden.mobilisauditorium.android.client.proxy.Question;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionNegativeRequest;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionNegativeResponse;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionPositiveRequest;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionPositiveResponse;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionRequest;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionResponse;
import de.dresden.mobilisauditorium.android.client.proxy.refreshAnswerListRequest;
import de.dresden.mobilisauditorium.android.client.proxy.refreshAnswerListResponse;
import de.dresden.mobilisauditorium.android.engine.Singleton;

/**
 * ShowQuestionActivity
 * Default method to create an activity in which there has been obtained the extras parameters 
 * from Intent control (if the button Submit Question is pushed) and  has been taken the object Question 
 * and related parameters from extras.
 * Finally each field of the question is field with the parameters obtained.
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * updated by Bernardo Plaza
 * last updated on January 23th, 2013
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
	
	WaitingAnimation animation = null;
	Singleton mSingleton = null;
	Question questionPassed = null;
	
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
		mSingleton = Singleton.getInstance();
		bundleFinalLogin =  getIntent().getExtras();
		// Do Not show the virtual keyboard only if the user press a editText
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//here you can get extras parameters from Intent
		Bundle extras = getIntent().getExtras();
		//here you can get the object Question and relatives parameters from extras
		questionPassed = (Question) extras.get("Question_Showed");
		String coursePassed  = questionPassed.getCourse();
		String subjectPassed = questionPassed.getSubject();
		String contentPassed = questionPassed.getContent();
		String authorPassed= questionPassed.getAuthor();
		String datePassed= questionPassed.getDate();
		int indexPassed= questionPassed.getQuestion_index();
		int privacyPassed = questionPassed.getSecurity();
		
		int numberOfRatesPassed= questionPassed.getRate();
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

		String datePassedtest = getDateData();
	
		imageButtonPositive.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				String user = mSingleton.userProfile.getEmailAddress();
				mSingleton.OutStub.sendXMPPBean(new rateQuestionPositiveRequest(mSingleton.userProfile.getEmailAddress(),questionPassed.getQuestion_index()));
			}
		});
		
		imageButtonNegative.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				String user = mSingleton.userProfile.getEmailAddress();
		    	mSingleton.OutStub.sendXMPPBean(new rateQuestionNegativeRequest(mSingleton.userProfile.getEmailAddress(),questionPassed.getQuestion_index()));
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
	    		Log.i("ShowQuestionActivity", "Message came!" );

	    		if (msg.obj instanceof rateQuestionPositiveResponse){
	    			Log.i("ShowQuestionActivity", "Message is response!" );
	    		//	animation.cancel(true);
	    		//	animation = null;
	    			Log.i("ShowQuestionActivity","Animation canceled!" );
	    			rateQuestionPositiveResponse response = (rateQuestionPositiveResponse)msg.obj;
	    			/*switch(response.getErrortype()){
	    			case Values.AlreadyRatedPositive:{
	    				Toast.makeText(getApplicationContext(), "Already rated positive!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			default:{
	    				
	    				break;
	    			}
	    			}*/
	    			txtview_Rates.setText(String.valueOf(response.getErrortype()));
	    			   	
	    		}
	    		
	    		if (msg.obj instanceof rateQuestionNegativeResponse){
	    			Log.i("ShowQuestionActivity", "Message is response!" );
	    		//	animation.cancel(true);
	    		//	animation = null;
	    			Log.i("ShowQuestionActivity","Animation canceled!" );
	    			rateQuestionNegativeResponse response = (rateQuestionNegativeResponse)msg.obj;
	    			/*switch(response.getErrortype()){
	    			case Values.AlreadyRatedNegative:{
	    				Toast.makeText(getApplicationContext(), "Already rated negative!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			default:{
	    				
	    				break;
	    			}
	    			}*/
	    			txtview_Rates.setText(String.valueOf(response.getErrortype()));
	    	
	    		}
	    		
	    		if (msg.obj instanceof refreshAnswerListResponse){
	    			Log.i("ShowQuestionActivity", "Message is response!" );
	    			animation.cancel(true);
	    		//	animation = null;
	    			Log.i("ShowQuestionActivity","Animation canceled!" );
	    			refreshAnswerListResponse response = (refreshAnswerListResponse)msg.obj;
	    			switch(response.getErrortype()){
	    			case Values.QuestionDoesNotExist:{
	    				Toast.makeText(getApplicationContext(), "This question was removed!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			case Values.AnswerListRefreshedSucc:{
	    				Question q = (Question)response.getQuestion();
	    				txtview_Rates.setText(String.valueOf(q.getRate()));
	    				authorTextField.setText(q.getAuthor());
	    				answerList = new ArrayList<Answer>(response.getAnswerlist().getAnswer());
	    				refreshAnswerList();
	    				break;
	    			}
	    			
	    			default:{
	    				txtview_Rates.setText(String.valueOf(response.getErrortype()));
	    				break;
	    			}
	    				
	    		}
	    	
	    		}
	    	}
	    };
	    
	    /**
	     * Refresh the answer list and sort it by rates
	     */
	    public void refreshAnswerList(){
	    
	    	sortByRates();
	    	adapterAnswers = new ListAnswerAdapter(this, answerList, R.layout.element_list_answer);
			listViewAnswers.setAdapter(adapterAnswers);
			adapterAnswers.notifyDataSetChanged();
	    }
	    
	    
	    @Override
	    protected void onResume(){
	    	super.onResume();
	    	animation = new WaitingAnimation(this);
	    	animation.execute();
	    	animation.setProgressBarLabel("Refreshing answers . . .");
	    	mSingleton.registerHandler(onReceived);
	    	mSingleton.OutStub.sendXMPPBean(new refreshAnswerListRequest(questionPassed.getQuestion_index()));
	    	
	    }
	    
	    @Override
	    protected void onPause(){
	    	super.onPause();
	    	mSingleton.unregisterHandler(onReceived);
	    }
	
	    /**
		 * Create the menu options, the same menu of listquestion is used so the text 
		 * has to be modified to add answer
		 * 
		 */
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
	 
	 /**
	  * Call the different method once the menu items are selected
	  * 
	  */  
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.menu_refresh:
	    		refresh();
	    		return true;
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
		/*	switch (requestCode){
				
				case Constant.ADD_ANSWER:
					if (resultCode == RESULT_OK){
						Bundle extras = data.getExtras();
						Answer answer = (Answer) extras.get("answer");
						answerList.add(answer);
						adapterAnswers.notifyDataSetChanged();
					}
			}*/
		}
	}
	/**
	 * Permits to pass to a new activity (AddAnswerActivity) 
	 * 
	 */
	public void goToAddAnswerActivity() {
		// TODO Auto-generated method stub
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 */
		Intent buttonIntent = new Intent(ShowQuestionActivity.this, AddAnswerActivity.class);
		//Launch an activity for which you would like a result when it finished		
		buttonIntent.putExtra("question_index",questionPassed.getQuestion_index());
		Log.i("ShowQuestionActivity", String.valueOf(questionPassed.getQuestion_index()));
		this.startActivityForResult(buttonIntent, Constant.ADD_ANSWER);
		
	}
	
	/**
	 * Log the user out of the system going back to LoginActivity
	 * 
	 */
	public void goToLogoutActivity() {
		// TODO Auto-generated method stub
		//! send data logout to server
		new AlertDialog.Builder(this).setTitle("Logout from Auditorium Mobile").setMessage("Do you really want to logout and exit?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				//!Logout
				mSingleton.Online = false;
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
	
	/**
	 * Used a comparator with answerList to sort the list by number of rates
	 * the boolean toggleRate is used to change the order of the sorting from
	 * the highest number of rates to lower or viceversa  
	 * 
	 */
	public void sortByRates(){
		Collections.sort(answerList, new Comparator<Answer>(){
			public int compare(Answer a0, Answer a1) {
				// TODO Auto-generated method stub
				int rateA1=0;
				int rateA2=0;
				if(!toggleRate){
					rateA1=a0.getRate();
					rateA2=a1.getRate();
				}
				else {
					rateA1=a1.getRate();
					rateA2=a0.getRate();
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

	/**
	 * Used a comparator with answerList to sort the list by date
	 * the boolean toggleDate is used to change the order of the sorting from
	 * the newest answerto older or viceversa  
	 * 
	 */
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
	
	/**
	 * Rate positive a question
	 * @param rateToServer update the rate
	 * @return return the value of the rete
	 */
	public int setRate(int rateToServer){
		int resultOfRate=1;
		FLAG_RATED=true;
		return resultOfRate;
	}
	
	public void ratedPositive(View arg0){
		
	}
	
	public void ratedNegative(View arg0){

		
	}

	/**
	 * Get the local time and return it formated "MM.DD.YYYY at HH:MM:SS" 
	 * @return date
	 */
	public String getDateData(){
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String date= today.format("%d.%m.%Y at %H:%M:%S");
		return date;
	}
	
	/**
	 * Kill the activity if back button is pressed
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        Intent intent = new Intent(ShowQuestionActivity.this, ListQuestionsActivity.class);
	        startActivity(intent);
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
	/**
	 * Refresh the answer list
	 */
	public void refresh(){
		animation = new WaitingAnimation(this);
    	animation.execute();
    	animation.setProgressBarLabel("Refreshing answers . . .");
    	mSingleton.registerHandler(onReceived);
    	mSingleton.OutStub.sendXMPPBean(new refreshAnswerListRequest(questionPassed.getQuestion_index()));
	}
	
}

