package com.activities.am1;

import com.models.am1.Question;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * AddQuestionActivity
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 */
public class AddQuestionActivity extends Activity {
	Button   buttonSubmitQuestion = null;
	EditText courseTextField  = null;
	EditText subjectTextField = null;
	EditText contentTextField = null;
	CheckBox chkPrivateQuestion = null;
	ScrollView scrollViewGeneralView = null;

	/**
	 * Default method to create an activity in which there is control if the button Submit Question is pushed
	 * or the checkbox is checked.
	 * @param savedInstanceState Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_question);
		courseTextField      = (EditText) findViewById(R.id.editText_CourseQuestion_AddQuestion);
		subjectTextField     = (EditText) findViewById(R.id.editText_SubjectQuestion_AddQuestion);
		contentTextField     = (EditText) findViewById(R.id.editText_ContentQuestion_AddQuestion);
		buttonSubmitQuestion = (Button)   findViewById(R.id.button_SubmitQuestion_AddQuestion);
		chkPrivateQuestion   = (CheckBox) findViewById(R.id.chekBox_PrivateQuestion_AddQuestion);
		buttonSubmitQuestion.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				manageButton(v);
			}
		});
		chkPrivateQuestion.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				manageCheckBox(v);
			}
		});
	}
	/**
	 * This method permits to show an advertisement message if checkbox is checked.
	 * @param v View
	 */
	public void  manageCheckBox(View v) {
		//if is checked there is an advertisement message
		if (((CheckBox) v).isChecked()) {
			Toast.makeText(AddQuestionActivity.this, R.string.checkedBox, Toast.LENGTH_LONG).show();

		}
	}
	/**
	 * This method permits on first thing to do a control if there are any empty field,
	 * to  fill in the parameters in each field and finally to create a new object Question.
	 * This object is passed to another activity through the use of an  intent  and  it is looking forward to the results.
	 * Finally the activity is  terminated and when the user push the come back button, the activity is deallocated.  
	 * @param v View
	 */
	public void manageButton(View v) {
		// TODO Auto-generated method stub
		String course  = courseTextField.getText().toString();
		String subject = subjectTextField.getText().toString();
		String content = contentTextField.getText().toString();

		/*We don't want empty fields so we have to check on subject,course and content field */
		if(subject.equals("") || course.equals("") || content.equals("")){
			/*show a message for a long(or short)period */
			Toast.makeText(this, R.string.errorEmptyString, Toast.LENGTH_LONG).show();
		}
		else{

			/*Create an obj Question with three field*/
			Question question = new Question(course,subject,content);

			/*Create a new Intent with a source and destination */
			Intent chekBoxIntent = new Intent(AddQuestionActivity.this, ListPublicQuestionActivity.class);

			/*There is the obj Question to pass to the other activity (destination)*/
			chekBoxIntent.putExtra("question", question);

			/*Set result with primitive constant of Android*/
			setResult(RESULT_OK,chekBoxIntent);

			/*the activity are allocated as a stack memory so if you don't termin it and you tip come
			 back button the activity is still there */
			finish();
		}
	}
}
