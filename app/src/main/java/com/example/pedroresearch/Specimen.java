package com.example.pedroresearch;
import java.io.*;
import java.util.Comparator;

public class Specimen {

    public static class SpecimenIDComparator implements Comparator<Specimen> {
        @Override
        public int compare(Specimen specimen1, Specimen specimen2) {
            // Compare specimens based on their IDs
            return Integer.compare(specimen1.getID(), specimen2.getID());
        }

    }

    public static class SpecimenNameComparator implements Comparator<Specimen>{
        @Override
        public int compare(Specimen specimen1, Specimen specimen2) {
            // Compare specimens based on their names (alphabetically)
            return specimen1.name.compareTo(specimen2.name);

        }
    }



    public static class Date{
        private int day;
        private int month;
        private int year;

        public Date(int day, int month, int year){
            this.day = day;
            this.month = month;
            this.year = year;
        }


        // Default constructor
        public Date() {
            this.day = -1;
            this.month = -1;
            this.year = -1;
        }

        public Date(String date) {
            String[] parts = date.split("/");
            if (parts.length == 3) {
                try {
                    this.day = Integer.parseInt(parts[0]);
                    this.month = Integer.parseInt(parts[1]);
                    this.year = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    // Handle parsing errors here, such as invalid input
                    this.day = -1;
                    this.month = -1;
                    this.year = -1;
                }
            } else {
                // Handle incorrect format (e.g., "DD/MM/YYYY" expected)
                this.day = -1;
                this.month = -1;
                this.year = -1;
            }
        }



        public String getDate(){
            return Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        }

        public void showDate(){
            System.out.println( day + "/" + month + "/" + year );
        }
    }

    public String name;
    public String phone;
    public Date dateTaken;

    private int ID;

    Specimen(String n, String phone, Date date){
        this.name = n;
        this.phone = phone;
        this.dateTaken = date;
        this.ID = -1;
    }

    Specimen() {
        this.name = "";
        this.phone = "";
        this.dateTaken = null;
        this.ID = -1;
    }

    String WhoIsThis(){
        return name + phone + dateTaken.getDate();
    }

    void printSpecimen(){
        System.out.println( "this is: {" + name + ", " + phone + ", " + dateTaken.getDate() + " }" );
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int id){
        this.ID = id;
    }






}


