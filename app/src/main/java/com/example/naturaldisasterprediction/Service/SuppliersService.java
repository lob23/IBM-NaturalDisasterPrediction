package com.example.naturaldisasterprediction.Service;

import android.util.Log;

import com.example.naturaldisasterprediction.Home.SendUser;
import com.example.naturaldisasterprediction.Home.SupplierResponse;
import com.example.naturaldisasterprediction.SignUp.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;


public class SuppliersService {
    public ArrayList<SendUser> members;
    public double latitude;
    public double longitude;
    public int day = 0;


    public SuppliersService(ArrayList<User> members, double latitude, double longitude, int day) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.day = day;
        this.members = new ArrayList<>();
        for (int i = 0; i < members.size(); ++i) {
            SendUser sendUser = new SendUser();
            convertUserToSendUser(members.get(i), sendUser);
            this.members.add(sendUser);
        }
    }


    public void convertUserToSendUser(User user, SendUser sendUser) {
        LocalDate currentDate = LocalDate.now();
        sendUser.setHealthName(user.getName());
        sendUser.setHealthWeight(user.getWeight());
        sendUser.setHealthHeight(user.getHeight());
        sendUser.setHealthAge(Period.between(user.getBirth(), currentDate).getYears());
        sendUser.setHealthGender(user.getGender() == 1 ? "Male" : "Female");
    }


    //    public void sendToServer() {
//        try {
//            // Create URL object192.168.102.24
//            URL url = new URL("https://10.0.2.2:3000/weather/getsuppliers");
//
//            // Open connection
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
//
//            // Convert object to JSON using Gson
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String jsonInputString = gson.toJson(this);
//            Log.d("sendToServer: ",jsonInputString);
//
//            // Write the JSON data to the request body
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            // Check the response code
//            int responseCode = conn.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                System.out.println("Data sent successfully.");
//            } else {
//                System.out.println("POST request failed. Response code: " + responseCode);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void sendToServer() {
        // Convert this SuppliersService object to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        Log.d("sendToServer: ", json);

        // Send the JSON to the server
        sendFileToServer(json);

    }

    public void sendFileToServer(String json) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.102.24:3000/").addConverterFactory(GsonConverterFactory.create()).build();

        SupplierApi apiService = retrofit.create(SupplierApi.class);

        // Create RequestBody from JSON string
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<SupplierResponse> call = apiService.sendSuppliers(requestBody);

        call.enqueue(new retrofit2.Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, retrofit2.Response<SupplierResponse> response) {
                Log.d("onResponse raw: ", response.raw().toString());
                if (response.isSuccessful() && response.body() != null) {
                    // Get the JSON response mapped to the SupplierResponse model
                    SupplierResponse supplierResponse = response.body();
                    Log.d("onResponse: ", supplierResponse.toString());

                    // Access food items
                    if (supplierResponse.getFood() != null) {
                        for (SupplierResponse.FoodItem foodItem : supplierResponse.getFood()) {
                            Log.d("Food Item", foodItem.toString());
                        }
                    }

                    // Access clothing items
                    if (supplierResponse.getClothing() != null) {
                        for (SupplierResponse.ClothingItem clothingItem : supplierResponse.getClothing()) {
                            Log.d("Clothing Item", clothingItem.toString());
                        }
                    }

                    // Access other supplies
                    if (supplierResponse.getOtherSupplies() != null) {
                        for (SupplierResponse.OtherSupplyItem otherSupplyItem : supplierResponse.getOtherSupplies()) {
                            Log.d("Other Supply Item", otherSupplyItem.toString());
                        }
                    }


                } else {
                    Log.e("sendToServer", "POST request failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Log.e("sendToServer", "Network error: " + t.getMessage());
            }
        });
    }


    public interface SupplierApi {
        @POST("weather/getsuppliers")
        Call<SupplierResponse> sendSuppliers(@Body RequestBody requestBody);
    }


}

