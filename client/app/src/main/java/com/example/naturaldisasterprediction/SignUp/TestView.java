package com.example.naturaldisasterprediction.SignUp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturaldisasterprediction.MyAdapter;
import com.example.naturaldisasterprediction.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TestView extends AppCompatActivity {

   private MyAdapter myAdapter;
   private ArrayList<String> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        TextView btnAdd = findViewById(R.id.btn_add);
//
//        // Initialize list and add a default item
//        itemList = new ArrayList<>();
//        itemList.add("");  // Add one element to the list
//
//        myAdapter = new MyAdapter(this, itemList);
//
//        // Set up RecyclerView
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(myAdapter);
//
//        // Add button functionality
//        btnAdd.setOnClickListener(v -> {
//            myAdapter.addItem();
//        });
    }
}
