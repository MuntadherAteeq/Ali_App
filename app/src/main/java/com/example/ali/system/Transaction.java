package com.example.ali.system;

public class Transaction {
    private int id;
    private int uID;
    private String tName;
    private double tPrice;
    private String date;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Transaction(){
    }

    public Transaction(int id, int uID, String tName, double tPrice,String time,String date) {
        this.id = id;
        this.uID = uID;
        this.tName = tName;
        this.tPrice = tPrice;
        this.time = time;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public double gettPrice() {
        return tPrice;
    }

    public void settPrice(double tPrice) {
        this.tPrice = tPrice;
    }
}
