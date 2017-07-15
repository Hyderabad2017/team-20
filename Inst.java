package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber on 17/06/2017.
 */

public class Inst {


    public Long dID; 
    public String dName;
    public Long dContact1;
    public Long dContact2;
    public String dEmail;
    public String dLocation;
    public Long dlat;
    public Long dlon;
    
    public Inst() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Inst(Long iid, String iname, Long icontact1, Long icontact2, String iemail, String ipassword, String ilocation, Long ilat, Long ilong) {
        this.dID = iid;
        this.dName = iname;
        this.dContact1 = icontact1;
        this.dContact2 = icontact2;
        this.dEmail = iemail;
        this.dLocation = ilocation;
        this.dlat = ilat;
        this.dlon = ilong;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dID", dID);
        result.put("dName", dName);
        result.put("dContact1",dContact1);
        result.put("dContact2",dContact2);
        result.put("dEmail",dEmail);
        result.put("dtype","bloodbank");
        result.put("dLocation",dLocation);
        result.put("dlat","789");
        result.put("dlon","546");
        return result;
    }

}

