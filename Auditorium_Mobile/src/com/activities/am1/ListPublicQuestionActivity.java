package com.activities.am1;

import java.util.ArrayList;
import java.util.List;


import com.adapters.am1.ListQuestionAdapter;
import com.models.am1.Constant;
import com.models.am1.Question;



import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
/**
 * ListPublicQuestionActivity
 * Activity class in which generic user can choose to ask a new question or to show old questions
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * Edited by Bernardo Plaza
 * Last edited on Januar 12th, 2013
 */
public class ListPublicQuestionActivity extends ListActivity {

	ListQuestionAdapter questionListAdapter = null;
	List<Question> questionsList = new ArrayList<Question>();

	int isTeacher=1;
	
	/**
	 * Default method to create an activity in which is configured the listener for the button buttonAskQuestion on click event.
	 * The list of question is passed to the adapter and the latter is passed to the view.
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_list_question);
		
		//! if you are a student just see the post and are able to ask a question
		//get if teacher
		//! teachers can add quiz and other options so the layout or the menu will be diferent.
		
		//! instead this we need to read them from the server
		//Model: List<Question>
		//Controller: Adapter
		//View: ListView o ListActivity
		questionsList.add(new Question("Support Center","Kann man als Member auch Announcements posten?","Wollte im konkreten Fall eine URL mit den anderen Membern teilen","Valdes", 6));
		questionsList.add(new Question("Intelligente Systeme", "Passwort", "Wir sollen das ja in der Übungsaufgabe ja anhand unserer Ergebnisse berechnen und zurückgeben. Gibt es dafür einen einfachen Weg, ohne numerische Methoden zu implementieren?", "Pique", 10));
		questionsList.add(new Question("Distributed Systems","what's the granularity?","in the comparison of client/server model and distributed object-oriented model, there is an granularity. What's the meaning of granularity?", "Puyol", 11));		
		questionsList.add(new Question("Software Fault Toleranc","When will the third task be released?","Hey Diogo, when will the third task be released?","Jordi Alba", 7));
		questionsList.add(new Question("Dystributed System", "book", "what is the title of the reference book?","Dani Alves", 5));
		questionsList.add(new Question("Security & Cryptography", "teacher", "who is the teacher?","Xavi Hernandez", 15));
		questionsList.add(new Question("Allgemeine und sonstige Fragen","Praktomat offline?","Heyho,ich wollte mal überprüfen ob der Praktomat noch funktioniert, aber beim Verbinden mit der Seite gibt ein Verbindungsproblem. Hat ihn wer offline genommen und wann kommt der wieder online?", "Messi",100));
		
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
		Intent showQuestionActivityIntent = new Intent(ListPublicQuestionActivity.this,ShowQuestionActivity.class);
		showQuestionActivityIntent.putExtra("Question_Showed", questionCurrent);
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
			Bundle extras = data.getExtras();
			Question question = (Question) extras.get("question");
			String course  = question.getCourse();
			String subject = question.getSubject();
			String content = question.getContent();
			String author= question.getAuthor();
			int numberOfRates= question.getNumberOfRates();
			//Add a new element to the list
			questionsList.add(new Question(course, subject, content, author, numberOfRates));
			questionListAdapter.notifyDataSetChanged();
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
					ListPublicQuestionActivity.this.finish();
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
        if (isTeacher==1){ menu.setGroupVisible(R.id.group_isteacher, true) ;}
        if (isTeacher==0){ menu.setGroupVisible(R.id.group_isteacher, false) ;}
        
        
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
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 
		Intent buttonIntent = new Intent(ListPublicQuestionActivity.this, AddQuestionActivity.class);
		//Launch an activity for which you would like a result when it finished		
		this.startActivityForResult(buttonIntent, Constant.ADD_PUBLIC_QUESTION);*/
		Intent intentLog = new Intent (ListPublicQuestionActivity.this, SendLoginRequestActivity.class); //getApplicationContext() LoginActivity.this
		//intent.putExtras(bundleLog);
		startActivity(intentLog);
		
	}

	public void goToCreateQuizActivity() {
		// TODO Auto-generated method stub
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 */
		Intent createQuiz = new Intent(ListPublicQuestionActivity.this, LoginActivity.class);
		startActivity(createQuiz);
		/*this.startActivityForResult(createQuiz, Constant.ADD_PUBLIC_QUESTION);
		
		Intent intent = new Intent (LoginActivity.this, SendLoginRequestActivity.class); //getApplicationContext() LoginActivity.this
		intent.putExtras(bundleLog);
		startActivity(intent);	*/
	}
	
	
	public void goToLogoutActivity() {
		// TODO Auto-generated method stub
		//! send data logout to server
		new AlertDialog.Builder(this).setTitle("Logout from Auditorium Mobile").setMessage("Do you really want to logout and exit?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				ListPublicQuestionActivity.this.finish();
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
	
	
}//end


