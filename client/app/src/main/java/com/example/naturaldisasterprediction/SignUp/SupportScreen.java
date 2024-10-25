package com.example.naturaldisasterprediction.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.naturaldisasterprediction.R;


public class SupportScreen extends AppCompatActivity {

    TextView nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        nextBtn = findViewById(R.id.nextButton1);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SupportScreen.this, PhoneScreen.class);
                startActivity(i);
            }
        });
    }
}