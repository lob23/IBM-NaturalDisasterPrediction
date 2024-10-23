package com.example.naturaldisasterprediction.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.naturaldisasterprediction.R;
import com.hbb20.CountryCodePicker;

public class PhoneScreen extends AppCompatActivity {

    private CountryCodePicker ccp;
    private EditText editPhoneNo;
    private ImageView validIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_screen);

        ccp = findViewById(R.id.ccp);
        editPhoneNo = findViewById(R.id.editPhoneNumber);
        validIcon = findViewById(R.id.validIcon);

        ccp.registerCarrierNumberEditText(editPhoneNo);

        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if(isValidNumber){
                    validIcon.setImageResource(R.drawable.yes_icon);
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
}