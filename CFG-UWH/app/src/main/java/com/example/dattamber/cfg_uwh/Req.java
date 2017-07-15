package com.example.dattamber.cfg_uwh;

/**
 * Created by chandana on 7/15/2017.
 */

public class Req {
    public String bloodGroup;
    public Long donationId;
    public String institutionName;
    public Long numberOfUnits;
    public String location;
    public String contactNumber;
    public String contact2;
    public String lat;
    public String lon;
    public String stat;
    public String deadline;

    public String getStat() {
        return stat;
    }
    public  void  setStat(String stat)
    {
        this.stat=stat;
    }


    public String getbloodGroup() {
        return bloodGroup;
    }

    public void setbloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getdeadline() {
        return deadline;
    }

    public void setdeadline(String bloodGroup) {
        this.deadline = bloodGroup;
    }


    public String getlat() {
        return lat;
    }

    public void setlat(String lon) {
        this.lat = lon;
    }

    public String getlon() {
        return lon;
    }

    public void setlon(String lon) {
        this.lon = lon;
    }

    public Long getdonationId() {
        return donationId;
    }

    public void setdonationId(Long id) {
        this.donationId=id;
    }

    public String getinstitutionName() {
        return institutionName;
    }

    public void setinstitutionName(String institutionName) {
        this.institutionName=institutionName;
    }

    public Long getnumberOfUnits() {
        return numberOfUnits;
    }

    public void setnumberOfUnits(Long numberOfUnits) {
        this.numberOfUnits=numberOfUnits;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location=location;
    }

    public String contactNumber() {
        return contactNumber;
    }

    public void setcontactNumber(String contactNumber) {
        this.contactNumber=contactNumber;
    }
    public String getcontact2() {
        return contact2;
    }
    public void setcontact2(String contact2) {
        this.contact2=contact2;
    }

    public String contact2() {
        return contact2;
    }

    public void setrecipient(String contact2) {
        this.contact2=contact2;
    }
}
