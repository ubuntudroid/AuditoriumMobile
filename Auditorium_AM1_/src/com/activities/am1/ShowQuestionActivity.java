package com.activities.am1;



import com.models.am1.Question;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

public class ShowQuestionActivity extends Activity {
	EditText courseTextField  = null;
	EditText subjectTextField = null;
	EditText contentTextField = null;
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
		//assign to the id of the object the field of the Question object
		courseTextField = (EditText) findViewById(R.id.editText_CourseQuestion_ShowQuestion);
		courseTextField.setText(coursePassed); 
		subjectTextField = (EditText) findViewById(R.id.editText_SubjectQuestion_ShowQuestion);
		subjectTextField.setText(subjectPassed);
		contentTextField = (EditText) findViewById(R.id.editText_ContentQuestion_ShowQuestion);
		contentTextField.setText(contentPassed); 
    }

}
