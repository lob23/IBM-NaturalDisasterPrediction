package com.example.naturaldisasterprediction.Service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naturaldisasterprediction.Home.MyLocation;
import com.example.naturaldisasterprediction.Home.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    private MyLocation myLocation;
    private Context context;

    public WeatherService(Context context){
        this.context = context;
        this.myLocation = new MyLocation(context);
    }

    public void getCurrentWeather(WeatherCallback callback) {
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();

        fetchWeatherList(longitude, latitude, callback);
    }

    private void fetchWeatherList(double longitude, double latitude, WeatherCallback callback) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=dcfabdd5c7fc896819d09133733b7eea";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    List<Weather> weatherList = handleWeatherArray(jsonArray);
                    // Notify the callback with the fetched weather data
                    callback.onSuccess(weatherList);
                } catch (JSONException e) {
                    callback.onError(e);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(new Exception("Network error: " + error.getMessage()));
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Weather> handleWeatherArray(JSONArray jsonArray) throws JSONException {
        List<Weather> res = new ArrayList<>();
        LocalDateTime currentTimeUTC = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        boolean check = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject weatherItem = jsonArray.getJSONObject(i);
            String dtTxt = weatherItem.getString("dt_txt");
            LocalDateTime weatherDate = LocalDateTime.parse(dtTxt, formatter);
            String dateCheck = getDatePart(dtTxt);

            // First weather item of the current day in UTC
            if (!check && weatherDate.toLocalDate().isEqual(currentTimeUTC.toLocalDate()) && i == 0) {
                check = true;
                res.add(createWeather(weatherItem, dtTxt));
            }

            // Add weather if it's 15:00 and falls within 5 days from the current time
            if (check && weatherDate.getHour() == 15 &&
                    weatherDate.isAfter(currentTimeUTC) &&
                    weatherDate.isBefore(currentTimeUTC.plusDays(5)) &&
                    !getDatePart(res.get(0).getTime()).equals(dateCheck)) {

                res.add(createWeather(weatherItem, dtTxt));
            }
        }

        return res;
    }

    private Weather createWeather(JSONObject weatherItem, String dtTxt) throws JSONException {
        JSONObject main = weatherItem.getJSONObject("main");
        int temp = (int) (main.getDouble("temp") - 273.15); // Convert Kelvin to Celsius
        JSONArray weatherArray = weatherItem.getJSONArray("weather");
        int id = weatherArray.getJSONObject(0).getInt("id");

        return new Weather(temp, id, dtTxt);
    }

    public interface WeatherCallback {
        void onSuccess(List<Weather> weatherConditions);
        void onError(Exception e);
    }

    public String getDatePart(String dateTime) {
        // Extract the date part before the 'T'
        return dateTime.substring(0, dateTime.indexOf(' '));
    }
}
