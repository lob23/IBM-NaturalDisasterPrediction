package com.example.naturaldisasterprediction.SignUp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.naturaldisasterprediction.R;

public class TestView extends AppCompatActivity {

     LinearLayout llFamilyForms;
     EditText etFamilyMembers;
     Button btnGenerateForms;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        etFamilyMembers = findViewById(R.id.et_family_members);
        llFamilyForms = findViewById(R.id.ll_family_forms);
        btnGenerateForms = findViewById(R.id.btn_generate_forms);

        btnGenerateForms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateFamilyForms();
            }
        });
    }

    private void generateFamilyForms() {
        // Clear any existing forms before generating new ones
        llFamilyForms.removeAllViews();

        // Get the number of family members
        String familyMembersInput = etFamilyMembers.getText().toString();
        int numberOfFamilyMembers;
        try {
            numberOfFamilyMembers = Integer.parseInt(familyMembersInput);
        } catch (NumberFormatException e) {
            numberOfFamilyMembers = 0;
        }

        // Create dynamic forms based on the number of family members
        for (int i = 0; i < numberOfFamilyMembers; i++) {
            // Create a new EditText for family member name
            EditText etName = new EditText(this);
            etName.setHint("Family Member " + (i + 1) + " Name");
            etName.setInputType(InputType.TYPE_CLASS_TEXT);

            // Create a new EditText for family member age
            EditText etAge = new EditText(this);
            etAge.setHint("Family Member " + (i + 1) + " Age");
            etAge.setInputType(InputType.TYPE_CLASS_NUMBER);

            // Add the name and age EditTexts to the LinearLayout
            llFamilyForms.addView(etName);
            llFamilyForms.addView(etAge);
        }
    }
}
