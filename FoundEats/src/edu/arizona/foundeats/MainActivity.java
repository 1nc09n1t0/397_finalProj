package edu.arizona.foundeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     // Add Button Widgets
        Button button_HowToPlay = (Button)findViewById(R.id.button_HowToPlay);        
        Button button_TypeOfMeal = (Button)findViewById(R.id.button_TypeOfMeal);
        Button button_MealPlan = (Button)findViewById(R.id.button_MealPlan);
        Button button_Map = (Button)findViewById(R.id.button_Map);
        Button button_Challenge = (Button)findViewById(R.id.button_Challenge);
        Button button_Achievements = (Button)findViewById(R.id.button_Achievements);
        
        //THE BUTTON LISTENERS
        button_HowToPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),HowToPlayActivity.class);
				startActivity(intent);
			}
		});
        button_TypeOfMeal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),TypeOfMealActivity.class);
				startActivity(intent);
			}
		});
        button_MealPlan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),MealPlanActivity.class);
				startActivity(intent);
			}
		});
        button_Map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),MapActivity.class);
				startActivity(intent);
			}
		});
        button_Challenge.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),ChallengeActivity.class);
				startActivity(intent);
			}
		});
        button_Achievements.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),AchievementsActivity.class);
				startActivity(intent);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
