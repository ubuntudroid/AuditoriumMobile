package com.activities.am1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


import com.adapters.am1.ListQuestionAdapter;
import com.models.am1.Answer;
import com.models.am1.Constant;
import com.models.am1.Question;



import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Toast;
/**
 * ListPublicQuestionActivity
 * ListActivity class in which shows the questions depending on the role (student/teacher), user can also
 * create a new question, logout or sort the list by Rates or Dates.
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * Edited by Bernardo Plaza
 * Last edited on January 18th, 2013
 */
public class ListQuestionsActivity extends ListActivity {

	ListQuestionAdapter questionListAdapter = null;
	List<Question> questionsList = new ArrayList<Question>();
	boolean isTeacher=false;
	boolean toggleRate=false;
	boolean toggleDate=false;
	Bundle bundleFinalLogin=null;
	
	
	/**
	 * Default method which reads the items from the server and display them
	 * The list of question is passed to the adapter and the latter is passed to the view.
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_list_question);
		
		bundleFinalLogin = getIntent().getExtras();
		isTeacher = bundleFinalLogin.getBoolean("IsTeacherLoged");
		
		// Do Not show the virtual keyboard only if the user press a editText
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		//! instead this we need to read them from the server
		//Model: List<Question>
		//Controller: Adapter
		//View: ListView o ListActivity add date and question index answer list private
		questionsList.add(new Question(0000001,"Support Center","Kann man als Member auch posten?","Wollte im konkreten Fall eine URL mit den anderen Membern teilen","Valdes", "10.11.2014 at 23:22:25", 6, 0));
		questionsList.add(new Question(0000002,"Intelligente Systeme", "Passwort", "Wir sollen das ja in der Übungsaufgabe ja anhand unserer Ergebnisse berechnen und zurückgeben. Gibt es dafür einen einfachen Weg, ohne numerische Methoden zu implementieren?", "Pique", "10.11.2014 at 21:22:23",10, 1));
		questionsList.add(new Question(0000003,"Distributed Systems","what's the granularity?","in the comparison of client/server model and distributed object-oriented model, there is an granularity. What's the meaning of granularity?", "Puyol","11.11.2014 at 21:22:25", 11, 1));		
		questionsList.add(new Question(0000004,"Software Fault Toleranc","When will the third task be released?","Hey Diogo, when will the third task be released?","Jordi Alba", "10.11.2014 at 21:22:25",7,0));
		questionsList.add(new Question(0000005,"Dystributed System", "book", "what is the title of the reference book?","Dani Alves","10.10.2014 at 21:22:25",5,1));
		questionsList.add(new Question(0000006,"Security & Cryptography", "teacher", "who is the teacher?","Xavi Hernandez","10.11.2011 at 21:22:23", 15,0));
		questionsList.add(new Question(0000007,"Allgemeine und sonstige Fragen","Praktomat offline?","Heyho,ich wollte mal überprüfen ob der Praktomat noch funktioniert, aber beim Verbinden mit der Seite gibt ein Verbindungsproblem. Hat ihn wer offline genommen und wann kommt der wieder online?", "Messi","10.11.2015 at 21:22:23",100,1));
		
		//delete private questions from list
		if (!isTeacher){
			Iterator<Question> it = questionsList.iterator();
			while (it.hasNext()) {
	           Question q = it.next();
	            if (q.getPrivacy()==0) {
	            	it.remove();
	            }
	        }
		}
		//list of question passed to adapter
		questionListAdapter = new ListQuestionAdapter(this, questionsList,R.layout.element_list_question);
		//pass  adapter to view
		this.setListAdapter(questionListAdapter);
	}
	/**
	 * This method permits to show a specific question when a general user. 
	 * click on item (a question) of the adapted list (only two field: name of the course and title of the question).
	 * @param l ListView 
	 * @param v View 
	 * @param position int 
	 * @param id long  
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		Question questionCurrent = (Question) getListAdapter().getItem(position);
		Intent showQuestionActivityIntent = new Intent(ListQuestionsActivity.this,ShowQuestionActivity.class);
		showQuestionActivityIntent.putExtra("Question_Showed", questionCurrent);
		showQuestionActivityIntent.putExtras(bundleFinalLogin);
		startActivity(showQuestionActivityIntent);
	}

	/** This method pop the results(extras) from the Intent and use those to create a new object Question to add at list.
	 * @param requestCode int
	 * @param resultCode int 
	 * @param data Intent 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("ListQuestionActivity", "come back from AddQuestionActivity");
		/* This control is important to don't crash the system when you open the form
		 *  of the question and click on previous button without submit nothing and the 
		 *  data(intent)is null */
		if(data!=null){
			/*pop the result passed with Intent in the other activity making a cast because 
			 * the type returned is an Object*/
			switch (requestCode){
			case Constant.ADD_QUESTION: 
				if (resultCode == RESULT_OK){
					
					Bundle extras = data.getExtras();
					Question question = (Question) extras.get("question");
					questionsList.add(question);
					questionListAdapter.notifyDataSetChanged();
				}
				
				break;
			default: break;
			}
		}
	}

	/**
	 * Ask to the user if he/she is sure to leave the application.
	 * @param keyCode int  
	 * @param event KeyEvent 
	 * @return boolean
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
					ListQuestionsActivity.this.finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    	
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_public, menu);   
        if (isTeacher==true){ menu.setGroupVisible(R.id.group_isteacher, true) ;}
        if (isTeacher==false){ menu.setGroupVisible(R.id.group_isteacher, false) ;}
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menu_add_question:
    		goToAddQuestionActivity();
    		return true;
    	case R.id.menu_logout:
    		goToLogoutActivity();
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
	 * This method permits to pass to a new activity (AddQuestionActivity) when the user press on a button Add Question.
	 * 
	 */
	public void goToAddQuestionActivity() {
		// TODO Auto-generated method stub
		Intent AddQIntent = new Intent(ListQuestionsActivity.this, AddQuestionActivity.class);
		AddQIntent.putExtras(bundleFinalLogin);
		//Launch an activity for which you would like a result when it finished		
		this.startActivityForResult(AddQIntent, Constant.ADD_QUESTION);
		
	}

	public void goToCreateQuizActivity() {
		// TODO Auto-generated method stub
		Intent createQuiz = new Intent(ListQuestionsActivity.this, CreateQuizActivity.class);
		startActivity(createQuiz);
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
				finish();
				Intent goLogin = new Intent(ListQuestionsActivity.this, LoginActivity.class);
				startActivity(goLogin);
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
	 * This method get the local time and return it formated "MM.DD.YYYY at HH:MM:SS" 
	 * @return date
	 */
	public String getDateData(){
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		String date=today.format("%d.%m.%Y at %H:%M:%S");
		return date;
	}

	public void sortByRates(){
		Collections.sort(questionsList, new Comparator<Question>(){
			public int compare(Question q0, Question q1) {
				// TODO Auto-generated method stub
				int rateQ1=0;
				int rateQ2=0;
				if(!toggleRate){
					rateQ1=q0.getNumberOfRates();
					rateQ2=q1.getNumberOfRates();
				}
				else {
					rateQ1=q1.getNumberOfRates();
					rateQ2=q0.getNumberOfRates();
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
	

}//end


