package com.example.naturaldisasterprediction;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

public class MyLocation {
    private LocationManager locationManager;
    private Location currentLocation;
    private Context context;

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
            public void onLocationChanged(android.location.Location location) {
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
            return 0.0; // Default value if location is null
        }

    }
}
