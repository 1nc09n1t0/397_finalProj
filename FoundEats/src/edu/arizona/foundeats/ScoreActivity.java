package edu.arizona.foundeats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ScoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);

		int score = 0;

		score += scoreCarbs();
		score += scoreCal();
		score += scoreCho();
		score += scoreFat();
		score += scorePro();
		score += scoreSod();

		TextView text = (TextView) findViewById(R.id.scoreText);

		text.setText("Your Score: " + score);
		
		DataHelper dh = new DataHelper(this);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		dh.addMeal("No Achievment", dateFormat.format(date).toString(), score, Nutrition.foods.toString());

	}

	private int scoreCarbs() {
		int allowed = Nutrition.getTotalCarbohydrates();
		int made = Nutrition.getCarbohydrates();
		if (allowed < made)
			return ((made - allowed)*-5);
		return ((allowed-made)*5);
	}
	
	private int scoreCal() {
		int allowed = Nutrition.getTotalCalories();
		int made = Nutrition.getCalories();
		if (allowed < made)
			return ((made - allowed)*-5);
		return ((allowed-made)*5);
	}
	
	private int scoreCho() {
		int allowed = Nutrition.getTotalCholesterol();
		int made = Nutrition.getCholesterol();
		if (allowed < made)
			return ((made - allowed)*-5);
		return ((allowed-made)*5);
	}
	
	private int scoreFat() {
		int allowed = Nutrition.getTotalFat();
		int made = Nutrition.getFat();
		if (allowed < made)
			return ((made - allowed)*-5);
		return ((allowed-made)*5);
	}
	
	private int scoreSod() {
		int allowed = Nutrition.getTotalSodium();
		int made = Nutrition.getSodium();
		if (allowed < made)
			return ((made - allowed)*-5);
		return ((allowed-made)*5);
	}
	
	private int scorePro() {
		int allowed = Nutrition.getTotalSodium();
		int made = Nutrition.getSodium();
		if (allowed < made)
			return (made*5);
		return (made*3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
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
}
