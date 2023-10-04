package com.example.pedroresearch;

import static android.os.Environment.getExternalStorageDirectory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class DataStorageUtil {
    private static final String FILE_NAME = "people_list.txt";
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String LAST_ID_KEY = "last_id";

    private static int IDCounter = 1;

    private static class IdentificationCard<K,V>{
        private K ID;
        private V Specimen;
        

        public IdentificationCard(K key, V value) {
            this.ID = key;
            this.Specimen = value;
        }

        public K getKey() {return ID;}
        public V getValue() {return Specimen;}

        public String identification(){
            return "ID = " + ID + " { " + Specimen.
        }

    }

    public static void savePeopleList(Context context, List<>peopleList){
        File storageDirectory = context.getFilesDir();

        File file = new File(storageDirectory, FILE_NAME);

        try(){




        }
        catch



    }


    public static void savePeopleList(Context context, List<Specimen> peopleList) {
        File storageDirectory = context.getFilesDir(); // Get the app's private files directory

        // Create a File object for your specific directory and file name
        File file = new File(storageDirectory, FILE_NAME);
        Log.d("FileCreation", "File path: " + file.getAbsolutePath());

        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {

            // Create a counter for generating IDs
//               int idCounter = this.IDCounter; // You can start from 1 or any desired initial value

            for (Specimen person : peopleList) {
                // Assign an ID if it's a new person (ID is -1)
                if (person.getID() == -1) {
                    person.setID(IDCounter);
                    IDCounter++;
                }

                // Format the person's data and write it to the file
                String personData = person.getID() + "," + person.name + "," + person.phone + "," + person.dateTaken;
                writer.write(personData);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Specimen> loadPeopleList(Context context) {
        List<Specimen> peopleList = new ArrayList<>();

        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into parts and create a Specimen object
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    String phone = parts[2];
                    String date = parts[3];
                    Specimen specimen = new Specimen(name, phone, new Specimen.Date(date));
                    peopleList.add(specimen);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return peopleList;
    }

    public static void clearPeopleList(Context context) {
        File storageDirectory = context.getFilesDir(); // Get the app's private files directory
        File file = new File(storageDirectory, FILE_NAME);

        if (file.exists()) {
            file.delete(); // Delete the existing file
            IDCounter = 0;
        }
    }

    private static int getLastAssignedId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(LAST_ID_KEY, 0);
    }
    private static void saveLastAssignedId(Context context, int lastId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(LAST_ID_KEY, lastId);
        editor.apply();
    }



}

