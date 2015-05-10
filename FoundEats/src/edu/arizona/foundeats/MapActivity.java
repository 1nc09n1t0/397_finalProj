package edu.arizona.foundeats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
	static final LatLng TUCSON = new LatLng(32.221743, -110.926479);
	private GoogleMap map;
	private GoogleApiClient mGoogleApiClient;
	private DataHelper dh;
	private Marker currLoc;
	
	private GoogleMap.OnMyLocationChangeListener currLocChangeListener = new GoogleMap.OnMyLocationChangeListener() {
		
		@Override
		public void onMyLocationChange(Location location) {
			
	        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
	        //currLoc = map.addMarker(new MarkerOptions().position(loc));
	     
	        //currLoc.setPosition(loc);
	        
	        if(map != null){
	            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
	        }
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		dh = new DataHelper(this);
		
		
	/*	
		 mGoogleApiClient = new GoogleApiClient
		            .Builder(this)
		            .addApi(Places.GEO_DATA_API)
		            .addApi(Places.PLACE_DETECTION_API)
		            .addConnectionCallbacks(this)
		            .addOnConnectionFailedListener(this)
		            .build();
		*/

		
		
		final Geocoder coder = new Geocoder(getApplicationContext());

//		
	
		 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
		 
		 map.setMyLocationEnabled(true);
		 map.setOnMyLocationChangeListener(currLocChangeListener);
		 
		 Location myLocation = map.getMyLocation();
		 
		 //Location currentLocation = locationManager.getLastKnownLocation("gps"); 

		 if (myLocation != null)
		 {
			double currLat = myLocation.getLatitude();
			double currLon = myLocation.getLongitude();
			LatLng currGPS = new LatLng(currLat, currLon);
		 
			//currLoc = map.addMarker(new MarkerOptions().position(currGPS));
		 }


		 
		 if (map!=null){
		    	try {
		    		
		    		//Populate address list
					//List<Address> addressList = coder.getFromLocationName("4411 E. Speedway, Tucson, AZ", 1);
		    		List<Address> addressList = new ArrayList<Address>();
		    		
		    		/*
		    		List<String> dhAddresses = dh.getLocations();
					for (String address : dhAddresses){
						addressList.add(coder.getFromLocationName(address, 1).get(0));
					}
					*/
		    		
//		    		addressList.add(coder.getFromLocationName("Safeway near  University of Ar", 1).get(0));
//		    		addressList.add(coder.getFromLocationName("Roma Imports", 1).get(0));
//		    		addressList.add(coder.getFromLocationName("Tucson Food Service", 1).get(0));
//		    		addressList.add(coder.getFromLocationName("Food Conspiracy Co-op", 1).get(0));
//		    		addressList.add(coder.getFromLocationName("Basha's", 1).get(0));
//		    		addressList.add(coder.getFromLocationName("Whole Foods", 1).get(0));
//		    		addressList.add(coder.getFromLocationName("Sprouts", 1).get(0));
		    		
//					//Create iterator
//					Iterator<Address> locations = addressList.iterator();
//					double lat = 0f;
//					double lon = 0f;
					
//					// SHOW LOCATIONS
//					while (locations.hasNext()) {
//						Address loc = locations.next();
//						lat = loc.getLatitude();
//						lon = loc.getLongitude();
//					}	
		    		
		    		
					
					Address saf = coder.getFromLocationName("Safeway near ", 1).get(0);
					double lat = saf.getLatitude();
					double lon = saf.getLongitude();
					LatLng tokenGPS = new LatLng(lat, lon);
					map.addMarker(new MarkerOptions()
						.position(tokenGPS)
						.title("Safeway")
				        .snippet(coder.getFromLocationName("Safeway", 1).get(0).getAddressLine(0))
				        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
					
					saf = coder.getFromLocationName("Whole Foods", 1).get(0);
					lat = saf.getLatitude();
					lon = saf.getLongitude();
					tokenGPS = new LatLng(lat, lon);
					map.addMarker(new MarkerOptions()
						.position(tokenGPS)
						.title("Whole Foods")
				        .snippet(coder.getFromLocationName("Whole Foods", 1).get(0).getAddressLine(0))
				        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
					
					saf = coder.getFromLocationName("Sprouts", 1).get(0);
					lat = saf.getLatitude();
					lon = saf.getLongitude();
					tokenGPS = new LatLng(lat, lon);
					map.addMarker(new MarkerOptions()
						.position(tokenGPS)
						.title("Sprouts")
				        .snippet(coder.getFromLocationName("Sprouts", 1).get(0).getAddressLine(0))
				        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
					
					
					
					
					
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
