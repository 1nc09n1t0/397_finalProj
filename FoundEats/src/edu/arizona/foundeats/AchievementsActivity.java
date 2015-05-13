package edu.arizona.foundeats;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AchievementsActivity extends Activity {
public class AchievementsActivity extends ListActivity {
	private List<String> names;
	private ArrayAdapter<String> adapter;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievements);
		
		names = new ArrayList<String>();
		names.add("Calorie Warrior\n  -Make a meal with zero calories.");
		names.add("Calorie Fighter\n  -Make a meal with less then half the given calories.");
		names.add("Calorie Watcher\n  -Make a meal with the exact number of calories.");
		
		names.add("Fat Warrior\n  -Make a meal with zero fat.");
		names.add("Fat Fighter\n  -Make a meal with less then half the given fat.");
		names.add("Fat Watcher\n  -Make a meal with the exact number of fat.");
		
		names.add("Cholesterol Warrior\n  -Make a meal with zero cholesterol.");
		names.add("Cholesterol Fighter\n  -Make a meal with less then half the given cholesterol.");
		names.add("Cholesterol Watcher\n  -Make a meal with the exact number of cholesterol.");
		
		names.add("Sodium Warrior\n  -Make a meal with zero sodium.");
		names.add("Sodium Fighter\n  -Make a meal with less then half the given sodium.");
		names.add("Sodium Watcher\n  -Make a meal with the exact number of sodium.");
		
		names.add("Carb Warrior\n  -Make a meal with zero carbohydrates.");
		names.add("Carb Fighter\n  -Make a meal with less then half the given carbohydrates.");
		names.add("Carb Watcher\n  -Make a meal with the exact number of carbohydrates.");
		
		names.add("Good Protein Start\n  -Make a meal with at least half the given protein.");
		names.add("The Power of Protein\n  -Make a meal with the exact number of protein.");
		names.add("Muscle Builder\n  -Make a meal with more then the max number of protein.");
		
		names.add("I Kinda Watch What I Eat\n  -Earn a score of over 4000.");
		names.add("Health Nut\n  -Earn a score of over 5000.");
		names.add("Royal Eater\n  -Earn a score of over 6000.");
		names.add("Health God\n  -Earn a score of over 7000.");
		names.add("Pitty Achievement\n  -Earn a negative score.");

		
//		names.add("\n  -.");



		

		adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);
		setListAdapter(adapter);
		lv = getListView();
		lv.setTextFilterEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.achievements, menu);
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
