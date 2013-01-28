package com.activities.am1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
import android.view.WindowManager;
import android.widget.ListView;

import com.adapters.am1.ListQuestionAdapter;

import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.Question;
import de.dresden.mobilisauditorium.android.client.proxy.postAnswerRequest;
import de.dresden.mobilisauditorium.android.client.proxy.rateAnswerRequest;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionRequest;
import de.dresden.mobilisauditorium.android.client.proxy.refreshQuestionListRequest;
import de.dresden.mobilisauditorium.android.client.proxy.refreshQuestionListResponse;
import de.dresden.mobilisauditorium.android.engine.Singleton;
/**
 * ListPublicQuestionActivity
 * Exteds ListActivity class, it shows the questions, using the menu button user can also
 * create a new question, logout or sort the list by Rates or Dates.
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * Edited by Bernardo Plaza
 * Last edited on January 23th, 2013
 */
public class ListQuestionsActivity extends ListActivity {

	ListQuestionAdapter questionListAdapter = null;
	List<Question> questionsList = null; // new ArrayList<Question>();
	boolean isTeacher=false;
	boolean toggleRate=false;
	boolean toggleDate=false;
	Bundle bundleFinalLogin=null;
	
	Singleton mSingleton = null;
	WaitingAnimation animation = null;
	
	/**
	 * The service for notification is created.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_list_question);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		mSingleton = Singleton.getInstance();
		Intent intent = new Intent(ListQuestionsActivity.this,SimpleService.class);
		startService(intent);
	}
	
	
	/** Handler is responsible for asynchronous receiving the response from server while
	 * waiting animation is running! Here is where the response is being processed and the GUI
	 * of the activity is updated
	 */
	 private Handler onReceived = new Handler(){
	    	@Override
	    	public void handleMessage(Message msg){
	    		Log.i("ListPublicQuestionActivity", "Message came!" );

	    		if (msg.obj instanceof refreshQuestionListResponse){
	    			Log.i("ListPublicQuestionActivity", "Message is response!" );
	    			animation.cancel(true);
	    			Log.i("ListPublicQuestionActivity","Animation canceled!" );
	    			refreshQuestionListResponse response = (refreshQuestionListResponse)msg.obj;
	    			questionsList = new ArrayList<Question>(response.getQuestionlist().getQuestion());
	    				if (mSingleton.userProfile.accountType==0){
	    						Iterator<Question> it = questionsList.iterator();
	    						while (it.hasNext()) {
	    				           Question q = it.next();
	    				            if (q.getSecurity()==1) {
	    				            	it.remove();
	    				            }
	    				        }
	    					}
	    			sortByRates();
	    			refreshQuestionList();	
	    			
	    		}
	    	}
	    };
	    
	    /**
	     * Update the questionListAdapter 
	     * 
	     */
	    public void refreshQuestionList(){
			
			questionListAdapter = new ListQuestionAdapter(this,questionsList ,R.layout.element_list_question);
			//pass  adapter to view
			this.setListAdapter(questionListAdapter);
			questionListAdapter.notifyDataSetChanged();
			
			Log.i("LISTQA", "questionsList" + String.valueOf(questionsList.size()));	
		}
	    
	    /** 
	     * Launched any time to start interacting with the user. 
	     * call the method refresh(); to update the question list
	     */
	    @Override
	    protected void onResume(){
	    	super.onResume();
	    	refresh();
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
	 * Permits to show a specific question when a general user. 
	 * click on item (a question) of the adapted list (only two field: name of the course and title of the question).
	 * @param l ListView the question list
	 * @param position int position of the table
	 * 
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		Question questionCurrent = (Question) getListAdapter().getItem(position);
		Intent showQuestionActivityIntent = new Intent(ListQuestionsActivity.this,ShowQuestionActivity.class);
		showQuestionActivityIntent.putExtra("Question_Showed", questionCurrent);
		startActivity(showQuestionActivityIntent);
		finish();
	}

	/**
	 * Ask the user if is sure to leave the application.
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(this).setTitle("Exit from Auditorium Mobile").setMessage("Do you really want to exit?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
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

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Create the menu options using two different views depending of the role of the user
	 * Is ready to show the create quiz and other actions which only teachers can do.
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    	
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_public, menu);   
        if (isTeacher==true){ menu.setGroupVisible(R.id.group_isteacher, true) ;}
        if (isTeacher==false){ menu.setGroupVisible(R.id.group_isteacher, false) ;}
        
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
    		goToAddQuestionActivity();
    		return true;
    	case R.id.menu_logout:
    		goToLogoutActivity();
    		return true;
    	case R.id.menu_settings:
    		goSettingsActivity();
    		return true;
    	case R.id.menu_quiz:
    		goToCreateQuizActivity();
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
    * Runs SettingsActivity used for configure notifications
    * 
    */
    private void goSettingsActivity() {
		startActivity(new Intent(this,SettingsActivity.class));
	}

	/**
	 * Permits to pass to a new activity (AddQuestionActivity).
	 * 
	 */
	public void goToAddQuestionActivity() {
		// TODO Auto-generated method stub
		Intent AddQIntent = new Intent(ListQuestionsActivity.this, AddQuestionActivity.class);
		startActivity(AddQIntent);
		finish();
	}

	/**
	 * Runs CreateQuizActivity
	 * only accesible by teachers
	 * 
	 */
	public void goToCreateQuizActivity() {
		// TODO Auto-generated method stub
		Intent createQuiz = new Intent(ListQuestionsActivity.this, CreateQuizActivity.class);
		startActivity(createQuiz);
		finish();
	}
	
	/**
	 * Log the user out of the system going back to LoginActivity
	 * 
	 */
	public void goToLogoutActivity() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setTitle("Logout from Auditorium Mobile").setMessage("Do you really want to logout and exit?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				mSingleton.Online = false;
				Intent goLogin = new Intent(ListQuestionsActivity.this, LoginActivity.class);
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
	 * Get the local time and return it formated "MM.DD.YYYY at HH:MM:SS" 
	 * @return date
	 * 
	 */
	public String getDateData(){
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String date=today.format("%d.%m.%Y at %H:%M:%S");
		return date;
	}

	/**
	 * Used a comparator with questionsList to sort the list by number of rates
	 * the boolean toggleRate is used to change the order of the sorting from
	 * the highest number of rates to lower or viceversa  
	 * 
	 */
	public void sortByRates(){
		Collections.sort(questionsList, new Comparator<Question>(){
			public int compare(Question q0, Question q1) {
				// TODO Auto-generated method stub
				int rateQ1=0;
				int rateQ2=0;
				if(!toggleRate){
					rateQ1=q0.getRate();
					rateQ2=q1.getRate();
				}
				else {
					rateQ1=q1.getRate();
					rateQ2=q0.getRate();
				}
				
				if ( rateQ2 > rateQ1 ) {return 1;}
				else if ( rateQ2 < rateQ1 ) { return -1;} 
				else {return 0;}
			}
	    });
		toggleRate=!toggleRate;
		
		//list of question passed to adapter
		questionListAdapter = new ListQuestionAdapter(this, questionsList,R.layout.element_list_question);
		//pass  adapter to view
		this.setListAdapter(questionListAdapter);
	}

	/**
	 * Used a comparator with questionsList to sort the list by date
	 * the boolean toggleDate is used to change the order of the sorting from
	 * the newest question to older or viceversa  
	 * 
	 */
	public void sortByDate(){
		Collections.sort(questionsList, new Comparator<Question>(){
	
			public int compare(Question q0t, Question q1t) {
				// TODO Auto-generated method stub
				Question q0=null;
				Question q1=null;
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
		questionListAdapter = new ListQuestionAdapter(this, questionsList,R.layout.element_list_question);
		//pass  adapter to view
		this.setListAdapter(questionListAdapter);
	}
	
	/**
	 * Refresh the question list ç
	 * 
	 */
	public void refresh(){
		animation = new WaitingAnimation(this);
    	animation.setProgressBarLabel("Refreshing questions...");
    	animation.execute();
    	mSingleton.registerHandler(onReceived);
    	mSingleton.OutStub.sendXMPPBean(new refreshQuestionListRequest());
	}

}//end


