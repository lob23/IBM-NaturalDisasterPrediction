package com.example.naturaldisasterprediction.Home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturaldisasterprediction.Adapter.ItemAdapter;
import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.Service.ItemModel;

import java.util.List;

public class HomeFlood extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> itemList; // Ensure you have your item list initialized
    private View statusBarAbove;
    private View statusBarBelow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_flood);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.rationList);

        // Storm and Flood status bar
        statusBarAbove = findViewById(R.id.HomeBasic_SafeTime);
        statusBarBelow = findViewById(R.id.HomeBasic_DangerTime);


        int daysBeforeFlood = 2; // Before Storm
        int daysDuringFlood = 6; // Duration of Storm + Flood

        float density = getResources().getDisplayMetrics().density;
        float oneDayLength = (float) Math.round(340 * density) /7; // the length of each day in the status bar. Particularly, we split the bar into 7 partitions, each represents for a day. Hence, this bar responsible for illustrating how long the flood is predicted to come and the duration of the flood.

        // The bar is split into two parts. If it is a day of flood is coming, we switch the color of the upper bar into red and adjust its width to overlap the under bar, which is set into blue, and vice versa.
        ViewGroup.LayoutParams aboveBarParams = statusBarAbove.getLayoutParams();

        if (daysBeforeFlood > 0){
            // Set the above bar into red, representing for flood coming days.
            statusBarAbove.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red_dam)));
            statusBarBelow.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue_light)));

            // Adjusting the width of the red bars to represent the number of days before the flood.
            aboveBarParams.width = (int) (oneDayLength * daysBeforeFlood);
            statusBarAbove.setLayoutParams(aboveBarParams);
        } else if (daysDuringFlood > 0){
            // Set the above bar into blue, representing for number of days the flood overcome.
            statusBarAbove.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue_light)));
            statusBarBelow.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red_dam)));

            // Adjusting the width of the red bars to represent the number of days before the flood
            aboveBarParams.width = (int) (oneDayLength * daysDuringFlood);
            statusBarAbove.setLayoutParams(aboveBarParams);
        } else {
            statusBarAbove.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue_light)));
            statusBarBelow.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red_dam)));

            // Adjusting the width of the red bars to represent the number of days before the flood
            aboveBarParams.width = (int) (oneDayLength*7);
            statusBarAbove.setLayoutParams(aboveBarParams);
        }

        // Assuming you have a method to populate your itemList
        itemList = populateItemList(); // Replace this with your actual data fetching logic

        // Setup the adapter
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Dummy method to populate itemList, replace with actual logic
    private List<ItemModel> populateItemList() {
        // Populate your item list here
        return null; // Return your actual item list
    }
}
