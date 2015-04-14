package edu.arizona.foundeats;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper {
	private static final String DATABASE_NAME = "foundEats.db";
	private static final int DATABASE_VERSION = 1;
	private Context context;
	private SQLiteDatabase db;
	static DataHelper dataHelper;

	// Tables
	private static final String ACHIEVEMENTSTABLE = "achievementsTable";
	private static final String MEALTABLE = "mealTable";

	// Columns
	private static final String ACHIEVEMENT = "achievement";
	private static final String COUNT = "count";
	private static final String DATE = "date";
	private static final String SCORE = "score";
	private static final String MEAL = "meal";
	private static final String LOCATION = "location";
	private static final String COST = "cost";
	private static final String REQUIREMENT = "requirement";

	/*
	 * getCompletedAchievements
	 * Get a list of all achievements that have been completed.
	 * Input - None
	 * Output - A list with all completed achievements
	 */
	public List<String> getCompletedAchievements() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(ACHIEVEMENTSTABLE, new String[] { ACHIEVEMENT, REQUIREMENT }, "count > 0", null, null, null, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					list.add(cursor.getString(0));
				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return list;
	}
	
	/*
	 * getIncompleteAchievements
	 * Get a list of all achievements that have not been completed.
	 * Input - None
	 * Output - A list with all incomplete achievements
	 */
	public List<String> getIncompleteAchievements() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(ACHIEVEMENTSTABLE, new String[] { ACHIEVEMENT, REQUIREMENT }, "count = 0", null, null, null, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					list.add(cursor.getString(0));
				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return list;
	}

	/*
	 * getLocations
	 * Get a list of the locations achievements were achieved.
	 * Input - None
	 * Output - An even sized index containing achievements and locations
	 * list[n] = Achievement achieved
	 * list[n+1] = Location achieved
	 */
	public List<String> getLocations() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(MEALTABLE, new String[] { ACHIEVEMENT, LOCATION }, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					list.add(cursor.getString(0));
					list.add(cursor.getString(1));
				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return list;
	}

	/*
	 * getScore
	 * Gets the all the statistics from a completed achievement.
	 * Input - String of meal you want to look at the score of
	 * Output - An arrayList list
	 * list[0] = The food that was in the meal
	 * list[1] = The achievements obtained for the meal
	 * list[2] = The date the meal was made
	 * list[3] = The score of the meal
	 * list[4] = Where the meal was made
	 * list[5] = The approximate cost of the meal
	 */
	public List<String> getScore(String meal) {
		List<String> list = new ArrayList<String>();
		list.add(meal);
		Cursor cursor = this.db.query(MEALTABLE, new String[] { ACHIEVEMENT, DATE, SCORE, LOCATION, COST }, "meal = ?", new String[] { meal }, null, null, null);
		cursor.moveToFirst();
		list.add(cursor.getString(0));
		list.add(cursor.getString(1));
		list.add(cursor.getString(2));
		list.add(cursor.getString(3));
		list.add(cursor.getString(4));
		if (cursor != null && !cursor.isClosed())
			cursor.close();
		return list;
	}

	/*
	 * addMeal
	 * Gets all the statistics from the completed meal and adds them to
	 * database.
	 * Input: - String of all achievements made on the meal
	 * - String date of day meal was made
	 * - int score of meal
	 * - String meal contains all items used to create meal
	 * - String location is address of where meal was made
	 * - int Cost is approximate cost of the meal
	 * Output: - None
	 */
	public void addMeal(String achievements, String date, int score, String meal, String location, int cost) {
		ContentValues values = new ContentValues();
		values.put(ACHIEVEMENT, achievements);
		values.put(DATE, date);
		values.put(SCORE, score);
		values.put(MEAL, meal);
		values.put(LOCATION, location);
		values.put(COST, cost);
		db.insert(MEALTABLE, null, values);
	}

	/*
	 * updateAchievement
	 * Increments the number of times the given achievement has been achieved in
	 * the achievements table.
	 * Input: - String achievement is name of the achievement being incremented
	 * Output: - None
	 */
	public void updateAchievement(String achievement) {
		ContentValues values = new ContentValues();
		int count = 0;
		Cursor cursor = this.db.query(ACHIEVEMENTSTABLE, new String[] { achievement }, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				count = cursor.getInt(0);
			}
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
		count++;
		values.put(COUNT, count);
		db.update(ACHIEVEMENTSTABLE, values, "achievement=?", new String[] { achievement });
	}

	// Basic Functions
	public DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
	}

	public static DataHelper getInstance(Context context) {
		if (dataHelper == null) {
			dataHelper = new DataHelper(context);
		}
		dataHelper.openDatabase();
		return dataHelper;
	}

	private void openDatabase() {
		OpenHelper openHelper = new OpenHelper(this.context);
		db = openHelper.getWritableDatabase();
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + ACHIEVEMENTSTABLE + "(achievement text not null, count integer not null, requirement text not null)");
			db.execSQL("CREATE TABLE " + MEALTABLE + "(achievement text not null, date text not null, score integer not null, meal text not null, location text not null, cost integer not null)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}