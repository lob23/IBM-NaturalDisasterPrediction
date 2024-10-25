package com.example.naturaldisasterprediction.SignUp;

import static com.example.naturaldisasterprediction.Constant.KEY_COLLECTION_USERS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class QuickRation extends AppCompatActivity {

    TextView minus, plus, number;
    int familyCount = 0;
    User user;
    TextView nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_ration);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getUser();

        fetchUI();
        handleButton();
    }

    private void getUser() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        user = (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveFamily(){
        List<User> family = new ArrayList<>();

        // Populate the family list based on familyCount
        for (int i = 0; i < familyCount; i++) {
            // Create new User objects or retrieve existing ones.
            // This is just an example; modify this to match your needs.
            User familyMember = new User(); // Replace with actual initialization
            family.add(familyMember); // Add the new user to the list
        }

        user.setFamily(family);
    }

    private void handleButton() {
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (familyCount > 0) {  // Prevent the count from going below zero
                    familyCount--;
                    number.setText(String.valueOf(familyCount));
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familyCount++;
                number.setText(String.valueOf(familyCount));
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                saveFamily();
                shareUser();
            }
        });
    }

    private void shareUser(){
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }
    private void fetchUI() {
        minus = findViewById(R.id.minusButton);
        plus = findViewById(R.id.plusButton);
        number = findViewById(R.id.textNumberFamily);
        nextbtn = findViewById(R.id.nextButton2);
    }
}