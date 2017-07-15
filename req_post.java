package com.example.dattamber.cfg_uwh;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Request_Post extends AppCompatActivity implements View.OnClickListener {
    EditText name,noofunits,plocation,contactno;
    Spinner bloodGroup;
    Button btn;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference dref,count;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        
        name= (EditText) findViewById(R.id.name);
        noofunits= (EditText) findViewById(R.id.noofunits);
        location= (EditText) findViewById(R.id.location);
        contactno= (EditText) findViewById(R.id.contactno);
        
        database = FirebaseDatabase.getInstance();
        dref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        btn = (Button) findViewById(R.id.post_request_button);

        btn.setOnClickListener(this);
    }
    

    @Override
    public void onClick(View v) {
        if(v == btn)
        {
            final String institutionName,numberOfUnits,location;
            institutionName = name.getText().toString();
            numberOfUnits = noofunits.getText().toString();
            contactNumber = contactno.getText().toString();
            location = plocation.getText().toString();

           
            count = database.getReference("countr");

            dbloodgroup = bloodgroup.getText().toString();



            if(!institutionName.equals("") && !numberOfUnits.equals("") && !contactNumber.equals("") && !location.equals(""))
            {
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long coun1 = dataSnapshot.getValue(Long.class);

                        Request request = new Request (String bloodGroup, Long donationId, String institutionName, Long numberOfUnits, String location, Long contactNumber, String recipient);
                        
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
                                    Toast.makeText(activity_main.this, "Request adding failed", Toast.LENGTH_LONG).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(activity_main.this, "Request adding failed", Toast.LENGTH_LONG).show();
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
}
