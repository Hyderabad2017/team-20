package com.example.dattamber.cfg_uwh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class donor_signup extends AppCompatActivity implements View.OnClickListener {
    EditText name,dat,location,contact1,contact2,password,email,bloodgroup;
    Button btn;
    FirebaseDatabase database;
    DatabaseReference dref,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_signup);
        name= (EditText) findViewById(R.id.name);
        dat= (EditText) findViewById(R.id.datOfBirth);
        location= (EditText) findViewById(R.id.location);
        contact1= (EditText) findViewById(R.id.contact1);
        contact2= (EditText) findViewById(R.id.contact2);
        password= (EditText) findViewById(R.id.password);
        database = FirebaseDatabase.getInstance();
        dref = database.getReference();
        email= (EditText) findViewById(R.id.email1);
        bloodgroup= (EditText) findViewById(R.id.bloodGroup);
        btn = (Button) findViewById(R.id.email_sign_in_button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btn)
        {
            final String dlocation,dcontact1,dcontact2,dpassword,demail,dbloodgroup,dname;
            dname = name.getText().toString();
            dlocation = location.getText().toString();
            dcontact1 = contact1.getText().toString();
            dcontact2 = contact2.getText().toString();
            demail = email.getText().toString();
            count = database.getReference("countd");
            dbloodgroup = bloodgroup.getText().toString();
            if(!dname.equals("") && !demail.equals("") && !dbloodgroup.equals("") && !dcontact1.equals("") && !dcontact2.equals("") && dcontact1.length() == 10 && dcontact2.length() == 10)
            {
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long coun1 = dataSnapshot.getValue(Long.class);
                        Donor donor = new Donor ( coun1,dname,dbloodgroup,dcontact1,dcontact2,demail,dlocation);
                        Map<String, Object> postValues = donor.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/users/user" + String.valueOf(fe), postValues);
                        coun1++;
                        final Long finalCoun = coun1;
                        dref.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                try {
                                    dref.child("count").setValue(finalCoun);
                                    Toast.makeText()
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(donor_signup.this, "User adding failed", Toast.LENGTH_LONG).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(donor_signup.this, "User adding failed", Toast.LENGTH_LONG).show();
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
