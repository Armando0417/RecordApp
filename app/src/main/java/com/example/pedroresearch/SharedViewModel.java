package com.example.pedroresearch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private ArrayList<Specimen> elementList = new ArrayList<>();
    private MutableLiveData<List<Specimen>> elementListLiveData = new MutableLiveData<>();

    public ArrayList<Specimen> getElementList() {
        return elementList;
    }

    public void setElementList(ArrayList<Specimen> list) {
        elementList = list;
        elementListLiveData.setValue(list); //Notify observers of the LiveData
    }

    public MutableLiveData<List<Specimen>> getElementListLiveData() {
        return elementListLiveData;
    }
}