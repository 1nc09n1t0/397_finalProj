package edu.arizona.foundeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DataReceiverExample extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.data_receiver);
		Intent intent = getIntent();
		String message = intent.getStringExtra("keyexample");
		
		TextView textView = new TextView(this);
		textView.setTextSize(24);
		textView.setText(message);
		
		setContentView(textView);
	}

}
