package com.example.dattamber.cfg_uwh;

/**
 * Created by chandana on 7/16/2017.
 */

import java.util.HashMap;
import java.util.Map;

import static com.example.dattamber.cfg_uwh.R.id.deadline;

/**
 * Created by Dattamber
 */

public class Request {

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

    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(DonUp.class)
    }

    public Request(String bloodGroup, Long donationId, String institutionName, Long numberOfUnits, String location, String contactNumber, String contact2, String lat, String lon,String stat,String deadline) {
        this.bloodGroup = bloodGroup;
        this.donationId = donationId;
        this.institutionName = institutionName;
        this.numberOfUnits = numberOfUnits;
        this.location = location;
        this.contactNumber = contactNumber;
        this.contact2 = contact2;
        this.lat = lat;
        this.lon = lon;
        this.stat=stat;
        this.deadline = deadline;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("bloodGroup", bloodGroup);
        result.put("donationId", donationId);
        result.put("institutionName", institutionName);
        result.put("numberOfUnits", numberOfUnits);
        result.put("location",location);
        result.put("contactNumber",contactNumber);
        result.put("contact2",contact2);
        result.put("lat",lat);
        result.put("lon",lon);
        result.put("stat",stat);
        result.put("donor","blaaa");
        result.put("status","incomplete");
        result.put("deadline",deadline);
        return result;
    }

}