package com.binhtranphuoc97.sqlitedemo;

import java.io.Serializable;

/**
 * Created by BinhTran on 10/18/2017.
 */

public class Contact implements Serializable {
    private int id;
    private String name;
    private String number;
    private String address;
    private String date;
    private String time;
    private String gender;

    public Contact() {
    }

    public Contact(int id,String name, String number, String address, String date, String time, String gender) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.date = date;
        this.time = time;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }
    public  void setName(String name) {this.name = name;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public  String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

}
