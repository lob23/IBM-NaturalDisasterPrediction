package com.example.naturaldisasterprediction.SignUp;

import static com.example.naturaldisasterprediction.Constant.KEY_COLLECTION_USERS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.SharedPreferenceManager;
import com.hbb20.CountryCodePicker;

public class PhoneScreen extends AppCompatActivity {

    private CountryCodePicker ccp;
    private EditText editPhoneNo;
    private ImageView validIcon;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fetchUI();
        getUser();
        handlePhone();
    }

    private void getUser() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        user = (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);
    }

    private void handlePhone() {
        ccp.registerCarrierNumberEditText(editPhoneNo);

        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if(isValidNumber){
                    validIcon.setImageResource(R.drawable.yes_icon);
                    setUserPhoneNumber();
                }
                else{
                    validIcon.setImageResource(R.drawable.no_icon);
                }
            }
        });

        editPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().trim();
                if(input.length() > 0){
                    validIcon.setVisibility(View.VISIBLE);
                }else{
                    validIcon.setVisibility(View.GONE);
                }
            }
        });


    }

    private void setUserPhoneNumber() {
        if (user != null) {

            // Get the full phone number with country code
            String fullPhoneNumber = ccp.getFullNumberWithPlus();
            // Set the phone number for the user
            user.setPhone(fullPhoneNumber);
            shareUser();
            handleButton();
            // Optionally save the user object back to SharedPreferences if needed

        }
    }

    private void handleButton() {
        validIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhoneScreen.this, SupportScreen2.class);
                startActivity(i);
            }
        });
    }

    private void shareUser() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }

    private void fetchUI() {
        ccp = findViewById(R.id.ccp);
        editPhoneNo = findViewById(R.id.editPhoneNumber);
        validIcon = findViewById(R.id.validIcon);
    }
}