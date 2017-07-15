package com.example.dattamber.cfg_uwh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;
import static android.R.attr.value;

public class admin extends AppCompatActivity implements View.OnClickListener {
    RecyclerView adminrecyc;
    Button user,dons,banks;
    FirebaseDatabase database;
    DatabaseReference dref;
    Query query;
    List<User> list;
    List<Req> list1;
    List<Institute> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        adminrecyc = (RecyclerView) findViewById(R.id.adminrecyc);
        user = (Button) findViewById(R.id.useradmin);
        dons = (Button) findViewById(R.id.donsadmin);
        banks = (Button) findViewById(R.id.banksadmin);
        user.setOnClickListener(this);
        banks.setOnClickListener(this);
        dons.setOnClickListener(this);

        adminrecyc = (RecyclerView) findViewById(R.id.adminrecyc);
        database = FirebaseDatabase.getInstance();
        dref = (DatabaseReference) database.getReference("requests");
        dref.keepSynced(true);
        query = dref.orderByChild("dID");
        // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list1 = new ArrayList<Req>();
                //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    Req value = dataSnapshot1.getValue(Req.class);
                    Req fire = new Req();
                    String name = value.getinstitutionName();
                    String bloodgroup = value.getbloodGroup();
                    String phone1 = value.contact2();
                    String deadline = value.getdeadline();
                    String phone = value.contactNumber();
                    String location = value.getlocation();
                    Long did = value.getdonationId();
                    fire.setinstitutionName(name);
                    fire.setcontactNumber(phone);
                    fire.setbloodGroup(bloodgroup);
                    fire.setcontact2(phone1);
                    fire.setdeadline(deadline);
                    fire.setlocation(location);
                    fire.setdonationId(did);
                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                    list1.add(fire);
                }

                RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,admin.this);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                adminrecyc.setLayoutManager(recyce);
                adminrecyc.setItemAnimator( new DefaultItemAnimator());
                adminrecyc.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == user)
        {
            adminrecyc = (RecyclerView) findViewById(R.id.adminrecyc);
            database = FirebaseDatabase.getInstance();
            dref = (DatabaseReference) database.getReference("users");
            dref.keepSynced(true);
            query = dref.orderByChild("dID");
            // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    list = new ArrayList<User>();
                    //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        User value = dataSnapshot1.getValue(User.class);
                        if(value.getdtype().equals("user")) {
                            User fire = new User();
                            String name = value.dName();
                            String phone = value.dContact1();
                            String phone1 = value.dContact2();
                            String bloodgroup = value.getdBloodGroup();
                            String location = value.dLocation();
                            String email = value.dEmail();
                            Log.d("emails ",value.dEmail());
                            Long did = value.dID();
                            fire.setdName(name);
                            fire.setdContact1(phone);
                            fire.setdEmail(email);
                            fire.setdContact2(phone1);
                            fire.setdLocation(location);
                            fire.setdID(did);
                            fire.setdBloodGroup(bloodgroup);
                            //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                            list.add(fire);
                        }
                    }

                    RecyclerAdapterUser recyclerAdapter = new RecyclerAdapterUser(list,admin.this);
                    RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                    adminrecyc.setLayoutManager(recyce);
                    adminrecyc.setItemAnimator( new DefaultItemAnimator());
                    adminrecyc.setAdapter(recyclerAdapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        }
        else if(v == banks)
        {
            adminrecyc = (RecyclerView) findViewById(R.id.adminrecyc);
            database = FirebaseDatabase.getInstance();
            dref = (DatabaseReference) database.getReference("users");
            dref.keepSynced(true);
            query = dref.orderByChild("dID");
            // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    list2 = new ArrayList<Institute>();
                    //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        Institute value = dataSnapshot1.getValue(Institute.class);
                        if(value.getdtype().equals("bloodbank")) {
                            Institute fire = new Institute();
                            String name = value.getiName();
                            String phone = value.getIContact1();
                            String phone1 = value.getIContact2();
                            String location = value.getLocation();
                            Long did = value.getiID();
                            fire.setiName(name);
                            fire.seticontact1(phone);
                            fire.seticontact2(phone1);
                            fire.setilocation(location);
                            fire.setiID(did);
                            //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                            list2.add(fire);
                        }
                    }

                    RecyclerAdapterInstitute recyclerAdapter = new RecyclerAdapterInstitute(list2,admin.this);
                    RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                    adminrecyc.setLayoutManager(recyce);
                    adminrecyc.setItemAnimator( new DefaultItemAnimator());
                    adminrecyc.setAdapter(recyclerAdapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });
        }
        else if(v == dons)
        {
            adminrecyc = (RecyclerView) findViewById(R.id.adminrecyc);
            database = FirebaseDatabase.getInstance();
            dref = (DatabaseReference) database.getReference("requests");
            dref.keepSynced(true);
            query = dref.orderByChild("dID");
            // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    list1 = new ArrayList<Req>();
                    //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        Req value = dataSnapshot1.getValue(Req.class);
                            Req fire = new Req();
                            String name = value.getinstitutionName();
                            String bloodgroup = value.getbloodGroup();
                            String phone1 = value.contact2();
                            String phone = value.contactNumber();
                            String location = value.getlocation();
                            Long did = value.getdonationId();
                            fire.setinstitutionName(name);
                            fire.setcontactNumber(phone);
                            fire.setbloodGroup(bloodgroup);
                            fire.setcontact2(phone1);
                            fire.setlocation(location);
                            fire.setdonationId(did);
                            //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                            list1.add(fire);
                    }

                    RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,admin.this);
                    RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                    adminrecyc.setLayoutManager(recyce);
                    adminrecyc.setItemAnimator( new DefaultItemAnimator());
                    adminrecyc.setAdapter(recyclerAdapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });
        }
    }
}
