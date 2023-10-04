package com.example.pedroresearch;

import static android.os.Environment.getExternalStorageDirectory;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pedroresearch.databinding.FragmentSecondBinding;
import com.example.pedroresearch.Specimen;

import java.util.ArrayList;
import java.util.Objects;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ArrayList<Specimen> listOfPeople;
    SharedViewModel viewModel;




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        listOfPeople = new ArrayList<>();
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class); //Shared ArrayLis

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listOfPeople = (ArrayList<Specimen>) DataStorageUtil.loadPeopleList(requireContext());

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect user input
                String name = Objects.requireNonNull(binding.nameTextInputLayout.getEditText()).getText().toString();
                String phone = Objects.requireNonNull(binding.phoneTextInput.getEditText()).getText().toString();
                String date = Objects.requireNonNull(binding.dateInput.getEditText()).getText().toString();
                Specimen.Date dateTaken = new Specimen.Date(date);

                // Ensure listOfPeople is initialized (possibly in onCreateView)
                if (listOfPeople == null) {
                    listOfPeople = new ArrayList<>();
                }

                // Create a new Specimen object and add it to the list
                Specimen specimen = new Specimen(name, phone, dateTaken);
                listOfPeople.add(specimen);

                // Save the updated list to the file
                DataStorageUtil.savePeopleList(requireContext(), listOfPeople);

                // Update the ViewModel with the new list of specimens
                viewModel.setElementList(listOfPeople);

                // Optionally, clear input fields or perform other actions
                clearInputFields();
            }
        });

        binding.changeStateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

    }

    // Helper method to clear input fields
    private void clearInputFields() {
        binding.nameTextInputLayout.getEditText().setText("");
        binding.phoneTextInput.getEditText().setText("");
        binding.dateInput.getEditText().setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    

}