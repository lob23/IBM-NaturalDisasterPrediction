package com.example.naturaldisasterprediction.Home;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocation {
    private LocationManager locationManager;
    private Location currentLocation;
    private Context context;

//    public double longitude, latitude;

    public MyLocation(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        fetchLocation();
    }

    // Fetches the current location
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle permission not granted scenario
            return;
        }

        // Set up the LocationListener to update the location
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Request location updates every 1 minute
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locationListener);

        currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    // Get latitude
    public double getLatitude() {
        if (currentLocation != null) {
            return currentLocation.getLatitude();
        } else {
            return 0.0; // Default value if location is null
        }
    }

    // Get longitude
    public double getLongitude() {
        if (currentLocation != null) {
            return currentLocation.getLongitude();
        } else {
            return 0.0;
        }
    }

    private Address getAddress(){
        Locale englishLocale = new Locale("en", "US");
        Geocoder geocoder = new Geocoder(this.context, englishLocale);

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Address res = addresses.get(0);
        return res;
    }

    public String getCountry(){
        Address address = this.getAddress();
        String country = address.getCountryName();
        return  country;
    }

    public String getCity(){
        Address address = this.getAddress();
        String city = address.getAdminArea();
        return city;
    }
}
