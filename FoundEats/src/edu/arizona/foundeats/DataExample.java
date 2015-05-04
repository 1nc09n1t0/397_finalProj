package edu.arizona.foundeats;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataExample extends Activity {

    private HttpClient client;
    private EditText searchText;
    private Button searchButton;
    private Button addButton;
    private List<String> namesOfFoods;
    private AlertDialog levelDialog;
    private JSONObject json;
    private JSONObject json2;
    private int currInt;
    private TextView foodInfo;
    private FoodEntry currFood;
    private AsyncTask<String, Integer, String> myAsyncTask2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_example);
        searchButton = (Button)findViewById(R.id.bSearch);
        addButton = (Button)findViewById(R.id.bAdd);
        searchText = (EditText)findViewById(R.id.etSearch);
        foodInfo = (TextView)findViewById(R.id.textFoodInfo);
        searchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String searchThis = searchText.getText().toString();
				if(searchThis != null)
					searchFood(searchThis);
			}
        });
        
        addButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(currFood == null)
					Toast.makeText(getApplicationContext(), "Please enter a food", Toast.LENGTH_LONG).show();
				else{
					Intent intent = new Intent(getApplicationContext(), MakeMealActivity.class);
					Nutrition.addCalories(currFood.calories);
	        		Nutrition.addCarbohydrates(currFood.carbs);
	        		Nutrition.addCholesterol(currFood.cholesterol);
	        		Nutrition.addFat(currFood.fat);
	        		Nutrition.addProtein(currFood.protein);
	        		Nutrition.foodNames.add(currFood.name);
					setResult(1, intent);
					finish();
				}
			}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void searchFood(final String q) {

        AsyncTask<String, Integer, String> myAsyncTask = new AsyncTask<String, Integer, String>()
        {

            @Override
            protected String doInBackground(String... params) {
            	String searchKeyword;
            	
                try{
                	searchKeyword = q.replaceAll("\\s+", "+");
                	
                	String requestString = "http://api.nal.usda.gov/usda/ndb/search/?format=json&q=" + searchKeyword + "&max=50&offset=0&api_key=KppbRe3Yt8JvTn9PNQLWQOf7K2aiYsVgiEyv2gRc";
                	java.net.URL url = new java.net.URL(requestString);
                	
                    StringBuilder foodRequest = new StringBuilder();
                    HttpGet get = new HttpGet(url.toString());
                    client = new DefaultHttpClient();
                    HttpResponse r = client.execute(get);
                    Log.d("EXECUTED:", "Executed post successfully!");
                    int status = r.getStatusLine().getStatusCode();
                    Log.d("STATUS:", status+"");

                    if(status >= 200 && status < 300){
                        BufferedReader br=new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
                        String t;
                        while((t = br.readLine())!=null){
                            foodRequest.append(t);
                        }
                        Log.d("", foodRequest.toString());
                        json = new JSONObject(foodRequest.toString());
                        namesOfFoods = new ArrayList<String>();
                        for(int i = 0; i < json.getJSONObject("list").getJSONArray("item").length(); i++){
                        	namesOfFoods.add(i, json.getJSONObject("list").getJSONArray("item").getJSONObject(i).getString("name"));
                        }
                    }
                }catch (JSONException e) {
                    Log.e("ERROR:", "This is not a valid JSON request");
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                pickFood();
            }
        };
        myAsyncTask.execute();
        
        myAsyncTask2 = new AsyncTask<String, Integer, String>(){

        	@Override
        	protected String doInBackground(String... params) {

        		try{

        			String requestString = "http://api.nal.usda.gov/usda/ndb/reports/?ndbno=" + currFood.number +"&api_key=KppbRe3Yt8JvTn9PNQLWQOf7K2aiYsVgiEyv2gRc";
        			java.net.URL url = new java.net.URL(requestString);

        			StringBuilder foodRequest = new StringBuilder();
        			HttpGet get = new HttpGet(url.toString());
        			client = new DefaultHttpClient();
        			HttpResponse r = client.execute(get);
        			Log.d("EXECUTED:", "Executed post successfully!");
        			int status = r.getStatusLine().getStatusCode();
        			Log.d("STATUS:", status+"");

        			if(status >= 200 && status < 300){
        				BufferedReader br=new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
        				String t;
        				while((t = br.readLine())!=null){
        					foodRequest.append(t);
        				}
        				Log.d("", foodRequest.toString());
        				json2 = new JSONObject(foodRequest.toString());
        				JSONArray nutrientArray = json2.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
        				for(int i = 0; i < nutrientArray.length(); i++){
//        					Log.d("", nutrientArray.getJSONObject(i).getInt("nutrient_id") + "");
        					if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 208){
        						currFood.calories = nutrientArray.getJSONObject(i).getInt("value");
        					}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 203){
        						currFood.protein = nutrientArray.getJSONObject(i).getInt("value");
        					}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 204){
        						currFood.fat = nutrientArray.getJSONObject(i).getInt("value");
        					}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 205){
        						currFood.carbs = nutrientArray.getJSONObject(i).getInt("value");
        					}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 307){
        						currFood.sodium = nutrientArray.getJSONObject(i).getInt("value");
        					}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 601){
        						currFood.cholesterol = nutrientArray.getJSONObject(i).getInt("value");
        					}
        				}
        			}
        		}catch (JSONException e) {
        			Log.e("ERROR:", "This is not a valid JSON request");
        		}catch (Exception e) {
        			e.printStackTrace();
        		}
        		return null;
        	}

        	@Override
        	protected void onPostExecute(String result) {
        		super.onPostExecute(result);
        		foodInfo.setText(currFood.name+"\n" + "NDBNO: " +  currFood.number + "\nCalories: " + currFood.calories + "\nProtein: " + currFood.protein
        			+ "\nFat: " + currFood.fat + "\nCarbs: " + currFood.carbs + "\nSodium: " + currFood.sodium + "\nCholesterol: " + currFood.cholesterol);
        	}
        };
    }

    public void pickFood(){
    	String[] foodArray = new String[namesOfFoods.size()];
    	foodArray = namesOfFoods.toArray(foodArray);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select your food");
        builder.setSingleChoiceItems(foodArray, -1, null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				levelDialog.dismiss();
				currInt = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
				Log.d("Curr int:", currInt+"");
				JSONObject currObj;
				try {
					currObj = json.getJSONObject("list").getJSONArray("item").getJSONObject(currInt);
					currFood = new FoodEntry(currObj.getString("name"), currObj.getString("ndbno"));
					foodInfo.setText(currFood.name+"\n" + "NDBNO: " +  currFood.number);
					myAsyncTask2.execute();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public static class FoodEntry {
        public final String name;
        public final String number;
        public int calories;
        public int fat;
        public int carbs;
        public int protein;
        public int sodium;
        public int cholesterol;

        private FoodEntry(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }
    
}