package edu.arizona.foundeats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * 
 * @author 1nc09_000, Kris Cabulong
 *
 * TODO:
 * 1) Get list of nearby grocery stores
 * 2) On each snippet, show address, how far away they are from user, how many times they've been visited
 * 3) ...
 *
 */
public class MapActivity extends Activity implements LocationListener {
	static final LatLng TUCSON = new LatLng(32.221743, -110.926479);
	private GoogleMap map;
//	private GoogleApiClient mGoogleApiClient;
	private DataHelper dh;
//	private Marker currLoc;
//	GoogleMap googleMap;
	private int PROXIMITY_RADIUS = 5000;
	private double latitude = 0;
	private double longitude = 0;
	
	
	private GoogleMap.OnMyLocationChangeListener currLocChangeListener = new GoogleMap.OnMyLocationChangeListener() {
		
		@Override
		public void onMyLocationChange(Location location) {
			
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
	        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

//	        if(map != null){
//	            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
//	        }
			
		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		dh = new DataHelper(this);
		
		final Geocoder coder = new Geocoder(getApplicationContext());

		 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
			        .getMap();
		 
		 map.setMyLocationEnabled(true);
		 map.setOnMyLocationChangeListener(currLocChangeListener);
		 
		 Location myLocation = map.getMyLocation();
		
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		
		latitude = 32.221743;
		longitude = -110.926479;
		if (location != null){
			onLocationChanged(location);
			Log.d("MapActivity", "latitude: " + location.getLatitude());
			Log.d("MapActivity", "longitude: " + location.getLongitude());
			
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
	        if(map != null){
	        	LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
	        	map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
	        }
		}
		locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
			
		StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
		googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=" + "grocery_or_supermarket");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDtbMBY61L8E1en7zS9x8EvSeweS2Gw6Qw");

        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[2];
        toPass[0] = map;
        toPass[1] = googlePlacesUrl.toString();
        googlePlacesReadTask.execute(toPass);
//		
//		StringBuilder googlePlacesUrl2 = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//		googlePlacesUrl.append("location=" + latitude + "," + longitude);
//        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
//        googlePlacesUrl.append("&types=" + "restaurant");
//        googlePlacesUrl.append("&sensor=true");
//        googlePlacesUrl.append("&key=" + "AIzaSyDtbMBY61L8E1en7zS9x8EvSeweS2Gw6Qw");
//
//        GooglePlacesReadTask googlePlacesReadTask2 = new GooglePlacesReadTask();
//        Object[] toPass2 = new Object[2];
//        toPass[0] = map;
//        toPass[1] = googlePlacesUrl2.toString();
//        googlePlacesReadTask2.execute(toPass2);
		 
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


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
