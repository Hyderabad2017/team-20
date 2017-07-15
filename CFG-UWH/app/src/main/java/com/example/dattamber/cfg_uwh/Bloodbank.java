package com.example.dattamber.cfg_uwh;

import android.content.Intent;
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

public class Bloodbank extends AppCompatActivity {
    RecyclerView adminrecyc;
    Button emer,all,addreq;
    FirebaseDatabase database;
    DatabaseReference dref;
    String lat,lon,location,con1,con2,instName;
    Query query;
    List<Req> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank);
        Bundle bd=getIntent().getExtras();
        lat=bd.getString("lat");
        lon=bd.getString("lon");
        location=bd.getString("location");
        con1=bd.getString("con1");
        con2=bd.getString("con2");
        instName=bd.getString("instName");


        addreq = (Button) findViewById(R.id.addreq);
        addreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), req_post.class);
                Bundle bd=new Bundle();
                bd.putString("lat",lat);
                bd.putString("lon",lon);
                bd.putString("location",location);
                bd.putString("con1",con1);
                bd.putString("con2",con2);
                bd.putString("instName",instName);
                intent.putExtras(bd);
                startActivity(intent);

            }
        });
        emer = (Button) findViewById(R.id.emerbtn);
        all = (Button) findViewById(R.id.allbtas);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminrecyc = (RecyclerView) findViewById(R.id.bloodrecyc);
                database = FirebaseDatabase.getInstance();
                dref = (DatabaseReference) database.getReference("requests");
                dref.keepSynced(true);
                query = dref.orderByChild("dID");
                // Toast.makeText(Bloodbank.this,"okay",Toast.LENGTH_SHORT);
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
                                String stat = value.getStat();
                                String deadline = value.getdeadline();
                                Long did = value.getdonationId();
                                fire.setinstitutionName(name);
                                fire.setcontactNumber(phone);
                                fire.setbloodGroup(bloodgroup);
                                fire.setcontact2(phone1);
                                fire.setlocation(location);
                                fire.setdonationId(did);
                                fire.setdeadline(deadline);
                                fire.setStat(stat);
                                //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                list1.add(fire);
                        }

                        RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,Bloodbank.this);
                        RecyclerView.LayoutManager recyce = new GridLayoutManager(Bloodbank.this,1);
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
        });
        emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminrecyc = (RecyclerView) findViewById(R.id.bloodrecyc);
                database = FirebaseDatabase.getInstance();
                dref = (DatabaseReference) database.getReference("requests");
                dref.keepSynced(true);
                query = dref.orderByChild("dID");
                // Toast.makeText(Bloodbank.this,"okay",Toast.LENGTH_SHORT);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        list1 = new ArrayList<Req>();
                        //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                            Req value = dataSnapshot1.getValue(Req.class);
                            if(value.getStat().equals("true")) {
                                Req fire = new Req();
                                String name = value.getinstitutionName();
                                String bloodgroup = value.getbloodGroup();
                                String phone1 = value.contact2();
                                String phone = value.contactNumber();
                                String location = value.getlocation();
                                String stat = value.getStat();
                                String deadline = value.getdeadline();
                                Long did = value.getdonationId();
                                fire.setinstitutionName(name);
                                fire.setcontactNumber(phone);
                                fire.setbloodGroup(bloodgroup);
                                fire.setcontact2(phone1);
                                fire.setlocation(location);
                                fire.setdeadline(deadline);
                                fire.setdonationId(did);
                                fire.setStat(stat);
                                //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                list1.add(fire);
                            }
                        }

                        RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,Bloodbank.this);
                        RecyclerView.LayoutManager recyce = new GridLayoutManager(Bloodbank.this,1);
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
        });
        adminrecyc = (RecyclerView) findViewById(R.id.bloodrecyc);
        database = FirebaseDatabase.getInstance();
        dref = (DatabaseReference) database.getReference("requests");
        dref.keepSynced(true);
        query = dref.orderByChild("dID");
        // Toast.makeText(Bloodbank.this,"okay",Toast.LENGTH_SHORT);
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

                RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,Bloodbank.this);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(Bloodbank.this,1);
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
