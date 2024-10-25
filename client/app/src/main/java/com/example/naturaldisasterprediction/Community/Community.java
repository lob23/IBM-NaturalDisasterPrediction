package com.example.naturaldisasterprediction.Community;

import android.os.Bundle;

import com.example.naturaldisasterprediction.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturaldisasterprediction.databinding.ActivityCommunityBinding;

import java.util.ArrayList;
import java.util.List;

public class Community extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCommunityBinding binding;

    private List<CommunityItem> communityBlogList;
    private CommunityAdapter communityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCommunityBinding.inflate(getLayoutInflater());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.getRoot());

        init();

//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_community);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_community);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void init() {
        // Replace by fetching data from server
        communityBlogList = new ArrayList<>();
        communityBlogList.add(new CommunityItem("1", "5498723212213", "1", "Hello"));
        communityBlogList.add(new CommunityItem("2", "2", "2", "Hi"));
        communityBlogList.add(new CommunityItem("3", "3", "3", "Hey"));

        communityAdapter = new CommunityAdapter(communityBlogList);

        binding.communityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.communityRecyclerView.setAdapter(communityAdapter);
    }
}