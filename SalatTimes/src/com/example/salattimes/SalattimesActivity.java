package com.example.salattimes;



import java.io.IOException;
import java.util.List;
import java.util.Locale;



import java.util.TimeZone;



import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SalattimesActivity extends Activity {

	public static final int UPDATE_TIME = 5000;  // time in milliseconds
	public static final int UPDATE_DISTANCE = 5;   // distance in meters  
	Location location;
	public static String str; // 
   
	String timeStr= "";
	String ss = "";
	int last = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salattimes);
		LinearLayout ll = new LinearLayout(this);
	    ll.setBackgroundDrawable(Drawable.createFromPath("/assets/moula.jpeg"));
	    ll.setBackgroundResource(R.drawable.moula);

		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		

		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, UPDATE_TIME, UPDATE_DISTANCE,
				new LocationListener() {
				@Override
				public void onLocationChanged(Location location) {
					refreshView(location);
				}
				@Override
				public void onProviderDisabled(String provider) {}
				@Override
				public void onProviderEnabled(String provider) {}
				@Override
				public void onStatusChanged(String provider, int status,
				Bundle extras) {}
				}
				);
			
		try {
			str = one.Position(location);
		}
		catch (Exception e){
			refreshView(location);
		}
	}
	
	public void handler(View view)
    {

		TextView onetime =  (TextView) findViewById(R.id.textView1);
	
		try {
				str = one.Position(location);
				String s = "" + str;
				onetime.setText(s);
			}
			catch (Exception e) {
	            e.toString();

	        }
		
			refreshView(location);
    }
	private void refreshView(Location location){
		TextView lat = (TextView) findViewById(R.id.lat);
		TextView lng = (TextView) findViewById(R.id.lng);
		TextView loc = (TextView) findViewById(R.id.loc);
	
		TextView onetime =  (TextView) findViewById(R.id.textView1);
	
		String s = "" + str;
		onetime.setText(s);
		
		if (location != null) {
			double latDbl = location.getLatitude();
			double lngDbl = location.getLongitude();
			lat.setText("lat: " + latDbl);
			lng.setText("long: " + lngDbl);
			
			if (Geocoder.isPresent()) {	
				
				Geocoder geocoder = new Geocoder(getApplicationContext(),
					Locale.getDefault());

				List<Address> addresses = null;
				Geocoder gc = new Geocoder(this, Locale.getDefault());
				try {
					addresses = gc.getFromLocation(latDbl, lngDbl, 10);
					loc.setText("Location: " + formatAddresses(addresses));
				} 
				catch (IOException e) {
					Log.v("*** DEBUG ***", "IO Exception while rev geocoding: " + e.toString());
					loc.setText("Locatoin: ");
				}
				
			}
			else
				Log.v("*** DEBUG ***", "Geocoding not available on this device...");
		}
	}
	
	
	private String formatAddresses(List<Address> addresses) {
		// Code from PAAD Chapter 13:
		StringBuilder sb = new StringBuilder();
		if (addresses.size() > 0) {
			Address address = addresses.get(0);
			for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
				sb.append(address.getAddressLine(i)).append("\n");
			
			sb.append(address.getCountryName());
		}
		return sb.toString();
	}
	public static String getStr() {
        return str;
    }

}