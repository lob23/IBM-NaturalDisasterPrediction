package com.example.naturaldisasterprediction.Service;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naturaldisasterprediction.Home.Weather;
import com.example.naturaldisasterprediction.RequestPost;
import com.example.naturaldisasterprediction.ResponsePost;
import com.example.naturaldisasterprediction.SignUp.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private Context context;
    private User user;

    public UserService(Context context){
        this.context = context;
    }

    interface RequestUser{
        @POST("/user/create")
        Call<ResponsePost> postUser(@Body RequestPost requestPost);
    }

    public void createUser(User user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.postUser(new RequestPost(user.getName(), "abc@mail", "123654"))
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable throwable) {

                    }
                });
    }
}
