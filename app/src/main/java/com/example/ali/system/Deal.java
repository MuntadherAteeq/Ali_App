package com.example.ali.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deal {
    private int id;
    private String name;
    private String phone;
    private String date;
    private String road;
    private String building;
    private byte image;
    private double total;
    private boolean active;

    public Deal(){
        this.building=null;
        this.road=null;
        this.active=true;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.date = dateFormat.format(date);
        this.total = 0;

    }



    public Deal(int id, String name, String phone, String date, String road, String building ,double total,boolean active) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.road = road;
        this.building = building;
        this.active=active;
        this.total = total;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setActive(int Zero_or_One) { active= Zero_or_One == 1; }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }
    public String getName() { return name; }
    public void setName(String name) {
          this.name=name;

    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone=phone;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
       this.date=date;
    }
    public String getRoad() {
        return road;
    }
    public void setRoad(String road) {
       this.road=road;
    }
    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building=building;
    }
    public byte getImage() {
        return image;
    }
    public void setImage(byte image) {
        this.image=image;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}
