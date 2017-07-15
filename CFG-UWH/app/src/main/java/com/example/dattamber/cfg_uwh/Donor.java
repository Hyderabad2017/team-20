package com.example.dattamber.cfg_uwh;
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
    public String dContact1;
    public String dContact2;
    public String dEmail;
    public String dLocation;
    public Long dLat;
    public Long dLong;
    public String dPublished;

    public Donor() {
        // Default constructor required for calls to DataSnapshot.getValue(Donor.class)
    }

    public Donor(Long did, String dname, String dbloodgroup, String dcontact1, String dcontact2,String demail, String dlocation) {
        this.dID = did;
        this.dName = dname;
        this.dBloodGroup = dbloodgroup;
        this.dContact1 = dcontact1;
        this.dContact2 = dcontact2;
        this.dEmail = demail;
        this.dLocation = dlocation;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dID", dID);
        result.put("dName", dName);
        result.put("dBloodGroup", dBloodGroup);
        result.put("dContact1",dContact1);
        result.put("dContact2",dContact2);
        result.put("dEmail",dEmail);
        result.put("dLocation",dLocation);
        return result;
    }

}