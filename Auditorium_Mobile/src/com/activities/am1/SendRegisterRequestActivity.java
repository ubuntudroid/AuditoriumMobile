package com.activities.am1;
import com.activities.am1.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class SendRegisterRequestActivity extends Activity {
	@Override
	public void onCreate (Bundle savedIntanceState){
		setContentView(R.layout.login_result);
		Bundle bundle = getIntent().getExtras();
		TextView txtEmailReg = (TextView) findViewById(R.id.emailAddress_res);
		TextView txtPasswordReg = (TextView) findViewById(R.id.password_res);
		CheckBox checkBoxTeacherReg = (CheckBox) findViewById(R.id.chekBox_teacher_reg_res);
		
		
		txtEmailReg.setText(bundle.getString("EmailReg"));	
		txtPasswordReg.setText(bundle.getString("PasswordReg"));	
		
		if (bundle.getBoolean("IsTeacherReg")==true){
			checkBoxTeacherReg.setChecked(true);
		}
		else checkBoxTeacherReg.setChecked(false);
		
	}
	

}
