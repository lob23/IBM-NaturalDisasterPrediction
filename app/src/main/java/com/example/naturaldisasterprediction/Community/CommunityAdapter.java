package com.example.naturaldisasterprediction.Community;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturaldisasterprediction.databinding.ActivityCommunityItemBinding;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    private final List<CommunityItem> communityBlogList;

    public CommunityAdapter(List<CommunityItem> communityBlogList) {
        this.communityBlogList = communityBlogList;
        Log.d("CommunityAdapter", "CommunityAdapter: " + communityBlogList.get(0).getSenderId());
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityCommunityItemBinding communityItemBinding = ActivityCommunityItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CommunityViewHolder(communityItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.CommunityViewHolder holder, int position) {
        Log.d("CommunityAdapter", "onBindViewHolder: " + position);
        holder.setData(communityBlogList.get(position));
    }

    @Override
    public int getItemCount() {
        return communityBlogList.size();
    }

    static class CommunityViewHolder extends RecyclerView.ViewHolder {
        private final ActivityCommunityItemBinding binding;

        public CommunityViewHolder(ActivityCommunityItemBinding communityItemBinding) {
            super(communityItemBinding.getRoot());
            binding = communityItemBinding;
        }

        void setData(CommunityItem communityBlog) {
            // Need to fetch data from server
            binding.userName.setText(communityBlog.getSenderId());
            binding.userLocation.setText(communityBlog.getRegionId());
        }
    }
}
