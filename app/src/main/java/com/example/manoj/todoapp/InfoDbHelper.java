package com.example.manoj.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.manoj.todoapp.InfoContract.DataEntry;


public class InfoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;
    public InfoDbHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Sqlcreate = "CREATE TABLE " + DataEntry.TABLE_NAME +"("
                +DataEntry._ID + " INTEGER PRIMARY KEY , "
                +DataEntry.COLUMN_TITLE + " TEXT NOT NULL"+");";
        db.execSQL(Sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
