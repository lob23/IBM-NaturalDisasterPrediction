package com.example.naturaldisasterprediction.Home;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.naturaldisasterprediction.Models.GPSLocation;
import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.Service.WeatherService;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class HomeBasic extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    MyLocation myLocation;
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_basic);

        myLocation= new MyLocation(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        TextView locationText=findViewById(R.id.locationText);
//        locationText.setText(myLocation.getCity() + ", " + myLocation.getCountry());
//
//        TextView desription = findViewById(R.id.description);
//        desription.setText("The sky is beautiful in your area, but not everywhere. Let’s help those in need!");

        setupLocation(); // tam thoi

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        // Format the day of the week (e.g., Thursday) and the date (dd-MM-yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());

        TextView currentDay = findViewById(R.id.currentDay);
        currentDay.setText(formattedDate);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, create an instance of MyLocation and update the UI
                setupLocation();
            }
//            } else {
//                // Permission denied, handle it gracefully
//                locationText.setText("Permission denied.");
//            }
        }
    }
    private void setupLocation() {
        myLocation = new MyLocation(this);
        updateLocation();
    }

    private void updateLocation() {
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        String country = myLocation.getCountry();
        String city = myLocation.getCity();
//        locationText.setText("Longtitude: " + longitude + " Latitude: " + latitude);
//        addressText.setText("Address: " + country + ", " + city);
        TextView locationText=findViewById(R.id.locationText);
        locationText.setText(myLocation.getCity() + ", " + myLocation.getCountry());

        TextView desription = findViewById(R.id.description);
        desription.setText("The sky is beautiful in your area, but not everywhere. Let’s help those in need!");

        // Update the user's location in the backend
//        GPSLocation location = new GPSLocation(latitude, longitude, city, country);
//        userService.updateUserLocation(location);
    }
}