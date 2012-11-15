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

public class ListPublicQuestionActivity extends ListActivity {

	Button buttonAskQuestion = null;
	ListQuestionAdapter questionListAdapter = null;
	List<Question> questionsList = new ArrayList<Question>();

	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		Question questionCurrent = (Question) getListAdapter().getItem(position);
		Intent showQuestionActivityIntent = new Intent(ListPublicQuestionActivity.this,ShowQuestionActivity.class);
		showQuestionActivityIntent.putExtra("Question_Showed", questionCurrent);
		startActivity(showQuestionActivityIntent);
	}
	
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

	/* This method pop the results(extras) from the Intent.
	 * We can use a switch case in the case the activities are more then one */
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

	public void manageButton(View v) {
		// TODO Auto-generated method stub
		/*With Intent you can pass standard type (string, int, float, etc) or defined by me.
		 * But the class have to implements Serializable (see class Question)
		 */
		Intent buttonIntent = new Intent(ListPublicQuestionActivity.this, AddQuestionActivity.class);
		//Launch an activity for which you would like a result when it finished		
		this.startActivityForResult(buttonIntent, Constant.ADD_PUBLIC_QUESTION);
	}
	
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
