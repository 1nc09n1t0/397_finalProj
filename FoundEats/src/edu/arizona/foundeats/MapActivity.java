package edu.arizona.foundeats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends Activity {
	static final LatLng TUCSON = new LatLng(32.221743, -110.926479);
	private GoogleMap map;
	private DataHelper dh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		dh = new DataHelper(this); 
		
		final Geocoder coder = new Geocoder(getApplicationContext());
		 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
		 if (map!=null){
		    	try {
		    		
		    		//Populate address list
					//List<Address> addressList = coder.getFromLocationName("4411 E. Speedway, Tucson, AZ", 1);
		    		List<Address> addressList = new ArrayList<Address>();
		    		
		    		List<String> dhAddresses = dh.getLocations();
					for (String address : dhAddresses){
						addressList.add(coder.getFromLocationName(address, 1).get(0));
					}
					
					//Create iterator
					Iterator<Address> locations = addressList.iterator();
					double lat = 0f;
					double lon = 0f;
					
					// SHOW LOCATIONS
					while (locations.hasNext()) {
						Address loc = locations.next();
						lat = loc.getLatitude();
						lon = loc.getLongitude();
					}	
					
//					map.addMarker(new MarkerOptions()
//						.position(drMurphyGPS)
//						.title("HOME OFFICE")
//				        .snippet("4411 E. Speedway, Tucson, AZ")
//				        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
					
					
					
					
					
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), "Failed to populate List<Address>", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
		    }
		 
		//including some animation
	      //Move the camera instantly to Tucson with a zoom of 15.
	      map.moveCamera(CameraUpdateFactory.newLatLngZoom(TUCSON, 15));

	      // Zoom in, animating the camera.
	      map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	    
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
