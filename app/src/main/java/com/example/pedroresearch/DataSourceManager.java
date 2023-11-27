package com.example.pedroresearch;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;



public class DataSourceManager {
    private SQLiteDatabase database;
    private DataStorageUtil dbHelper;
    private MutableLiveData<List<Specimen>> peopleLiveData = new MutableLiveData<>();

    private ArrayList<Specimen> listOfPeople;

    public LiveData<List<Specimen>> getAllPeopleLiveData() {
        loadPeopleData(); // Load data initially
        return peopleLiveData;
    }

    private void loadPeopleData() {
        List<Specimen> people = getAllPeople();
        peopleLiveData.setValue(people);
    }
    public DataSourceManager(Context context) {
        dbHelper = new DataStorageUtil(context);
        database = dbHelper.getWritableDatabase();
        refreshList();
    }


    public long addPerson(Specimen person) {
        ContentValues values = new ContentValues();
        values.put("Name", person.getName());
        values.put("Phone", person.getPhone());
        values.put("Date", person.getDate());

        return database.insert("People", null, values);
    }

    public List<Specimen> getAllPeople() {
//        List<Specimen> people = new ArrayList<>();
//        Cursor cursor = database.query("People", null, null, null, null, null, null);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                if(cursor.getColumnIndex("Name") != -1) {
//                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
//                    @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("Phone"));
//                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));
//
//                    Specimen specimen = new Specimen(name, phone, new Specimen.Date(date));
//                    people.add(specimen);
//                }
//            }
//
//            cursor.close();
//        }
//        return people;
        return listOfPeople;
    }

    public int updatePerson(int personID, Specimen updatedPerson) {
        ContentValues values = new ContentValues();
        values.put("Name", updatedPerson.getName());
        values.put("Phone", updatedPerson.getPhone());
        values.put("Date", updatedPerson.getDate());

        int rowsAffected = database.update("People", values, "ID = ?", new String[]{String.valueOf(personID)});
        database.close();

        return rowsAffected;
    }


    public int deletePerson(int personID) {
        int rowsDeleted = database.delete("People", "ID = ?", new String[]{String.valueOf(personID)});

        refreshList();
        database.close();
        return rowsDeleted;
    }


    public void sortPeopleByName() {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        String query = "SELECT * FROM People ORDER BY Name ASC";
        Cursor cursor = database.rawQuery(query, null);

        List<Specimen> sortedPeople = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("Phone"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));
                Specimen specimen = new Specimen(name, phone, new Specimen.Date(date));
                sortedPeople.add(specimen);
            }
            cursor.close();
        }

        // Log statements to check if sorting is occurring
        for (Specimen specimen : sortedPeople) {
            Log.d("SortByName", "Sorted Specimen: " + specimen.getName());
        }

        // Update the list of people with the sorted data
        peopleLiveData.setValue(sortedPeople);
        setDatabaseList((ArrayList<Specimen>) sortedPeople);
    }

    public void setDatabaseList(ArrayList<Specimen> newList){
        listOfPeople = newList;
    }


    public void sortPeopleByID() {
        if (!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }

        String query = "SELECT * FROM People ORDER BY ID ASC";
        Cursor cursor = database.rawQuery(query, null);

        List<Specimen> sortedPeople = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("Phone"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));
                Specimen specimen = new Specimen(name, phone, new Specimen.Date(date));
                sortedPeople.add(specimen);
            }
            cursor.close();
        }

        // Log statements to check if sorting is occurring
        for (Specimen specimen : sortedPeople) {
            Log.d("SortByID", "Sorted Specimen: " + specimen.getName());
        }

        // Update the list of people with the sorted data
        peopleLiveData.setValue(sortedPeople);
        setDatabaseList((ArrayList<Specimen>) sortedPeople);
    }

    public void clearPeopleList() {
        database.delete("People", null, null);
        listOfPeople.clear();
    }

    @SuppressLint("Range")
    public int getSpecimenId(Specimen specimen) {
        int specimenId = -1; // Default value if not found

        // Construct a SQL query to find the ID based on the attributes that uniquely identify a Specimen
        String query = "SELECT ID FROM People WHERE Name = ? AND Phone = ? AND Date = ?";
        String[] selectionArgs = {specimen.getName(), specimen.getPhone(), specimen.getDate()};

        Cursor cursor = database.rawQuery(query, selectionArgs);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                specimenId = cursor.getInt(cursor.getColumnIndex("ID"));
            }
            cursor.close();
        }

        return specimenId;
    }

    private void refreshList() {
            List<Specimen> people = new ArrayList<>();
            Cursor cursor = database.query("People", null, null, null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnIndex("Name") != -1) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                        @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("Phone"));
                        @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));

                        Specimen specimen = new Specimen(name, phone, new Specimen.Date(date));
                        people.add(specimen);
                    }
                }
            }

            listOfPeople = (ArrayList<Specimen>) people;
        }

}
