package com.example.pedroresearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedroresearch.databinding.FragmentFirstBinding;
import com.example.pedroresearch.DataSourceManager; // Import your DataSourceManager
import com.example.pedroresearch.Specimen; // Import your Specimen class
import com.example.pedroresearch.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements CardAdapter.OnCardClickListener {
    private FragmentFirstBinding binding;
    private SharedViewModel viewModel;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private DataSourceManager dataSourceManager; // Create an instance of DataSourceManager

    private ArrayList<Specimen> listToShow;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Load data from the database using DataSourceManager
            dataSourceManager = new DataSourceManager(requireContext());

        // Initialize the CardAdapter with your data
        cardAdapter = new CardAdapter(getContext(), dataSourceManager, this);
        recyclerView.setAdapter(cardAdapter);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.clearListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the list in the database using DataSourceManager
                dataSourceManager.clearPeopleList();
                cardAdapter.setData();
            }
        });

        binding.sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sort the list in the database using DataSourceManager
                dataSourceManager.sortPeopleByName();
                cardAdapter.setData();
            }
        });

        binding.sortByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sort the list in the database by ID using DataSourceManager
                dataSourceManager.sortPeopleByID();
                cardAdapter.setData();
            }
        });
    }

    public void onCardClicked(int specimenId) {
        // Show the dialog fragment
        dialogWindow dialogFragment = new dialogWindow(dataSourceManager, cardAdapter);
        dialogFragment.setSpecimenId(specimenId);
        dialogFragment.show(getChildFragmentManager(), "EditDeleteDialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
