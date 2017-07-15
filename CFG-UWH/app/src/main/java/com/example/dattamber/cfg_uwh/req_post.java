package com.example.dattamber.cfg_uwh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class req_post extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText deadline,noofunits,plocation,contactno;
    String instname,con1,con2,loc,lat1,lon1,dead;
    Spinner bloodGroup;
    CheckBox emer;
    Button btn;
    String blood="";
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference dref,count;
    ProgressDialog pd;
    String stat="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_req_post);
        deadline= (EditText) findViewById(R.id.deadline);
        noofunits= (EditText) findViewById(R.id.numunits);
        bloodGroup = (Spinner) findViewById(R.id.spinner);
        emer = (CheckBox) findViewById(R.id.emer);
        database = FirebaseDatabase.getInstance();
        dref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        String lat,lon,location,instName;

        btn = (Button) findViewById(R.id.send);
        Bundle bd=getIntent().getExtras();
        lat1=bd.getString("lat");
        lon1=bd.getString("lon");
        loc=bd.getString("location");
        con1=bd.getString("con1");
        con2=bd.getString("con2");
        instname=bd.getString("instName");

        List<String> bloodGroups = new ArrayList<String>();
        bloodGroups.add("A+");
        bloodGroups.add("A-");
        bloodGroups.add("A1+");
        bloodGroups.add("A1-");
        bloodGroups.add("A1B+");
        bloodGroups.add("A1B-");
        bloodGroups.add("A2+");
        bloodGroups.add("A2-");
        bloodGroups.add("A2B+");
        bloodGroups.add("A2B-");
        bloodGroups.add("AB+");
        bloodGroups.add("AB-");
        bloodGroups.add("B+");
        bloodGroups.add("B-");
        bloodGroups.add("Bombay blood group");
        bloodGroups.add("INRA");
        bloodGroups.add("O+");
        bloodGroups.add("O-");
        bloodGroup.setPrompt("Select Blood group");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroups);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(dataAdapter);
        bloodGroup.setOnItemSelectedListener(this);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(final View v) {
        if(v == btn)
        {
            final String institutionName,numberOfUnits,location,contactNumber,contact2,lat,lon,dbloodgroup;
            institutionName = instname;
            numberOfUnits = noofunits.getText().toString();
            contactNumber = con1;
            contact2 = con2;
            dead = deadline.getText().toString();
            location = loc;
            lat = lat1;
            lon = lon1;
            count = database.getReference("countr");

            dbloodgroup = blood;

            if(!institutionName.equals("") && !numberOfUnits.equals("") && !contactNumber.equals("") && !location.equals(""))
            {
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long coun1 = dataSnapshot.getValue(Long.class);

                        Request request = new Request (dbloodgroup,coun1,institutionName,Long.valueOf(numberOfUnits),location,contactNumber,contact2,lat,lon,stat,dead);

                        Map<String, Object> postValues = request.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/requests/request" + String.valueOf(coun1), postValues);
                        coun1++;
                        final Long finalCoun = coun1;


                        dref.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                try {
                                    dref.child("countr").setValue(finalCoun);
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(),"Request Added Successfully",Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(v.getContext(), "Request adding failed", Toast.LENGTH_LONG).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(v.getContext(), "Request adding failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
    }

    public void emerfun(View view)
    {
        boolean checked=((CheckBox) view).isChecked();
        if(checked)
        {
            stat="true";

        }
        else
        {
            stat="false";
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        blood = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
