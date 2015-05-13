package edu.arizona.foundeats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	
	private int score = 0;
	private ArrayList<String> ach;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		Button button_OK = (Button)findViewById(R.id.button_OK);
		
		button_OK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}	
		});
		
		ach = new ArrayList<String>();

		score += scoreCarbs();
		score += scoreCal();
		score += scoreCho();
		score += scoreFat();
		score += scorePro();
		score += scoreSod();
		
		achieveCal();
		achieveCho();
		achieveFat();
		achievePro();
		achieveSod();
		achieveMisc();
		
		String achievements = "";
		
		for (String temp: ach){
			achievements += "\n" + temp;
		}

		TextView text = (TextView) findViewById(R.id.scoreText);

		text.setText("Your Score: " + score + "\n" + achievements);

		DataHelper dh = new DataHelper(this);

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();

		String food = "";
		for (String item : Nutrition.getNames()) {
			food += " - " + item + "\n";
		}

		dh.addMeal(achievements, dateFormat.format(date).toString(), score, food);

	}

	private void achieveMisc() {
		if(score < 0){
			score += 100;
			ach.add("-Pitty Achievement (100)");
			return;
		}
		else if(score > 7000){
			score += 400;
			ach.add("-Health God (400)");
			return;
		}
		else if(score > 6000){
			score += 300;
			ach.add("-Royal Eater (300)");
			return;
		}
		else if(score > 5000){
			score += 200;
			ach.add("-Health Nut (200)");
			return;
		}
		else if(score > 4000){
			score += 100;
			ach.add("-I Kinda Watch What I Eat (100)");
			return;
		}
	}

	private void achieveSod() {
		if (Nutrition.getSodium() == 0){
			score += 300;
			ach.add("-Sodium Warrior (300)");
			return;
		}
		else if (Nutrition.getSodium() < (Nutrition.getTotalSodium() / 2)){
			score += 200;
			ach.add("-Sodium Fighter (200)");
			return;
		}
		else if (Nutrition.getSodium() == Nutrition.getTotalSodium()){
			score += 100;
			ach.add("-Sodium Watcher (100)");
			return;
		}	
	}

	private void achievePro() {
		if (Nutrition.getProtein() > Nutrition.getTotalProtein()){
			score += 300;
			ach.add("-Muscle Builder (300)");
			return;
		}
		else if (Nutrition.getProtein() == Nutrition.getTotalProtein()){
			score += 100;
			ach.add("-The Power of Protein (200)");
			return;
		}
		else if (Nutrition.getProtein() > (Nutrition.getTotalProtein() / 2)){
			score += 200;
			ach.add("-Good Protein Start (100)");
			return;
		}
	}

	private void achieveFat() {
		if (Nutrition.getFat() == 0){
			score += 300;
			ach.add("-Fat Warrior (300)");
			return;
		}
		else if (Nutrition.getFat() < (Nutrition.getTotalFat() / 2)){
			score += 200;
			ach.add("-Fat Fighter (200)");
			return;
		}
		else if (Nutrition.getFat() == Nutrition.getTotalFat()){
			score += 100;
			ach.add("-Fat Watcher (100)");
			return;
		}	
	}

	private void achieveCho() {
		if (Nutrition.getCholesterol() == 0){
			score += 300;
			ach.add("-Cholesterol Warrior (300)");
			return;
		}
		else if (Nutrition.getCholesterol() < (Nutrition.getTotalCholesterol() / 2)){
			score += 200;
			ach.add("-Cholesterol Fighter (200)");
			return;
		}
		else if (Nutrition.getCholesterol() == Nutrition.getTotalCholesterol()){
			score += 100;
			ach.add("-Cholesterol Watcher (100)");
			return;
		}	
	}

	private void achieveCal() {
		if (Nutrition.getCalories() == 0){
			score += 300;
			ach.add("-Calories Warrior (300)");
			return;
		}
		else if (Nutrition.getCalories() < (Nutrition.getTotalCalories() / 2)){
			score += 200;
			ach.add("-Calories Fighter (200)");
			return;
		}
		else if (Nutrition.getCalories() == Nutrition.getTotalCalories()){
			score += 100;
			ach.add("-Calories Watcher (100)");
			return;
		}
	}

	private int scoreCarbs() {
		int allowed = Nutrition.getTotalCarbohydrates();
		int made = Nutrition.getCarbohydrates();
		if (allowed < made)
			return ((made - allowed) * -5);
		return ((allowed - made) * 5);
	}

	private int scoreCal() {
		int allowed = Nutrition.getTotalCalories();
		int made = Nutrition.getCalories();
		if (allowed < made)
			return ((made - allowed) * -5);
		return ((allowed - made) * 5);
	}

	private int scoreCho() {
		int allowed = Nutrition.getTotalCholesterol();
		int made = Nutrition.getCholesterol();
		if (allowed < made)
			return ((made - allowed) * -5);
		return ((allowed - made) * 5);
	}

	private int scoreFat() {
		int allowed = Nutrition.getTotalFat();
		int made = Nutrition.getFat();
		if (allowed < made)
			return ((made - allowed) * -5);
		return ((allowed - made) * 5);
	}

	private int scoreSod() {
		int allowed = Nutrition.getTotalSodium();
		int made = Nutrition.getSodium();
		if (allowed < made)
			return ((made - allowed) * -5);
		return ((allowed - made) * 5);
	}

	private int scorePro() {
		int allowed = Nutrition.getTotalSodium();
		int made = Nutrition.getSodium();
		if (allowed < made)
			return (made * 5);
		return (made * 3);
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
