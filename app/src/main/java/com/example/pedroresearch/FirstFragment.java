package com.example.pedroresearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedroresearch.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Specimen> listOfPeople;
    private SharedViewModel viewModel;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout using DataBindingUtil
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//      View view = inflater.inflate(R.layout.fragment_first, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set the layout manager (e.g., LinearLayoutManager)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the viewModel before creating the CardAdapter
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

//        Log.d("FirstFragment", "List size: " + viewModel.getElementList().size());

        listOfPeople = (ArrayList<Specimen>) DataStorageUtil.loadPeopleList(requireContext());


        // Initialize the CardAdapter with your data
        cardAdapter = new CardAdapter(listOfPeople);
        //cardAdapter = new CardAdapter();


        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(cardAdapter);


        // Add an observer to the elementList LiveData
        viewModel.getElementListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Specimen>>() {
            @Override
            public void onChanged(List<Specimen> newList) {
                // Update the RecyclerView adapter with the new data
                cardAdapter.notifyDataSetChanged();
            }
        });

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
                DataStorageUtil.clearPeopleList(requireContext());
                cardAdapter = new CardAdapter(listOfPeople);
            }

        });

        binding.sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfPeople.sort(new Specimen.SpecimenNameComparator());
                DataStorageUtil.clearPeopleList(requireContext());
                DataStorageUtil.savePeopleList(requireContext(), listOfPeople);
            }
        });

        binding.sortByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfPeople.sort(new Specimen.SpecimenIDComparator());
//                DataStorageUtil.clearPeopleList(requireContext());
//                DataStorageUtil.savePeopleList(requireContext(), listOfPeople);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

