package com.example.naturaldisasterprediction.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.naturaldisasterprediction.R;

public class QuickCustom extends AppCompatActivity {

    TextView quickBtn, customBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_custom);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fetchUI();
        handleButton();
    }

    private void handleButton() {
        quickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuickCustom.this, QuickRation.class);
                startActivity(i);
            }
        });

        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuickCustom.this, FamilyScreen.class);
                startActivity(i);
            }
        });
    }

    private void fetchUI() {
        quickBtn = findViewById(R.id.QuickButton);
        customBtn = findViewById(R.id.CustomButton);
    }
}