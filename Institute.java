package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;


public class Institute {
    public Long dID;
    public String dName;
    public String dEmail;
    public String dContact1;
    public String dContact2;
    public String dLocation;
    public String dlat;
    public String dlon;
    public String dtype;

    public Long getiID() {
        return dID;
    }

    public void setiID(Long dID) {
        this.dID = dID;
    }   

    public String getiName() {
        return dName;
    }

    public void setiName(String name) {
        this.dName=name;
    }

    public String getiEmail() {
        return dEmail;
    }

    public void setiEmail(String email) {
        this.dEmail=email; 
    }

    public String getIContact1() {
        return dContact1;
    }

    public void setiContact1(String dContact1) {
        this.dContact1=dContact1;
    }

    public String getIContact2() {
        return dContact2;
    }

    public void setiContact2(String dContact2) {
        this.dContact2=dContact2;
    }

    public String getLocation() {
        return dLocation;
    }

    public String setiLocation(String iLocation) {
        this.dlocation=iLocation;
    }

    public String getiLat() {
        return dlat;
    }

    public void setiLat(String iLat) {
        this.dlat=dlat;
    }

    public String getiLong() {
        return dlon;
    }

    public void setiLong(String iLong) {
        this.dlon=dlon;
    }

    public String getdtype() {
        return dtype;
    }

    public void setdtype(String dtype) {
        this.dtype=dtype;
    }
}

