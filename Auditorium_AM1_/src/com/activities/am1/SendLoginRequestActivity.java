package com.activities.am1;

import com.activities.am1.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class SendLoginRequestActivity extends Activity {
	@Override
	public void onCreate (Bundle savedIntanceState){
		setContentView(R.layout.login_result);
		//Bundle bundle = getIntent().getExtras();
		TextView txtEmail = (TextView) findViewById(R.id.emailAddress_res);
		TextView txtPassword = (TextView) findViewById(R.id.password_res);
		CheckBox checkBoxTeacher = (CheckBox) findViewById(R.id.chekBox_teacher_res);
		
		//txtEmail.setText(bundle.getString("Email"));	
		//txtPassword.setText(bundle.getString("Password"));	
		
		//if (bundle.getBoolean("IsTeacher")==true){
			//checkBoxTeacher.setChecked(true);
		//}
		//else checkBoxTeacher.setChecked(false);
		
	}
	

}
