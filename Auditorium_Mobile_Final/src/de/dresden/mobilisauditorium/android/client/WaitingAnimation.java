package de.dresden.mobilisauditorium.android.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.activities.am1.AddAnswerActivity;
import com.activities.am1.AddQuestionActivity;
import com.activities.am1.LoginActivity;
import com.activities.am1.RegisterActivity;
import com.activities.am1.ShowQuestionActivity;
/**
 * This class is responsible for showing waiting animation while response is being
 * received and processed
 * @author MARCHELLO
 *
 */

public class WaitingAnimation extends AsyncTask<Void, Void, Boolean>{
	
	public WaitingAnimation(Activity activity){
		this.activity = activity;
		progress = new ProgressDialog(activity);

		if(activity instanceof RegisterActivity){
			progressLabel = "Registering . . .";
		}
		if(activity instanceof LoginActivity){
			progressLabel = "Authentication . . .";
		}
		if(activity instanceof AddQuestionActivity){
			
			progressLabel = "Posting the question . . .";
		}

		if(activity instanceof ShowQuestionActivity){
			progressLabel = "Refreshing answer list . . .";
		}
		if(activity instanceof AddAnswerActivity){
			progressLabel = "Posting the answer . . .";
		}
	}
	
	
	public void setProgressBarLabel(String label){
		this.progressLabel = label;
	}

	ProgressDialog progress;
	Activity activity;
	String progressLabel = null;
	
	protected void onPreExecute(){
		this.progress.setMessage(progressLabel);
		this.progress.show();
		
	}
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		while(!this.isCancelled()){	}
		return true;
	}
	
	@Override
	protected void onCancelled(){
		if(this.progress.isShowing()){
			progress.dismiss();
		}
	}
	
	@Override
    protected void onPostExecute(final Boolean success) {
		if(this.progress.isShowing()){
			progress.dismiss();
		}
		
	}
	
	
	
};

