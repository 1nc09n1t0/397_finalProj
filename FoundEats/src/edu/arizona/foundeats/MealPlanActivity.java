package edu.arizona.foundeats;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MealPlanActivity extends ListActivity {
	DataHelper dh;
	private List<String> names;
	private ArrayAdapter<String> adapter;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_plan);
		
		dh = new DataHelper(this);

		names = dh.getScore();
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, dh.getScore());
		setListAdapter(adapter);
		lv = getListView();
		lv.setTextFilterEnabled(true);
		if (names.size() == 0)
			Toast.makeText(getApplicationContext(), "No scores to display", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meal_plan, menu);
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
