package edu.arizona.foundeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class MakeMealActivity extends Activity {
	private ProgressBar caloriesBar;
	private ProgressBar fatBar;
	private ProgressBar cholBar;
	private ProgressBar sodiumBar;
	private ProgressBar carbBar;
	private ProgressBar proteinBar;
	private Spinner foodSpinner;
	private Button removeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_meal);

		caloriesBar = (ProgressBar) findViewById(R.id.progressCalories);
		fatBar = (ProgressBar) findViewById(R.id.progressFat);
		cholBar = (ProgressBar) findViewById(R.id.progressCholesterol);
		sodiumBar = (ProgressBar) findViewById(R.id.progressSodium);
		carbBar = (ProgressBar) findViewById(R.id.progressCarbs);
		proteinBar = (ProgressBar) findViewById(R.id.progressProtein);
		foodSpinner = (Spinner) findViewById(R.id.spinner1);
		removeButton = (Button) findViewById(R.id.button2);

		caloriesBar.setMax(Nutrition.getTotalCalories());
		fatBar.setMax(Nutrition.getTotalFat());
		cholBar.setMax(Nutrition.getTotalCholesterol());
		sodiumBar.setMax(Nutrition.getTotalSodium());
		carbBar.setMax(Nutrition.getTotalCarbohydrates());
		proteinBar.setMax(Nutrition.getTotalProtein());
		
		removeButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Nutrition.deleteFood(String.valueOf(foodSpinner.getSelectedItem()));
				updateValues();
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_meal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	static final int FOOD_REQUEST = 1;

	public void addFood(View view) {
		Intent intent = new Intent(getApplicationContext(), DataExample.class);
		startActivityForResult(intent, FOOD_REQUEST);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FOOD_REQUEST){
			updateValues();
		}
	}
	
	private void updateValues(){
		caloriesBar.setProgress(Nutrition.getCalories());
		fatBar.setProgress(Nutrition.getFat());
		cholBar.setProgress(Nutrition.getCholesterol());
		sodiumBar.setProgress(Nutrition.getSodium());
		carbBar.setProgress(Nutrition.getCarbohydrates());
		proteinBar.setProgress(Nutrition.getProtein());
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Nutrition.getNames());
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		foodSpinner.setAdapter(dataAdapter);
	}
}
