package com.activities.am1;

import com.models.am1.Constant;

import de.dresden.mobilisauditorium.android.client.Values;
import de.dresden.mobilisauditorium.android.client.WaitingAnimation;
import de.dresden.mobilisauditorium.android.client.proxy.Question;
import de.dresden.mobilisauditorium.android.client.proxy.postQuestionRequest;
import de.dresden.mobilisauditorium.android.client.proxy.postQuestionResponse;
import de.dresden.mobilisauditorium.android.engine.Singleton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * AddQuestionActivity
 * Create a new question. It needs 3 different fields:
 * courseTextField contains the course of the question.
 * subjectTextField contains the topic of the question.
 * contentTextField contains the content of the question.
 * It is also possible to check the question as private so only Admin or Teachers can see it.
 * For that the field privated is updated 
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * updated by Bernardo Plaza
 * last updated January 22th, 2013
 */
public class AddQuestionActivity extends Activity {
	Button   buttonSubmitQuestion = null;
	EditText courseTextField  = null;
	EditText subjectTextField = null;
	EditText contentTextField = null;
	CheckBox chkPrivateQuestion = null;
	ScrollView scrollViewGeneralView = null;
	String questionAuthor=null;
	String questionDate=null;
	int privated =0;
	
	Singleton mSingleton = null;
	WaitingAnimation animation = null;

	/**
	 * Create a new question. It needs 3 different fields:
	 * courseTextField contains the course of the question.
	 * subjectTextField contains the topic of the question.
	 * contentTextField contains the content of the question.
	 * It is also possible to check the question as private so only Admin or Teachers can see it.
	 * For that the field privated is updated 
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_question);
		mSingleton = Singleton.getInstance();
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
	
	 
	/** The response object from Singleton is received and processed.
	 *  If the message is posted successfully its changes to the activity ListQuestionsActivity
	 *  if not, Toast is showed.
	 */
	private Handler onReceived = new Handler(){
	    	@Override
	    	public void handleMessage(Message msg){
	    		Log.i("AddQuestionActivity", "Message came!" );

	    		if (msg.obj instanceof postQuestionResponse){
	    			Log.i("AddQuestionActivity", "Message is response!" );
	    			animation.cancel(true);
	    			Log.i("AddQuestionActivity","Animation canceled!" );
	    			postQuestionResponse response = (postQuestionResponse)msg.obj;
	    			int err = response.getErrortype();
	    			switch(err){
	    			case Values.QuestionPostedSuccessfully:{
	    				//Toast.makeText(getApplicationContext(), "Question was successfully posted!", Toast.LENGTH_SHORT).show();
	    				Intent intent = new Intent(AddQuestionActivity.this,ListQuestionsActivity.class);
	    				startActivity(intent);
	    				finish();
	    				break;
	    			}
	    			case Values.QuestionWasNotAdded:{
	    				Toast.makeText(getApplicationContext(), "Error occured while adding question!", Toast.LENGTH_SHORT).show();
	    				break;
	    			}
	    			default:{ break;}
	    			}
	    			
	    		}
	    	}
	    };

	    /** 
	     * Launched any time to start interacting with the user. The mSingleton is updated 
	     */
	    @Override
	    protected void onResume(){
	    	super.onResume();
	    	mSingleton.registerHandler(onReceived);
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
	     * Permits to show an advertisement message if CheckBox is checked.
	     * @param v View
	     */
	    public void  manageCheckBox(View v) {
	    	//if is checked there is an advertisement message
	    	if (((CheckBox) v).isChecked()) {
	    		Toast.makeText(AddQuestionActivity.this, R.string.checkedBox, Toast.LENGTH_LONG).show();
	    		privated=1;
	    	}
	    	else privated =0;
	    }
	    /**
	     * Permits on first thing to do a control if there are any empty field,
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
	    	if(subject.equals("") || course.equals("") || content.equals("") 
	    			&& (content.length()< Constant.MINIMUM_QUESTION_SIZE)){
	    		/*show a message for a long(or short)period */
	    		Toast.makeText(this, R.string.errorEmptyString, Toast.LENGTH_LONG).show();
	    	}
	    	else{

	    		Question question = new Question();
	    		question.setContent(content);
	    		question.setCourse(course);
	    		question.setSubject(subject);
	    		question.setAuthor(mSingleton.userProfile.getNickname());
	    		question.setRate(0);
	    		question.setDate(getDateData());
	    		if (chkPrivateQuestion.isChecked()){
	    			question.setSecurity(1);
	    		}else{
	    			question.setSecurity(0);
	    		}

	    		animation = new WaitingAnimation(this);
	    		animation.execute();
	    		animation.setProgressBarLabel("Publishing the question...");
	    		mSingleton.OutStub.sendXMPPBean(new postQuestionRequest(question));

	    	}
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
	    		finish();
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
}
