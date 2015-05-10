package edu.arizona.foundeats;

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
		
		score += (Nutrition.getTotalCarbohydrates() - Nutrition.getCarbohydrates()) * 10;
		score += (Nutrition.getTotalCalories() - Nutrition.getCalories()) * 10;
		score += (Nutrition.getTotalCholesterol() - Nutrition.getTotalCarbohydrates()) * 10;
		score += (Nutrition.getTotalFat() - Nutrition.getFat()) * 10;
		score += Nutrition.getTotalProtein() * 10;
		score += (Nutrition.getTotalSodium() - Nutrition.getSodium()) * 10;
		
//		TextView text = (TextView) findViewById(R.id.scoreText);
		

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
