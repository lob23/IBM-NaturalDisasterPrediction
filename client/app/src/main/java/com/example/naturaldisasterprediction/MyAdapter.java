package com.example.naturaldisasterprediction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturaldisasterprediction.SignUp.User;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> itemList;  // Use User class directly
    LocalDate localDate;
    private FragmentManager fragmentManager;

    public MyAdapter(Context context, ArrayList<User> itemList, FragmentManager fragmentManager) {
        this.context = context;
        this.itemList = itemList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_family, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User member = itemList.get(position);

        // Update the item title based on its position (1-based index)
        String title = "Member " + (position + 1);
        holder.title.setText(title);

        // Set initial height and weight values
        holder.heightValue.setText(String.format("%.0f cm", member.getHeight()));
        holder.weightValue.setText(String.format("%.0f kg", member.getWeight()));

        // Listen for changes in the height slider and update the member's height
        holder.sliderHeight.addOnChangeListener((slider, value, fromUser) -> {
            holder.heightValue.setText(String.format("%.0f cm", value));
            member.setHeight(value);  // Update the height in the User object
        });

        // Listen for changes in the weight slider and update the member's weight
        holder.sliderWeight.addOnChangeListener((slider, value, fromUser) -> {
            holder.weightValue.setText(String.format("%.0f kg", value));
            member.setWeight(value);  // Update the weight in the User object
        });

        // Delete button functionality
        holder.btnDelete.setOnClickListener(v -> {
            // Remove the item at the current position
            itemList.remove(position);

            // Notify the adapter of the item removal and refresh the RecyclerView
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
        });

        setDOB_Gender(holder, member);
    }

    private void setDOB_Gender(@NonNull MyViewHolder holder, User member) {

        holder.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date(selection));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.getDefault());
                        localDate = LocalDate.parse(date, formatter);
                        holder.dob.setText(date);
                        member.setBirth(localDate);
                    }
                });
                materialDatePicker.show(fragmentManager, "tag");
            }
        });

        holder.gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] genderOption = {"Male", "Female"};
                final int[] selectedOption = {-1};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Gender");
                builder.setSingleChoiceItems(genderOption, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedOption[0] = which;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedOption[0] != -1) {
                            holder.gender.setText(genderOption[selectedOption[0]]);
                            if(genderOption[selectedOption[0]] == "Male"){
                                member.setGender(1);
                            } else if(genderOption[selectedOption[0]] == "Female"){
                                member.setGender(2);
                            }

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

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, heightValue, weightValue;
        TextView dob, gender;
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
            dob = itemView.findViewById(R.id.adapterDOB);
            gender = itemView.findViewById(R.id.adapterGender);

        }
    }

    // Add new User to the list
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addItem() {
        itemList.add(new User());  // Add a new User with default values
        notifyItemInserted(itemList.size() - 1);
    }

    // Get the current list of family members (User objects)
    public ArrayList<User> getItemList() {
        return itemList;
    }
}
