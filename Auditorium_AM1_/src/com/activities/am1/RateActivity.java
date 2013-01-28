package com.activities.am1;

import com.activities.am1.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class RateActivity extends Activity {
	Button imageButtonPossitive;
	Button imageButtonNegative;
	TextView txtview_Rates;
	int rates=0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_row);
		
		addListenerOnButtons();

	}

	public void addListenerOnButtons() {

		txtview_Rates = (TextView) findViewById(R.id.txtview_rates);
		txtview_Rates.setText(String.valueOf(rates));
		
		imageButtonPossitive = (Button) findViewById(R.id.rate_possitive);

		imageButtonPossitive.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				Toast.makeText(RateActivity.this,
						"VOTED POSSITIVE",
						Toast.LENGTH_SHORT).show();
				rates++;
				txtview_Rates.setText(String.valueOf(rates));

			}

		});
		
		imageButtonNegative = (Button) findViewById(R.id.rate_negative);

		imageButtonNegative.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				Toast.makeText(RateActivity.this,
						"VOTED NEGATIVE",
						Toast.LENGTH_SHORT).show();
				rates--;
				txtview_Rates.setText(String.valueOf(rates));

			}

		});

	}
	

}