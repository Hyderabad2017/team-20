package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber on 19/06/2017.
 */

public class DonUp {

    public String phone;
    public String status;
    public Long id;
    public String name;
    public String place;
    public String date;
    public String type;
    public String amount;


    public DonUp() {
        // Default constructor required for calls to DataSnapshot.getValue(DonUp.class)
    }

    public DonUp(Long did, String phone, String name, String status, String amount, String place,String type, String date) {
        this.phone = phone;
        this.status = status;
        this.id = did;
        this.name = name;
        this.place = place;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("phone", phone);
        result.put("status", status);
        result.put("id", id);
        result.put("name", name);
        result.put("place",place);
        result.put("date",date);
        result.put("type",type);
        result.put("amount",amount);
        return result;
    }

}

