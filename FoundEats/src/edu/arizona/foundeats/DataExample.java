package edu.arizona.foundeats;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class DataExample extends Activity {
	private String food = "";
	
	public final static String bigString = "POST&http%3A%2F%2Fplatform.fatsecret.com%2Frest%2Fserver.api&"
			+ "food_id%3Dbanana%26method%3Dfood.get%26"
			+ "oauth_consumer_key%3D42b26199c061485290df43070e1df2f8%26"
			+ "oauth_nonce%3Dabc%26"
			+ "oauth_signature%3D84fed8096c294b86b6a581e549ab4538%26"
			+ "oauth_signature_method%3DHMAC-SHA1%26"
			+ "oauth_timestamp%3D12345678%26"
			+ "oauth_version%3D1.0";
	
	public final static String bigString2 = "GET & http://platform.fatsecret.com/rest/server.api&?"
			+ "food_id=banana&method=food.get&"
			+ "oauth_consumer_key=42b26199c061485290df43070e1df2f8&"
			+ "oauth_nonce=1234&"
			+ "oauth_signature=84fed8096c294b86b6a581e549ab4538%3D&"
			+ "oauth_signature_method=HMAC-SHA1&"
			+ "oauth_timestamp=1245126631&"
			+ "oauth_version=1.0";
	
	public final static String bigString3 = "POST & http%3A%2F%2Fplatform.fatsecret.com%2Frest%2Fserver.api & "
			+ "a%3Dbar%26%26"
			+ "oauth_consumer_key%3D42b26199c061485290df43070e1df2f8%26"
			+ "oauth_nonce%3Dabc%26"
			+ "oauth_signature_method%3DHMAC-SHA1%26"
			+ "oauth_timestamp%3D12345678%26"
			+ "oauth_version%3D1.0%26"
			+ "z%3Dbar";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_example);
	}
	
	public void verifyFood(View view){
//		EditText foodEditText = (EditText)findViewById(R.id.fooditem);
//		food = foodEditText.getText().toString();
		
		new CallAPI().execute(bigString3);
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
	
	private class CallAPI extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String urlString = params[0]; // URL to call
			String resultToDisplay = "";
			InputStream in = null;
			
			//HTTP Get
			try{
				URL url = new URL(urlString);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				in = new BufferedInputStream(urlConnection.getInputStream());
			}catch(Exception e){
				System.out.println(e.getMessage());
				return e.getMessage();
			}
			
			verificationResult result = null;
			XmlPullParserFactory pullParserFactory;
			 
		    try {
		      pullParserFactory = XmlPullParserFactory.newInstance();
		      XmlPullParser parser = pullParserFactory.newPullParser();
		 
		      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		      parser.setInput(in, null);
		      result = parseXML(parser);
		    } catch (XmlPullParserException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
			
			return resultToDisplay;
		}
		
		protected void onPostExecute(String result){
			Intent intent = new Intent(getApplicationContext(), DataReceiverExample.class);
			intent.putExtra("keyexample", result);
			startActivity(intent);
		}
	}
	
	private verificationResult parseXML(XmlPullParser parser) throws XmlPullParserException, IOException{
		int eventType = parser.getEventType();
		verificationResult result = new verificationResult();
	
		while(eventType != XmlPullParser.END_DOCUMENT){
			String name = null;
			
			switch(eventType){
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if(name.equals("Error"))
					System.out.println("API Error");
				else if(name.equals("food_name")){
					result.string1 = parser.nextText();
				}else if (name.equals("calories")){
					result.string2 = parser.nextText();
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			return result;
		}
		return null;
		
	}
	
	private class verificationResult{
		public String string1;
		public String string2;
		
	}
}