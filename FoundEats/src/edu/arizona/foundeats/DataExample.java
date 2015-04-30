package edu.arizona.foundeats;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DataExample extends Activity {

    HttpClient client;
    EditText searchText;
    Button searchButton;
    List<String> namesOfFoods;
    List<String> nutrients;
    AlertDialog levelDialog;
    JSONObject json;
    JSONObject json2;
    int currInt;
    TextView foodInfo;
    FoodEntry currFood;
    AsyncTask<String, Integer, String> myAsyncTask2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_example);
        Button submit = (Button)findViewById(R.id.bSearch);
        searchText = (EditText)findViewById(R.id.etSearch);
        foodInfo = (TextView)findViewById(R.id.textFoodInfo);
        submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String searchThis = searchText.getText().toString();
				if(searchThis != null)
					searchFood(searchThis);
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
//                        	Log.d("item " + i + ": ", namesOfFoods.get(i));
                        }
//                        Log.d("RESULT:", x);
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
//        pickFood();
        
        myAsyncTask2 = new AsyncTask<String, Integer, String>(){

        	@Override
        	protected String doInBackground(String... params) {

        		try{

        			String requestString = "http://api.nal.usda.gov/usda/ndb/reports/?ndbno=" + currFood.number +"&api_key=KppbRe3Yt8JvTn9PNQLWQOf7K2aiYsVgiEyv2gRc";
//        			String requestString = "http://api.nal.usda.gov/usda/ndb/reports/?ndbno=11987&api_key=KppbRe3Yt8JvTn9PNQLWQOf7K2aiYsVgiEyv2gRc";
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
        				// 1-calories, 3-protein, 4-fat, 6-carbs, 20-sodium, 106-cholesterol
        				currFood.calories = json2.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients").getJSONObject(1).getInt("value");
        				Log.d("Calories:", currFood.calories+"");
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
//        		foodInfo.setText(currFood.name+"\n" + "NDBNO: " +  currFood.number);
        	}
        };
    }

    public void pickFood(){
    	String[] foodArray = new String[namesOfFoods.size()];
    	foodArray = namesOfFoods.toArray(foodArray);
//    	for(int i = 0; i < foodArray.length; i++){
//    		Log.d("Food " + i + ": ", foodArray[i]);
//    	}
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
					currFood = new FoodEntry(currObj.getString("name"), Integer.parseInt(currObj.getString("ndbno")));
					foodInfo.setText(currFood.name+"\n" + "NDBNO: " +  currFood.number);
					myAsyncTask2.execute();
//					http://api.nal.usda.gov/usda/ndb/reports/?ndbno=11987&type=b&format=fjson&api_key=DEMO_KEY
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public static class FoodEntry {
        public final String name;
        public final int number;
        public int calories;
        public int fat;
        public int carbs;
        public int protein;
        public int sodium;
        public int cholesterol;

        private FoodEntry(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }
    
}