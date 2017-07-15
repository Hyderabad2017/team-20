package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber on 17/06/2017.
 */

public class Inst {


    public Long iID; 
    public String iName;
    public String iLocation;
    public Long iContact1;
    public Long iContact2;
    public String iEmail;
    public Long iLat;
    public Long iLong;
    public String iDate;
    public String iPublished;
    public Long iDID;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(Long iid, String iname, String ilocation, Long icontact1, Long icontact2,String iemail, Long ilat, Long ilong, String idate, String ipublished, Long idid) {
        this.iID = iid;
        this.iName = iname;
        this.iLocation = ilocation;
        this.iContact1 = icontact1;
        this.iContact2 = icontact2;
        this.iEmail = iemail;
        this.iLat = ilat;
        this.iLong = ilong;
        this.iDate = idate;
        this.iPublished = ipublished;
        this.iDID = idid;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("iID", dID);
        result.put("iName", dName);
        result.put("iLocation",iLocation);
        result.put("iContact1",iContact1);
        result.put("iContact2",iContact2);
        result.put("iEmail",iEmail);
        result.put("iLat",iLat);
        result.put("iLong",iLong);
        result.put("iDate",iDate);
        result.put("dPublished",dPublished);
        result.put("iDID",iDID);
        return result;
    }

}

