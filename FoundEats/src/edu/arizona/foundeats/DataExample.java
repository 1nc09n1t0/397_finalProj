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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DataExample extends Activity {

    private HttpClient client;
    private EditText searchText;
    private Spinner quantitySpinner;
    private Button searchButton;
    private Button addButton;
    private Spinner unitSpinner;
    private List<String> namesOfFoods;
    private AlertDialog levelDialog;
    private JSONObject json;
    private JSONObject json2;
    private JSONArray nutrientArray;
    private int currInt;
    private TextView foodInfo;
    private FoodEntry currFood;
    private AsyncTask<String, Integer, String> myAsyncTask2;
    private ArrayList<String> unitArray;
    private double scale;
    double[] numArray;
    private String[] quantityArray;
    private int currPosMeas;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_example);
        searchButton = (Button)findViewById(R.id.bSearch);
        addButton = (Button)findViewById(R.id.bAdd);
        searchText = (EditText)findViewById(R.id.etSearch);
        quantitySpinner = (Spinner)findViewById(R.id.quantitySpinner);
        numArray = new double[50];
        for(int j = 0; j < numArray.length; j++)
        	numArray[j] = j+1.0;
        quantityArray = new String[50];
        foodInfo = (TextView)findViewById(R.id.textFoodInfo);
        unitSpinner = (Spinner)findViewById(R.id.unitSpinner);
        currPosMeas = 0;
        searchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String searchThis = searchText.getText().toString();
				addButton.setEnabled(false);
				scale = 1;
				if(searchThis != null)
					searchFood(searchThis);
				else
					Toast.makeText(getApplicationContext(), "Please enter a food", Toast.LENGTH_LONG).show();
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
                if(namesOfFoods == null){
            		Toast.makeText(getApplicationContext(), "No results for searched food", Toast.LENGTH_LONG).show();
            		return;
            	}
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
        				unitArray = new ArrayList<String>();
        				Log.d("", foodRequest.toString());
        				json2 = new JSONObject(foodRequest.toString());
        				nutrientArray = json2.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
        				for(int i = 0; i < nutrientArray.getJSONObject(1).getJSONArray("measures").length(); i++){
        					unitArray.add(i, nutrientArray.getJSONObject(1).getJSONArray("measures").getJSONObject(i).getString("label"));
//        					quantityArray[i] = "" + numArray[i]*nutrientArray.getJSONObject(1).getJSONArray("measures").getJSONObject(i).getDouble("qty");
        				}
        				setNutritionValues();
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
        		InputMethodManager imm = (InputMethodManager)getSystemService(
        			      Context.INPUT_METHOD_SERVICE);
        			imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
        		updateText();
        		updateSpinner();
        		try {
					setQuantitySpinner();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//        		setScale(quantitySpinner.getSelectedItemPosition()+1);
        		setSpinnerListener();
        		updateQuantitySpinner();
        		addButton.setEnabled(true);
        		addButton.setOnClickListener(new OnClickListener(){

        			@Override
        			public void onClick(View v) {
        				if(currFood == null)
        					Toast.makeText(getApplicationContext(), "Please enter a food", Toast.LENGTH_LONG).show();
        				else{
        					Intent intent = new Intent(getApplicationContext(), MakeMealActivity.class);
        	        		Nutrition.addFood(currFood);
        					setResult(1, intent);
        					finish();
        				}
        			}
                });
        	}
        };
    }

    private void updateSpinner(){
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitArray);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitSpinner.setAdapter(dataAdapter);
    }
    
    private void setSpinnerListener(){
    	unitSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					currPosMeas = position;
//					setScale(quantitySpinner.getSelectedItemPosition()+1);
					setQuantitySpinner();
//					updateQuantitySpinner();
					setNutritionValues();
					updateText();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
    		
    	});
    }
    
//    private void setScale(){
//		try {
//			scale = nutrientArray.getJSONObject(1).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("qty");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
    
    private void setQuantitySpinner() throws JSONException{
    	for(int i = 0; i < quantityArray.length; i++)
			quantityArray[i] = ((double)(i+1)*nutrientArray.getJSONObject(1).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("qty"))+"";
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quantityArray);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		quantitySpinner.setAdapter(dataAdapter);
    }
    
    private void updateQuantitySpinner(){
    	quantitySpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
//					currPosMeas = position;
					scale = position+1;
					setNutritionValues();
					updateText();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
    		
    	});
    }
    
    private void updateText(){
    	foodInfo.setText(currFood.name+"\n" + "NDBNO: " +  currFood.number + "\nCalories: " + currFood.calories + "\nProtein: " + currFood.protein
    			+ "\nFat: " + currFood.fat + "\nCarbs: " + currFood.carbs + "\nSodium: " + currFood.sodium + "\nCholesterol: " + currFood.cholesterol);
    }
    
    private void setNutritionValues() throws JSONException{
    	for(int i = 0; i < nutrientArray.length(); i++){
			if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 208){
				currFood.calories = (int) (nutrientArray.getJSONObject(i).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("value")*scale);
			}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 203){
				currFood.protein = (int) (nutrientArray.getJSONObject(i).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("value")*scale);
			}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 204){
				currFood.fat = (int) (nutrientArray.getJSONObject(i).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("value")*scale);
			}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 205){
				currFood.carbs = (int) (nutrientArray.getJSONObject(i).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("value")*scale);
			}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 307){
				currFood.sodium = (int) (nutrientArray.getJSONObject(i).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("value")*scale);
			}else if(nutrientArray.getJSONObject(i).getInt("nutrient_id") == 601){
				currFood.cholesterol = (int) (nutrientArray.getJSONObject(i).getJSONArray("measures").getJSONObject(currPosMeas).getDouble("value")*scale);
			}
		}
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