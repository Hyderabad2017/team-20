package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;


public class Institute {
    public String iID;
    public String iName;
    public String iEmail;
    public String iContact1;
    public String iContact2;
    public String iLocation;
    public String iLat;
    public String iLong;
    public String idate;
    public String dPublished;
    public String iDID;
    
    public String getiID() {
        return iID;
    }

    public void setiID(String iID) {
        this.iID = iID;
    }   

    public String getiName() {
        return iName;
    }

    public void setiName(String name) {
        this.iName=name;
    }

    public String getiEmail() {
        return iEmail;
    }

    public void setiEmail(String email) {
        this.iEmail=email; 
    }

    public String getIContact1() {
        return iContact1;
    }

    public void setiContact1(String iContact1) {
        this.iContact1=iContact1;
    }

    public String getIContact2() {
        return iContact2;
    }

    public void setiContact2(String iContact2) {
        this.iContact2=iContact2;
    }

    public String getLocation() {
        return location;
    }

    public String setiLocation(String iLocation) {
        this.iLocation=iLocation;
    }

    public String getiLat() {
        return iLat;
    }

    public void setiLat(String iLat) {
        this.iLat=iLat;
    }

    public String getiLong() {
        return iLong;
    }

    public void setiLong(String iLong) {
        this.iLong=iLong;
    }

    public String getiDate() {
        return idate;
    }

    public void setiDate(String date) {
        this.idate=date;
    }

    public String getdPublished() {
        return dPublished;
    }

    public void setdPublished(String dPublished) {
        this.dPublished=dPublished;
    }

    public String getiDID() {
        return iDID;
    }

    public void setIDID(String iDID) {
        this.iDID=iDID;
    }
}

