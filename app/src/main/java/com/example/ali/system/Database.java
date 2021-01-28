package com.example.ali.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


    public Database(@Nullable Context context) {
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
        cv.put(phone,deal.getPhone());
        cv.put(date,deal.getDate());
        cv.put(building,deal.getBuilding());
        cv.put(road,deal.getRoad());
        cv.put(image,deal.getImage());
        cv.put(active,deal.isActive());
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
        cv.put(phone,deal.getPhone());
        cv.put(date,deal.getDate());
        cv.put(building,deal.getBuilding());
        cv.put(road,deal.getRoad());
        cv.put(image,deal.getImage());
        cv.put(active,deal.isActive());
        long result = db.update(deals_Table, cv, "_id=?", new String[]{id});
        return result != -1;

    }
    public Cursor readAllDeals(){
        String query = "SELECT * FROM " + deals_Table+" ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        if(cursor.getCount() == 0){
             cursor = null;
            return cursor;
        }else{
            return cursor;
        }

    }

}
