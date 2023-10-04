package com.example.pedroresearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>  {
    private ArrayList<Specimen> dataList;

    public CardAdapter(ArrayList<Specimen> dataList) {
        this.dataList = dataList;
    }

    public CardAdapter() {
        // Initialize dataList with hard-coded data
        dataList = new ArrayList<>();
        dataList.add(new Specimen("John Doe", "555-1234", new Specimen.Date("08/09/2023")));
        dataList.add(new Specimen("Jane Smith", "555-5678", new Specimen.Date("08/09/2023")));
        // Add more items as needed
    }


    //Should it be able to

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Specimen specimen = dataList.get(position);

        // Bind data to the views in the card_item layout
        holder.nameTextView.setText(specimen.name);
        holder.phoneTextView.setText(specimen.phone);
        holder.dateTextView.setText(specimen.dateTaken.getDate());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
