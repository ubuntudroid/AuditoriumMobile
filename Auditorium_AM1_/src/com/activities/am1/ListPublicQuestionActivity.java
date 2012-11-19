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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
/**
 * ListPublicQuestionActivity
 * Activity class in which generic user can choose to ask a new question or to show old questions
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 */
public class ListPublicQuestionActivity extends ListActivity {

	Button buttonAskQuestion = null;
	ListQuestionAdapter questionListAdapter = null;
	List<Question> questionsList = new ArrayList<Question>();

	/**
	 * Default method to create an activity in which is configured the listener for the button buttonAskQuestion on click event.
	 * The list of question is passed to the adapter and the latter is passed to the view.
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_list_question);
		buttonAskQuestion = (Button) findViewById(R.id.buttonListQuestion);
		//Configure  listener for the button on click event
		buttonAskQuestion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				manageButton(v);
			}
		});
		//Model: List<Question>
		//Controller: Adapter
		//View: ListView o ListActivity

		//list of question passed to adapter
		questionListAdapter = new ListQuestionAdapter(this, questionsList,R.layout.element_list);
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
			//Add a new element to the list
			questionsList.add(new Question(course, subject, content));
			questionListAdapter.notifyDataSetChanged();
		}
	}
	/**
	 * This method permits to pass to a new activity (AddQuestionActivity) when the user press on a button Add Question.
	 * @param v View
	 */
	public void manageButton(View v) {
		// TODO Auto-generated method stub
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 */
		Intent buttonIntent = new Intent(ListPublicQuestionActivity.this, AddQuestionActivity.class);
		//Launch an activity for which you would like a result when it finished		
		this.startActivityForResult(buttonIntent, Constant.ADD_PUBLIC_QUESTION);
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
}
