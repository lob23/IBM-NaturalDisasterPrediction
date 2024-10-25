package com.example.naturaldisasterprediction.SignUp;

import static com.example.naturaldisasterprediction.Constant.KEY_COLLECTION_USERS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.naturaldisasterprediction.Home.HomeBasic;
import com.example.naturaldisasterprediction.MyAdapter;
import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.Service.UserService;
import com.example.naturaldisasterprediction.SharedPreferenceManager;

import java.util.ArrayList;

public class FamilyScreen extends AppCompatActivity {
    private MyAdapter myAdapter;
    private ArrayList<User> itemList;
    RecyclerView recyclerView;
    TextView addbtn, nextbtn;
    User user;
    UserService userService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fetchUI();
        setUp();
        getUser();

        // Handle "Next" button click to retrieve family information
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveFamilyInfo();
                shareUser();
//                createUser();
                Log.d("SHIBA", "shiba");
                Intent i = new Intent(FamilyScreen.this, HomeBasic.class);
                startActivity(i);
            }
        });
    }

    private void createUser() {
        userService.createUser(user);
    }

    private void getUser() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        user = (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUp() {
        itemList = new ArrayList<>();
        itemList.add(new User());  // Add one family member (User) with default values

        myAdapter = new MyAdapter(this, itemList, getSupportFragmentManager());
        userService = new UserService(this);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        // Add button functionality
        addbtn.setOnClickListener(v -> {
            myAdapter.addItem();
        });
    }

    private void shareUser(){
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }

    private void fetchUI() {
        recyclerView = findViewById(R.id.recycler_view);
        addbtn = findViewById(R.id.btn_add);
        nextbtn = findViewById(R.id.nextButton3);
    }

    // Method to retrieve the family information and assign it to the current user
    private void retrieveFamilyInfo() {
        ArrayList<User> familyList = myAdapter.getItemList();  // Get the family members from the adapter

        user.setFamily(familyList);  // Assign family members to the user

        Log.d("FAMILY: ", familyList.toString());

        // Now the current user has a family list with each member's details
    }
}
