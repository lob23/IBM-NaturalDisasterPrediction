package com.example.naturaldisasterprediction.Service;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.naturaldisasterprediction.Constant.USER_ID_KEY;
import static com.example.naturaldisasterprediction.Constant.USER_PREF;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naturaldisasterprediction.Home.HomeBasic;
import com.example.naturaldisasterprediction.Home.Weather;
import com.example.naturaldisasterprediction.Models.GPSLocation;
import com.example.naturaldisasterprediction.Models.GeneralResponse;
import com.example.naturaldisasterprediction.Models.User.UserUpdateLocationRequest;
import com.example.naturaldisasterprediction.RequestPost;
import com.example.naturaldisasterprediction.ResponsePost;
import com.example.naturaldisasterprediction.SignUp.FamilyScreen;
import com.example.naturaldisasterprediction.SignUp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private Context context;
    private User user;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String USER_ID_KEY = "UserId";

    public UserService(Context context){
        this.context = context;
    }

    public interface UserCreationCallback{
        void onUserCreated();
        void onUserCreationFailed();
    }

    private void saveUserIdToLocal(String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_KEY, userId);
        editor.apply();
    }

    public String getUserIdFromLocal() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID_KEY, null);
    }

    interface RequestUser{
        @POST("/user/create")
        Call<ResponsePost> postUser(@Body RequestPost requestPost);

        @PUT("/user/updateLocation/{userId}")
        Call<GeneralResponse> updateLocation(@Path("userId") String userId, @Body UserUpdateLocationRequest userUpdateLocationRequest);

    }

    public void createUser(User user, UserCreationCallback callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.18:3000")
//                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.postUser(new RequestPost(user.getName(), user.getMail(), user.getPhone()))
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Save user id to shared preferences
                            String userId = response.body().getUserId();
                            Log.d("USER ID", userId);
                            saveUserIdToLocal(userId);
                            callback.onUserCreated();
                        }
                        else{
                            callback.onUserCreationFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable throwable) {
                        callback.onUserCreationFailed();
                    }
                });
    }

    public void sendUpdateLocation(GPSLocation location, String token) {

        // Get user id from shared preferences
        String userId = getUserIdFromLocal();

        // If user id is null, return
        if (userId == null) {
            return;
        }

        // Create request body
        if(token == null){
            Log.d("UPDATE LOCATION", "TOKEN IS NULL");
            return;
        }

        Log.d("UPDATE LOCATION", token);

        Date updateDate = new Date();
        UserUpdateLocationRequest userUpdateLocationRequest = new UserUpdateLocationRequest(location, token, updateDate);

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000")
                .baseUrl("http://192.168.1.18:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.updateLocation( userId, userUpdateLocationRequest)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        Log.d("UPDATE LOCATION", "SUCCESS");
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        Log.d("UPDATE LOCATION", "FAILED");
                    }
                });
    }

    public void updateUserLocation(GPSLocation location) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("FCM TOKEN", token);
                        sendUpdateLocation(location, token);
                    }
                });
    }
}
