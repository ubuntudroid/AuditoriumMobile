package com.activities.am1;



import com.activities.am1.*;
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

public class AddQuestionActivity extends Activity {
	Button   buttonSubmitQuestion = null;
	EditText courseTextField  = null;
	EditText subjectTextField = null;
	EditText contentTextField = null;
	CheckBox chkPrivateQuestion = null;
	ScrollView scrollViewGeneralView = null;

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

	public void  manageCheckBox(View v) {
		//if is checked there is an advertissment message
		if (((CheckBox) v).isChecked()) {
			Toast.makeText(AddQuestionActivity.this, R.string.checkedBox, Toast.LENGTH_LONG).show();

		}
	}

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
