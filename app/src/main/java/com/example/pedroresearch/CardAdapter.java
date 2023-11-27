package com.example.pedroresearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>  {
    public interface OnCardClickListener {
        void onCardClicked(int specimenId);
    }
    private ArrayList<Specimen> dataList;
    private DataSourceManager dataBase;

    private OnCardClickListener cardClickListener;
    private Context context; // Add a Context field

    public CardAdapter(ArrayList<Specimen> dataList) {
        this.dataList = dataList;
    }

    public CardAdapter(Context context, DataSourceManager dataSourceManager, OnCardClickListener listener) {
        this.dataBase = dataSourceManager;
        this.context = context;
        this.cardClickListener = listener;
        this.dataList = (ArrayList<Specimen>) dataSourceManager.getAllPeople();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Specimen specimen = dataList.get(position);

        holder.nameTextView.setText(specimen.getName());
        holder.phoneTextView.setText(specimen.getPhone());
        holder.dateTextView.setText(specimen.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int specimenId = dataBase.getSpecimenId(specimen);
                cardClickListener.onCardClicked(specimenId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData() {
        dataList = (ArrayList<Specimen>) dataBase.getAllPeople();
        notifyDataSetChanged();
    }

    public void setData(List<Specimen> newData) {
        dataList.clear(); // Clear the existing data
        dataList.addAll(newData); // Add the new data
        notifyDataSetChanged(); // Notify the adapter that the data has changed
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
