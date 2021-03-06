package com.example.ali.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private String deals_Table = "deals";
    private String transaction_Table = "transactions";
    private String name="name";
    private String id = "id";
    private String phone="phone";
    private String date="date";
    private String price="price";
    private String total="total";
    private String building="building";
    private String road="road";
    private String image = "image";
    private String active = "active";
    private String uid = "uID";
    private ArrayList<Transaction> transactions;
    private ArrayList<Deal> deals;
    private Deal deal;
    Transaction tran;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_h:mm a");



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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean insert_New_Transaction(Transaction tran) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        Date now = new Date();
        tran.setDate(dateFormat.format(now));

        cv.put(name,tran.gettName());
        cv.put(date,tran.getDate());
        cv.put(price,tran.gettPrice());
        cv.put(uid,tran.getuID());

        long insert = db.insert(transaction_Table, null, cv);
        return insert != -1;

    }

    public boolean deleteDealData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result1 = db.delete(deals_Table, "id=?",new String[]{id});
        long result2 = db.delete(transaction_Table, "uID=?",new String[]{id});

        return result1 != -1 && result2 != -1;
    }


    public boolean updateDealData(Deal deal){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(id,deal.getId());
        cv.put(name,deal.getName());
        cv.put(phone,deal.getPhone());
        cv.put(date,deal.getDate());
        cv.put(building,deal.getBuilding());
        cv.put(road,deal.getRoad());
        cv.put(image,deal.getImage());
        cv.put(active,deal.isActive());
        cv.put(total,deal.getTotal());
        long result = db.update(deals_Table, cv, "id=?", new String[]{String.valueOf(deal.getId())});
        return result != -1;

    }

    public boolean deleteTransactionData(Transaction tran){
        String id =String.valueOf(tran.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(transaction_Table, "id=?",new String[]{id});
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
    public ArrayList<Transaction> readAllTransactions(String uID){
        String query = "SELECT * FROM " + transaction_Table +" WHERE "+ uid +" = "+ uID ;
        SQLiteDatabase db = this.getReadableDatabase();
        transactions = new ArrayList<>();
        String date = "";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {

            tran = new Transaction();
            tran.setId(cursor.getInt(0));
            tran.setuID(cursor.getInt(1));
            tran.settName(cursor.getString(2));
            String string = cursor.getString(3);
            if (string != null && string.contains("_")){
                    String[] parts = string.split("_");
                    tran.setTime(parts[1]);
                    tran.setDate(parts[0]);
            }else {
                    tran.setTime("");
                    tran.setDate("");
                }
            tran.settPrice(cursor.getDouble(4));

            if (!tran.getDate().equals(date)){
                date = tran.getDate();


                Transaction tr = new Transaction(0,0,"",0.0,tran.getTime(),tran.getDate());
                transactions.add(tr);
            }

            transactions.add(tran);
        }
        cursor.close();
        return transactions;

        }
    public double getTotalSum(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + deals_Table +" WHERE "+ active +" = 0" ;
        Cursor cursor = db.rawQuery(query,null);
        double sum=0;
        while (cursor.moveToNext()) {
            sum+=cursor.getDouble(4);
        }
        query = "SELECT * FROM " + transaction_Table +" WHERE "+ uid +" = 0" ;
        cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            sum+=cursor.getDouble(4);
        }
        cursor.close();
        return sum;
    }

    public Deal readDealOwnerByID(String id){
        String query = "SELECT * FROM " + deals_Table +" WHERE id = "+ id ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null ){
            while (cursor.moveToNext()) {
                deal = new Deal();
                deal.setId(cursor.getInt(0));
                deal.setName(cursor.getString(1));
                deal.setPhone(cursor.getString(2));
                deal.setDate(cursor.getString(3));
                deal.setTotal(cursor.getDouble(4));
                deal.setBuilding(cursor.getString(5));
                deal.setRoad(cursor.getString(6));
                deal.setActive(cursor.getInt(8));
            }
        }else {
            return null;
        }

        cursor.close();
        return deal;

    }
    public ArrayList<Deal> readAllUnactiveDeals(){
        String query = "SELECT * FROM " + deals_Table +" WHERE "+ active +" = "+ 0 +" ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        deals = new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            deal = new Deal();
            deal.setId(cursor.getInt(0));
            deal.setName(cursor.getString(1));
            deal.setPhone(cursor.getString(2));
            deal.setDate(cursor.getString(3));
            deal.setTotal(cursor.getDouble(4));
            deal.setBuilding(cursor.getString(5));
            deal.setRoad(cursor.getString(6));
            deal.setActive(cursor.getInt(8));
            deals.add(deal);
        }
        cursor.close();
        return deals;

    }

    public ArrayList<Deal> readAllActiveDeals(){
        String query = "SELECT * FROM " + deals_Table +" WHERE "+ active +" = "+ 1 +" ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        deals = new ArrayList<>();

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            deal = new Deal();
            deal.setId(cursor.getInt(0));
            deal.setName(cursor.getString(1));
            deal.setPhone(cursor.getString(2));
            deal.setDate(cursor.getString(3));
            deal.setTotal(cursor.getDouble(4));
            deal.setBuilding(cursor.getString(5));
            deal.setRoad(cursor.getString(6));
            deal.setActive(cursor.getInt(8));
            deals.add(deal);
        }
        cursor.close();
        return deals;

    }

    public ArrayList<Transaction> readAllPocketTransaction(){
        Date d = new Date();
        

        String query = "SELECT * FROM " + transaction_Table +" WHERE "+ uid +" = 0" ;
        SQLiteDatabase db = this.getReadableDatabase();
        transactions = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            tran = new Transaction();
            tran.setId(cursor.getInt(0));
            tran.setuID(cursor.getInt(1));
            tran.settName(cursor.getString(2));
            String string = cursor.getString(3);
            if (string != null && string.contains("_")){
                String[] parts = string.split("_");
                tran.setTime(parts[1]);
                tran.setDate(parts[0]);
            }else {
                tran.setTime("");
                tran.setDate("");
            }
            tran.settPrice(cursor.getDouble(4));
            transactions.add(tran);
        }
        cursor.close();
        return transactions;

    }

    }

