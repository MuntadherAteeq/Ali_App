package com.example.ali.system;

public class Transaction {
    private int id;
    private int uID;
    private String tName;
    private double tPrice;

    public Transaction(int id, int uID, String tName, double tPrice) {
        this.id = id;
        this.uID = uID;
        this.tName = tName;
        this.tPrice = tPrice;
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
