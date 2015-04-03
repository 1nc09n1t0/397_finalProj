package edu.arizona.foundeats;

import android.app.Activity;
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
        Button button_MealPlan = (Button)findViewById(R.id.button_Achievements);
        Button button_Map = (Button)findViewById(R.id.button_Map);
        Button button_Challenge = (Button)findViewById(R.id.button_Challenge);
        
        //THE LISTENERS
        button_HowToPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
