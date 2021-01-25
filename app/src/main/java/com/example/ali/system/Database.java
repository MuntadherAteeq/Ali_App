package com.example.ali.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private String deals_Table = "deals";
    private String transaction_Table = "transactions";
    private String name="name";
    private String phone="phone";
    private String date="date";
    private String price="price";
    private String total="total";
    private String building="building";
    private String road="road";
    private String image = "image";
    private String active = "active";
    private String uid = "uID";


    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + deals_Table + " (id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT ,phone TEXT,date TEXT,total REAL,building TEXT,road TEXT ,image BLOG,active BOOLEAN NOT NULL CHECK (active IN (0,1)) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + transaction_Table + " (id INTEGER PRIMARY KEY AUTOINCREMENT ,uID INT,name TEXT,date TEXT,price REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean insert_New_Deal(Deal deal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(name,deal.getName());
        cv.put(phone,deal.getName());
        cv.put(date,deal.getName());
        cv.put(building,deal.getName());
        cv.put(road,deal.getName());
        long insert = db.insert(deals_Table, null, cv);
        return insert != -1;

    }
    public Boolean insert_New_Transaction(Transaction tran) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(name,tran.gettName());
        cv.put(date,tran.getDate());
        cv.put(price,tran.gettPrice());
        cv.put(uid,tran.getuID());

        long insert = db.insert(deals_Table, null, cv);
        return insert != -1;

    }

    boolean updateDealData(String id,Deal deal){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(name,deal.getName());
        cv.put(phone,deal.getName());
        cv.put(date,deal.getName());
        cv.put(building,deal.getName());
        cv.put(road,deal.getName());
        if (deal.isActive())cv.put(active,"1");else cv.put(active,"0");
        long result = db.update(deals_Table, cv, "_id=?", new String[]{id});
        return result != -1;

    }

}
