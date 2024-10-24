package com.example.naturaldisasterprediction.SignUp;

import static com.example.naturaldisasterprediction.Constant.KEY_COLLECTION_USERS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.Service.UserService;
import com.example.naturaldisasterprediction.SharedPreferenceManager;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class RegisterInfo extends AppCompatActivity {
    TextView inputName;
    TextView inputEmail;
    TextView inputDOB;
    TextView inputGender;
    User user;
    LocalDate localDate;
    TextView button;
    UserService userService = new UserService(this);
    Boolean fullfill = false;
    TextView heightVal, weightVal;
    Slider sliderHeight, sliderWeight;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fetchUI();
        handleClickUI();
        handleButton();

    }

    private void handleButton() {

        checkInput();
        if(fullfill){
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

//                    shareUser();
//                    upUserToServer();

                    Intent i = new Intent(RegisterInfo.this, SupportScreen.class);
                    startActivity(i);
                }
            });
        }


    }

    private void checkInput() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String dob = inputDOB.getText().toString().trim();
        String gender = inputGender.getText().toString().trim();

        float height = sliderHeight.getValue();
        float weight = sliderWeight.getValue();

        // Check if all fields are filled and sliders have valid values
        if (!name.isEmpty() && !email.isEmpty() && !dob.isEmpty() && !gender.isEmpty() && height > 0 && weight > 0) {
            // Enable the button and change its drawable
            button.setBackgroundResource(R.drawable.button2);
            button.setClickable(true); // Make the button clickable
            button.setTextColor(getResources().getColor(R.color.white));
            fullfill = true;
        } else {
            // Disable the button or reset its background if not all fields are filled
            button.setBackgroundResource(R.drawable.button1); // or your default button drawable
            button.setClickable(false);
            fullfill = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void upUserToServer() {
        String gender = inputGender.getText().toString();
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();

        user = new User();
        user.setName(name);
        user.setEmail(email);
        if(gender.equals("Male")){
            user.setGender(1);
        } else if(gender.equals("Female")){
            user.setGender(2);
        }
        user.setBirth(localDate);
//        userService.createUser(user);
    }

    private void shareUser() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user, KEY_COLLECTION_USERS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleClickUI() {
        inputDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date(selection));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.getDefault());
                        localDate = LocalDate.parse(date, formatter);
                        inputDOB.setText(date);
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        inputGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] genderOptions = {"Male", "Female"};
                final int[] selectedOption = {-1};


                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterInfo.this);
                builder.setTitle("Select Gender");
                builder.setSingleChoiceItems(genderOptions, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedOption[0] = which;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedOption[0] != -1) {
                            inputGender.setText(genderOptions[selectedOption[0]]);
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog when 'Cancel' is clicked
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });

        heightVal.setText("0 cm");
        weightVal.setText("0 kg");

        // Listen for changes in the height slider and update the height value TextView
        sliderHeight.addOnChangeListener((slider, value, fromUser) -> {
           heightVal.setText(String.format("%.0f cm", value));
           checkInput();
        });

        // Listen for changes in the weight slider and update the weight value TextView
        sliderWeight.addOnChangeListener((slider, value, fromUser) -> {
           weightVal.setText(String.format("%.0f kg", value));
           checkInput();
        });

    }

    private void fetchUI() {
        inputDOB = findViewById(R.id.inputDOB);
        inputGender = findViewById(R.id.inputGender);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        button = findViewById(R.id.saveUserButton);
        heightVal = findViewById(R.id.heightValue);
        weightVal = findViewById(R.id.weightValue);
        sliderHeight = findViewById(R.id.sliderHeight);
        sliderWeight = findViewById(R.id.sliderWeight);

        // Adding text watchers to detect input changes
        inputName.addTextChangedListener(inputWatcher);
        inputEmail.addTextChangedListener(inputWatcher);
        inputDOB.addTextChangedListener(inputWatcher);
        inputGender.addTextChangedListener(inputWatcher);
    }

    private final TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Call method to check if all fields are filled
            checkInput();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}