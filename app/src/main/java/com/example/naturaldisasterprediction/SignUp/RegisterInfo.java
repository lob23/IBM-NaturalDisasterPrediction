package com.example.naturaldisasterprediction.SignUp;

import static com.example.naturaldisasterprediction.Constant.KEY_COLLECTION_USERS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
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
    Button button;
    UserService userService = new UserService(this);
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        fetchUI();
        handleClickUI();
//        loadInfo();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveUser();
                loadInfo();

                Toast.makeText(RegisterInfo.this, "DONE", Toast.LENGTH_LONG).show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadInfo() {
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
        userService.createUser(user);
    }

    private void saveUser() {
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

//        String dob = inputDOB.getText().toString();

    }

    private void fetchUI() {
        inputDOB = findViewById(R.id.inputDOB);
        inputGender = findViewById(R.id.inputGender);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        button = findViewById(R.id.saveUserButton);
    }
}