package com.example.dattamber.cfg_uwh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.dattamber.cfg_uwh.R.id.donbtn;
import static com.example.dattamber.cfg_uwh.R.id.start;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    EditText email,pass;
    DatabaseReference dref;
    ProgressDialog pd;
    Button login, donor, insti;
    String lat,lon,location,con1,con2,instName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginbtn);
        pass = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.emailid);
        login.setOnClickListener(this);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dref= database.getReference("users");
        pd = new ProgressDialog(MainActivity.this);
        donor = (Button) findViewById(R.id.donbtn);
        donor.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if(v==login)
        {
            final String emailID,password;
            emailID = email.getText().toString();
            password = pass.getText().toString();
            if(emailID != "" && password != "") {
                pd.setMessage("Signing in");
                pd.show();
                mAuth.signInWithEmailAndPassword(emailID, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Toast.makeText(Signin.this, "Success", Toast.LENGTH_LONG).show();
                                    dref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            List<User> list;
                                            list = new ArrayList<User>();
                                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                            {
                                                User value = dataSnapshot1.getValue(User.class);
                                                if(value.getdtype().equals("user") && value.dEmail().equals(emailID))
                                                {
                                                    Intent intent = new Intent(getApplicationContext(),DonorMain.class);
                                                    Bundle bd=new Bundle();
                                                    lat=value.dlat();
                                                    lon=value.dlon();
                                                    location=value.dLocation();
                                                    con1=value.dContact1();
                                                    con2=value.dContact2();
                                                    instName=value.dName();
                                                    bd.putString("lat",lat);
                                                    bd.putString("lon",lon);
                                                    bd.putString("location",location);
                                                    bd.putString("con1",con1);
                                                    bd.putString("con2",con2);
                                                    bd.putString("instName",instName);
                                                    intent.putExtras(bd);
                                                    startActivity(intent);
                                                }
                                                else if(value.getdtype().equals("bloodbank") && value.dEmail().equals(emailID))
                                                {
                                                    Intent intent = new Intent(getApplicationContext(),Bloodbank.class);
                                                    Bundle bd=new Bundle();
                                                    lat=value.dlat();
                                                    lon=value.dlon();
                                                    location=value.dLocation();
                                                    con1=value.dContact1();
                                                    con2=value.dContact2();
                                                    instName=value.dName();
                                                    bd.putString("lat",lat);
                                                    bd.putString("lon",lon);
                                                    bd.putString("location",location);
                                                    bd.putString("con1",con1);
                                                    bd.putString("con2",con2);
                                                    bd.putString("instName",instName);
                                                    intent.putExtras(bd);
                                                    startActivity(intent);
                                                }
                                                else if(emailID.equals("admin@uwh.com"))
                                                {
                                                    Log.d("edf","sfds");
                                                    Intent intent = new Intent(getApplicationContext(), admin.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    pd.dismiss();
                                    //Toast.makeText(getApplicationContext(),globalVariable.getMyString(),Toast.LENGTH_LONG).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // [START_EXCLUDE]
                                if (!task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(MainActivity.this, "Attempt failed. Please try again.", Toast.LENGTH_LONG).show();
                                }
                                // [END_EXCLUDE]
                            }
                        });
            }
            else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == donor)
        {
            Intent intent = new Intent(this, checklist.class);
            startActivity(intent);

        }
        else if(v == insti)
        {
            Intent intent = new Intent(this, admin.class);
            startActivity(intent);
        }
    }
}
