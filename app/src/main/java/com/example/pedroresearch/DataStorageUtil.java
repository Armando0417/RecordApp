package com.example.pedroresearch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataStorageUtil extends SQLiteOpenHelper   {
    private static final String DATABASE_NAME = "MyAppDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DataStorageUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS People (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Phone TEXT, Date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
