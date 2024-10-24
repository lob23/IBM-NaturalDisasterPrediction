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
import java.util.concurrent.TimeUnit;

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


    public void sendToServer(SupplierResponseCallback callback) {
        // Convert this SuppliersService object to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        Log.d("sendToServer: ", json);

        // Send the JSON to the server
        sendFileToServer(json, callback);

    }

    public void sendFileToServer(String json, SupplierResponseCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
                .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
                .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.32:3000/")
                .client(okHttpClient)  // Use the custom client with timeout settings
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SupplierApi apiService = retrofit.create(SupplierApi.class);

        // Create RequestBody from JSON string
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);


        Call<SupplierResponse> call = apiService.sendSuppliers(requestBody);
        call.enqueue(new retrofit2.Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, retrofit2.Response<SupplierResponse> response) {
                Log.d("isSuccessful:", response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    SupplierResponse supplierResponse = response.body();  // Save response if needed.
                    Log.d("onResponse: ", supplierResponse.toString());
                    callback.onSuccess(supplierResponse);  // Pass response to callback.
                } else {
                    callback.onFailure("POST request failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Log.d("ditconme", "ma");
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }


    public interface SupplierApi {
        @POST("weather/getsuppliers")
        Call<SupplierResponse> sendSuppliers(@Body RequestBody requestBody);
    }


}


