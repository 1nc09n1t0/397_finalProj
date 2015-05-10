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
	private static final String FOODTABLE = "foodTable";

	// Columns
	private static final String ACHIEVEMENT = "achievement";
	private static final String COUNT = "count";
	private static final String DATE = "date";
	private static final String SCORE = "score";
	private static final String MEAL = "meal";
	private static final String LOCATION = "location";
	private static final String REQUIREMENT = "requirement";
	
	private static final String FOOD = "food";
	private static final String CALORIES = "calories";
	private static final String CARBS = "carbs";
	private static final String FAT = "fat";
	private static final String PROTEIN = "protein";
	

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
		Cursor cursor = this.db.query(MEALTABLE, new String[] { ACHIEVEMENT, DATE, SCORE }, "meal = ?", new String[] { meal }, null, null, null);
		cursor.moveToFirst();
		list.add(cursor.getString(0));
		list.add(cursor.getString(1));
		list.add(cursor.getString(2));
		if (cursor != null && !cursor.isClosed())
			cursor.close();
		return list;
	}
	
	public List<String> getScore() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(MEALTABLE, new String[] { ACHIEVEMENT, DATE, SCORE, MEAL }, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					String line = "";
					line += "Date: " + cursor.getString(1);
					line += "\nScore:" + cursor.getString(2);
					line += "\nAchievements: " + cursor.getString(0);
					line += "\nMeal: " + cursor.getString(3);
					list.add(line);
				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
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
	 * Output: - None
	 */
	public void addMeal(String achievements, String date, int score, String meal) {
		ContentValues values = new ContentValues();
		values.put(ACHIEVEMENT, achievements);
		values.put(DATE, date);
		values.put(SCORE, score);
		values.put(MEAL, meal);
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

	/* 
	 * addFood
	 * Adds a certain food to the food database.
	 * Input: - String name of food
	 * - int amount of calories per serving
	 * - int amount of carbs per serving
	 * - int amount of fat per serving
	 * - int amount of protein per serving
	 * Output: - None
	 */
	public void addFood(String food, int calories, int carbs, int fat, int protein){
		ContentValues values = new ContentValues();
		values.put(FOOD, food);
		values.put(CALORIES, calories);
		values.put(CARBS, carbs);
		values.put(FAT, fat);
		values.put(PROTEIN, protein);
		db.insert(FOODTABLE, null, values);
	}

	/*
	 * getFoodInfo
	 * Gets information of food given the name of the food.
	 * Input: - String food name
	 * Output: - List of food data
	 * list[0] = Name of food
	 * list[1] = Calories
	 * list[2] = Carbs
	 * list[3] = Fat
	 * list[4] = Protein
	 */
	public List<String> getFoodInfo(String food){
		List<String> list = new ArrayList<String>();
		list.add(food);
		Cursor cursor = this.db.query(FOODTABLE, new String[] { CALORIES, CARBS, FAT, PROTEIN }, "food = ?", new String[] { food }, null, null, null);
		cursor.moveToFirst();
		list.add(cursor.getString(0));
		list.add(cursor.getString(1));
		list.add(cursor.getString(2));
		list.add(cursor.getString(3));
		if (cursor != null && !cursor.isClosed())
			cursor.close();
		return list;
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
			db.execSQL("CREATE TABLE " + MEALTABLE + "(achievement text not null, date text not null, score integer not null, meal text not null)");
			db.execSQL("CREATE TABLE " + FOODTABLE + "(food text not null, calories integer not null, carbs integer not null, fat integer not null, protein integer not null)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}