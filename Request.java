package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber
 */

public class Request {

    public String bloodGroup;
    public Long donationId;
    public String institutionName;
    public Long numberOfUnits;
    public String location;
    public Long contactNumber;
    public String recipient;


    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(DonUp.class)
    }

    public Request(String bloodGroup, Long donationId, String institutionName, Long numberOfUnits, String location, Long contactNumber, String recipient) {
        this.bloodGroup = bloodGroup;
        this.donationId = donationId;
        this.institutionName = institutionName;
        this.numberOfUnits = numberOfUnits;
        this.location = location;
        this.contactNumber = contactNumber;
        this.recipient = recipient;
      }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("bloodGroup", bloodGroup);
        result.put("donationId", donationId);
        result.put("institutionName", institutionNamen);
        result.put("numberOfUnits", numberOfUnits);
        result.put("location",location);
        result.put("contactNumber",contactNumber);
        result.put("recipient",recipient);
        
        return result;
    }

}
