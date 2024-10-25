package com.example.naturaldisasterprediction.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturaldisasterprediction.R;
import com.example.naturaldisasterprediction.Service.ItemModel;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<ItemModel> itemList;

    public ItemAdapter(List<ItemModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_ration, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Bind data to the views
        ItemModel item = itemList.get(position);
        holder.itemName.setText(item.getItem());
        holder.itemQuantity.setText(item.getQuantity());
        holder.itemDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class to hold references to views
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemDescription;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.quantity);
            itemDescription = itemView.findViewById(R.id.itemDescription);
        }
    }
}
