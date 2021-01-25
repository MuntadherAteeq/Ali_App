package com.example.ali.system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private String deals_Table = "deals";
    private String transaction_Table = "transactions";



    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Database.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+deals_Table+" (id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT ,phone TEXT,date TEXT,total REAL,building TEXT,road TEXT ,image BLOG )");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+transaction_Table+" (id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT ,phone TEXT,date TEXT,price REAL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
