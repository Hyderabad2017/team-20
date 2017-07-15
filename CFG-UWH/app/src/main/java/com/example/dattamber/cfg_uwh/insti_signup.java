package com.example.dattamber.cfg_uwh;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import static com.example.dattamber.cfg_uwh.R.id.contact1;
import static com.example.dattamber.cfg_uwh.R.id.contact2;
import static com.example.dattamber.cfg_uwh.R.id.location;

public class insti_signup extends AppCompatActivity implements View.OnClickListener {
    EditText bname,bcontact1,bcontact2,bemail,blocation,bpass,bpassaa;
    Button bsub;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog pd;
    DatabaseReference dref,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insti_signup);
        bname = (EditText) findViewById(R.id.bname);
        bcontact1 = (EditText) findViewById(R.id.bcontact1);
        bcontact2 = (EditText) findViewById(R.id.bcontact2);
        bemail = (EditText) findViewById(R.id.bemail);
        blocation = (EditText) findViewById(R.id.blocation);
        bpass = (EditText) findViewById(R.id.bpass);
        bpassaa = (EditText) findViewById(R.id.bpassaa);
        bsub = (Button) findViewById(R.id.bsignup);
        bsub.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        pd = new ProgressDialog(this);
        dref = database.getReference();
        count = database.getReference("countd");
    }

    @Override
    public void onClick(View v) {
        if(v == bsub)
        {
            final String dlocation,dcontact1,dcontact2,dpassword,demail,dbloodgroup,dname,dpassaa;
            dname = bname.getText().toString();
            dlocation = blocation.getText().toString();
            dcontact1 = bcontact1.getText().toString();
            dcontact2 = bcontact2.getText().toString();
            demail = bemail.getText().toString();
            count = database.getReference("countd");
            dpassword = bpass.getText().toString();
            dpassaa = bpassaa.getText().toString();
            if(!dname.equals("") && !demail.equals("") && dpassaa.equals(dpassword) && !dcontact1.equals("") && !dcontact2.equals("") && dcontact1.length() == 10 && dcontact2.length() == 10)
            {
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long coun1 = dataSnapshot.getValue(Long.class);
                        Inst inst = new Inst ( coun1,dname,dcontact1,dcontact2,demail,dlocation);
                        Map<String, Object> postValues = inst.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/users/user" + String.valueOf(coun1), postValues);
                        coun1++;
                        final Long finalCoun = coun1;
                        mAuth.createUserWithEmailAndPassword(demail, dpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Log.d("Sdsd","sadasfga");
                                Toast.makeText(getApplicationContext(),"Successfully added",Toast.LENGTH_LONG).show();
                            }
                        });

                        dref.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                try {
                                    dref.child("countd").setValue(finalCoun);
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(insti_signup.this, "Adding failed", Toast.LENGTH_LONG).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(insti_signup.this, "Adding failed", Toast.LENGTH_LONG).show();
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
