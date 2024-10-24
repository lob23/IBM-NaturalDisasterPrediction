package com.example.naturaldisasterprediction.Home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.naturaldisasterprediction.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class HomeBasic extends AppCompatActivity {
    MyLocation myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_basic);

        myLocation= new MyLocation(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView locationText=findViewById(R.id.locationText);
        locationText.setText(myLocation.getCity() + ", " + myLocation.getCountry());

        TextView desription = findViewById(R.id.description);
        desription.setText("The sky is beautiful in your area, but not everywhere. Let’s help those in need!");

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        // Format the day of the week (e.g., Thursday) and the date (dd-MM-yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());

        TextView currentDay = findViewById(R.id.currentDay);
        currentDay.setText(formattedDate);

    }
}