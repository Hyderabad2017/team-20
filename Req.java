package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;


public class Req {
    public String bloodGroup;
    public Long donationId;
    public String institutionName;
    public Long numberOfUnits;
    public String location;
    public Long contactNumber;
    public String recipient;
    
    public String getbloodGroup() {
        return bloodGroup;
    }

    public void setbloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
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
        return getnumberOfUnits;
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

    public Long contactNumber() {
        return contactNumber;
    }    

    public void setcontactNumber(Long contactNumber) {
        this.contactNumber=contactNumber;
    } 

    public String recipient() {
        return recipient;
    }

    public void setrecipient(String recipient) {
        this.recipient=recipient;
    }
}

