package com.example.dattamber.cfg_uwh;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chandana on 7/15/2017.
 */


public class Inst {


    public Long dID;
    public String dName;
    public String dContact1;
    public String  dContact2;
    public String dEmail;
    public String dLocation;
    public String dlat;
    public String dlon;

    public Inst() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Inst(Long iid, String iname, String icontact1, String icontact2, String iemail, String ilocation) {
        this.dID = iid;
        this.dName = iname;
        this.dContact1 = icontact1;
        this.dContact2 = icontact2;
        this.dEmail = iemail;
        this.dLocation = ilocation;
        this.dlat = "54654";
        this.dlon = "554654";
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
