package com.example.naturaldisasterprediction.Home;

import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeBasic extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    MyLocation myLocation;
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_basic);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        weatherService = new WeatherService(this);

        fetchData();
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
        TextView locationText=findViewById(R.id.locationText);
        locationText.setText(myLocation.getCity() + ", " + myLocation.getCountry());

        TextView desription = findViewById(R.id.description);
        desription.setText("The sky is beautiful in your area, but not everywhere. Let’s help those in need!");
    }

    private void fetchData() {
        weatherService.getCurrentWeather(new WeatherService.WeatherCallback() {
            @Override
            public void onSuccess(List<Weather> weatherConditions) {
                // Update UI with the weather data
                Log.d("TEST WEATHER: ", weatherConditions.toString());
                updateUI(weatherConditions);

            }

            @Override
            public void onError(Exception e) {
                // Handle the error, e.g., show a toast message
                Log.d("TEST WEATHER ERROR: ", e.getMessage());
            }
        });
    }
    private void updateUI(List<Weather> weatherConditions)
    {
        List<Integer> days=generateDays(5);
        TextView textViewCurrentDay = findViewById(R.id.temp);
        textViewCurrentDay.setText(days.get(0).toString());
        TextView textViewFirstDate= findViewById(R.id
                .firstDayDate);
        TextView currentWeather = findViewById(R.id.weather);
        setWeatherText(currentWeather, weatherConditions.get(0).getId());
        ImageView imageViewCurrentDay= findViewById(R.id.currentWeather);
        setImageView(imageViewCurrentDay, weatherConditions.get(0).getId());
        textViewFirstDate.setText(days.get(0).toString());
        ImageView imageViewfirst=findViewById(R.id.firstDayImage);
        setImageView(imageViewfirst, weatherConditions.get(0).getId());
        TextView textViewFirstTemp=findViewById(R.id.firstDayTemp);
        textViewFirstTemp.setText(String.valueOf(weatherConditions.get(0).getTemp()));


// Second Day
        TextView textViewSecondDate = findViewById(R.id.secondDayDate);
        textViewSecondDate.setText(days.get(1).toString());
        ImageView imageViewSecond = findViewById(R.id.secondDayImage);
        setImageView(imageViewSecond, weatherConditions.get(1).getId());
        TextView textViewSecondTemp = findViewById(R.id.secondDayTemp);
        textViewSecondTemp.setText(String.valueOf(weatherConditions.get(1).getTemp()));

// Third Day
        TextView textViewThirdDate = findViewById(R.id.thirdDayDate);
        textViewThirdDate.setText(days.get(2).toString());
        ImageView imageViewThird = findViewById(R.id.thirdDayImage);
        setImageView(imageViewThird, weatherConditions.get(2).getId());
        TextView textViewThirdTemp = findViewById(R.id.thirdDayTemp);
        textViewThirdTemp.setText(String.valueOf(weatherConditions.get(2).getTemp()));

// Fourth Day
        TextView textViewFourthDate = findViewById(R.id.fourthDayDate);
        textViewFourthDate.setText(days.get(3).toString());
        ImageView imageViewFourth = findViewById(R.id.fourthDayImage);
        setImageView(imageViewFourth, weatherConditions.get(3).getId());
        TextView textViewFourthTemp = findViewById(R.id.fourthDayTemp);
        textViewFourthTemp.setText(String.valueOf(weatherConditions.get(3).getTemp()));

// Fifth Day
        TextView textViewFifthDate = findViewById(R.id.fifthDayDate);
        textViewFifthDate.setText(days.get(4).toString());
        ImageView imageViewFifth = findViewById(R.id.fifthDayImage);
        setImageView(imageViewFifth, weatherConditions.get(4).getId());
        TextView textViewFifthTemp = findViewById(R.id.fifthDayTemp);
        textViewFifthTemp.setText(String.valueOf(weatherConditions.get(4).getTemp()));

    }
    private void setTempDescription(String id1, TextView textView)
    {
        String id =  id1.substring(0, 2);

        switch (id){
            case "01":
            case "02":
                textView.setText("Sunny");
                break;
            case "03":
            case "04":
                textView.setText("Cloudy");
                break;
            case "09":
            case "10":
                textView.setText("Rainy");
                break;
            case "11":
                textView.setText("Thunderstorm");
                break;
        }
    }
    private void setWeatherText(TextView textView, String id1)
    {
        String id =  id1.substring(0, 2);
        Log.d("setWeatherText: ",id);
        switch (id)
        {
            case "01":
                textView.setText("Clear Sky");
                break;
            case "02":
                textView.setText("Few Clouds");
                break;
            case "03":
                textView.setText("Scattered Clouds");
                break;
            case "04":
                textView.setText("Broken Clouds");
                break;
            case "09":
                textView.setText("Shower Rain");
                break;
            case "10":
                textView.setText("Rain");
                break;
            case "11":
                textView.setText("Thunderstorm");
                break;
        }
    }
    private void setImageView(ImageView imageView, String id1)
    {
        String id =  id1.substring(0, 2);
        switch (id) {
            case "01":
                imageView.setImageResource(R.drawable._1d);
                break;
            case "02":
                imageView.setImageResource(R.drawable._2d);
                break;
            case "03":
                imageView.setImageResource(R.drawable._3d);
                break;
            case "04":
                imageView.setImageResource(R.drawable._4d);
                break;
            case "09":
                imageView.setImageResource(R.drawable._9d);
                break;
            case "10":
                imageView.setImageResource(R.drawable._10d);
                break;
            case "11":
                imageView.setImageResource(R.drawable._11d);
                break; // Added missing break
            default:
                // Default case for unexpected IDs
                imageView.setImageResource(R.drawable._1d);
                break;
        }
    }
            // Return the list of XML file names

    public List<Integer> generateDays(int numberOfDays) {
        List<Integer> dayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < numberOfDays; i++) {
            dayList.add(calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dayList;
    }

}