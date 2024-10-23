package com.example.naturaldisasterprediction;// com.example.naturaldisasterprediction.MyAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> itemList;

    public MyAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_family, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Update the item title based on its position (1-based index)
        String title = "Member " + (position + 1);
        holder.title.setText(title);

        // Set initial height and weight values
        holder.heightValue.setText("0 cm");
        holder.weightValue.setText("0 kg");

        // Listen for changes in the height slider and update the height value TextView
        holder.sliderHeight.addOnChangeListener((slider, value, fromUser) -> {
            holder.heightValue.setText(String.format("%.0f cm", value));
        });

        // Listen for changes in the weight slider and update the weight value TextView
        holder.sliderWeight.addOnChangeListener((slider, value, fromUser) -> {
            holder.weightValue.setText(String.format("%.0f kg", value));
        });

        // Delete button functionality
        holder.btnDelete.setOnClickListener(v -> {
            // Remove the item at the current position
            itemList.remove(position);

            // Notify the adapter of the item removal and refresh the RecyclerView
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());  // Refresh the list after removal
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, heightValue, weightValue;
        Slider sliderHeight, sliderWeight;
        ImageView btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            heightValue = itemView.findViewById(R.id.heightValue);
            weightValue = itemView.findViewById(R.id.weightValue);
            sliderHeight = itemView.findViewById(R.id.sliderHeight);
            sliderWeight = itemView.findViewById(R.id.sliderWeight);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    // Add new item to the list
    public void addItem() {
        itemList.add("");  // Add empty data for a new item
        notifyItemInserted(itemList.size() - 1);  // Notify adapter of the new item
    }
}
