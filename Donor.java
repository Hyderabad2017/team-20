package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber on 17/06/2017.
 */

public class Donor {


    public Long dID; 
    public String dName;
    public String dBloodGroup;
    public Long dWeight;
    public Long dContact1;
    public Long dContact2;
    public String dEmail;
    public String dLocation;
    public Long dLat;
    public Long dLong;
    public String dPublished;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(Long did, String dname, String dbloodgroup, Long dweight, Long dcontact1, Long dcontact2,String demail, String dlocation,Long dlat, Long dlong,String dpublished) {
        this.dID = did;
        this.dName = dname;
        this.dBloodGroup = dbloodgroup;
        this.dWeight = dweight;
        this.dContact1 = dcontact1;
        this.dContact2 = dcontact2;
        this.dEmail = demail;
        this.dLocation = dlocation;
        this.dLat = dlat;
        this.dLong = dlong;
        this.dPublished = dpublished;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dID", dID);
        result.put("dName", dName);
        result.put("dBloodGroup", dBloodGroup);
        result.put("dWeight", dWeight);
        result.put("dContact1",dContact1);
        result.put("dContact2",dContact2);
        result.put("dEmail",dEmail);
        result.put("dLocation",dLocation);
        result.put("dLat",dLat);
        result.put("dLong",dLong);
        result.put("dPublished",dPublished);
        return result;
    }

}

