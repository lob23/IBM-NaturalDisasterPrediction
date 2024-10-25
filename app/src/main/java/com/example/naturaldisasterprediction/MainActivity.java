package com.example.naturaldisasterprediction;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naturaldisasterprediction.Home.HomeBasic;
import com.example.naturaldisasterprediction.Home.HomeFlood;
import com.example.naturaldisasterprediction.Home.MyLocation;
import com.example.naturaldisasterprediction.Home.SendUser;
import com.example.naturaldisasterprediction.Home.SupplierResponse;
import com.example.naturaldisasterprediction.Home.Weather;
import com.example.naturaldisasterprediction.Service.SupplierResponseCallback;
import com.example.naturaldisasterprediction.Service.SuppliersService;
import com.example.naturaldisasterprediction.Home.Weather;
import com.example.naturaldisasterprediction.Models.GPSLocation;
import com.example.naturaldisasterprediction.Models.User.UserUpdateLocationRequest;
import com.example.naturaldisasterprediction.Service.UserService;
import com.example.naturaldisasterprediction.Service.WeatherService;
import com.example.naturaldisasterprediction.SignUp.FamilyScreen;
import com.example.naturaldisasterprediction.SignUp.PhoneScreen;
import com.example.naturaldisasterprediction.SignUp.QuickCustom;
import com.example.naturaldisasterprediction.SignUp.RegisterInfo;
import com.example.naturaldisasterprediction.SignUp.SupportScreen;
import com.example.naturaldisasterprediction.SignUp.TestView;
import com.example.naturaldisasterprediction.SignUp.User;
import com.google.firebase.FirebaseApp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    MyLocation myLocation;
    TextView locationText;
    TextView addressText;
    private WeatherService weatherService;
    private UserService userService;
    private SuppliersService suppliersService;

 //   private UserService userService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent i = new Intent(MainActivity.this, HomeFlood.class);
        startActivity(i);
////
        locationText = findViewById(R.id.locationText);
        addressText = findViewById(R.id.addressText);
        weatherService = new WeatherService(this);
        userService = new UserService(this);

        setupLocation();
        fetchData();
    }

    private void fetchData() {
        weatherService.getCurrentWeather(new WeatherService.WeatherCallback() {
        @Override
        public void onSuccess(List<Weather> weatherConditions) {
            // Update UI with the weather data
            Log.d("TEST WEATHER: ", weatherConditions.toString());
        }

        @Override
        public void onError(Exception e) {
            // Handle the error, e.g., show a toast message
            Log.d("TEST WEATHER ERROR: ", e.getMessage());
        }
    });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, create an instance of MyLocation and update the UI
                setupLocation();
            } else {
                // Permission denied, handle it gracefully
                locationText.setText("Permission denied.");
            }
        }
    }
    private void setupLocation() {
        myLocation = new MyLocation(this);
        Log.d("setupLocation: ", String.valueOf(myLocation==null));
        updateLocation();
    }

    private void updateLocation() {
        Log.d( "updateLocation: ", "okay");
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        String country = myLocation.getCountry();
        String city = myLocation.getCity();
        locationText.setText("Longtitude: " + longitude + " Latitude: " + latitude);
        addressText.setText("Address: " + country + ", " + city);

        // Update the user's location in the backend
        GPSLocation location = new GPSLocation(latitude, longitude, city, country);
        userService.updateUserLocation(location);
        Log.d("userService", "okay");
    }
}
