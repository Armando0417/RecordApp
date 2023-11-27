package com.example.pedroresearch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

public class dialogWindow extends DialogFragment {

    private int specimenId;
    private DataSourceManager dataSourceManager;
    private CardAdapter cardAdapter;

    // Constructor to receive dependencies
    public dialogWindow(DataSourceManager dataSourceManager, CardAdapter cardAdapter) {
        this.dataSourceManager = dataSourceManager;
        this.cardAdapter = cardAdapter;
    }

    // Setter method to set the specimen ID
    public void setSpecimenId(int specimenId) {
        this.specimenId = specimenId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Inflate the custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogwindow, null);
        builder.setView(view);

        // Set title
        TextView titleTextView = view.findViewById(R.id.dialogTitle);
        titleTextView.setText("Choose an action");

        // Set up the buttons
        Button editButton = view.findViewById(R.id.editButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                dismiss(); // Dismiss the dialog
                // Open the edit fragment, passing the specimen ID as an argument
                Bundle bundle = new Bundle();
                bundle.putInt("specimenId", specimenId);
                NavHostFragment.findNavController(dialogWindow.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click

                // Delete the specimen from the database
                dataSourceManager.deletePerson(specimenId);

                // Refresh the data in the RecyclerView
                cardAdapter.setData();

                dismiss(); // Dismiss the dialog



            }
        });

        return builder.create();
    }

}
