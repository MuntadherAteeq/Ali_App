package com.example.ali.system;

public class Deal {
    private int id;
    private String name;
    private int phone;
    private String date;
    private String road;
    private String building;
    private byte photo;
    private double total;

    public Deal(int id, String name, int phone, String date, String road, String building) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.road = road;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public byte getPhoto() {
        return photo;
    }

    public void setPhoto(byte photo) {
        this.photo = photo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
