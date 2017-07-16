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

public class DonorMain extends AppCompatActivity {
    RecyclerView donrecycler;
    Button emer,all,addreq,cur;
    FirebaseDatabase database;
    DatabaseReference dref;
    String lat,lon,location,con1,con2,instName;
    Query query;
    List<Req> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_main2);
        Bundle bd=getIntent().getExtras();
        donrecycler = (RecyclerView) findViewById(R.id.donrecyc);
        instName=bd.getString("instName");
        getSupportActionBar().setTitle(instName);
        emer = (Button) findViewById(R.id.donemerbtn);
        all = (Button) findViewById(R.id.donallbtas);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donrecycler = (RecyclerView) findViewById(R.id.donrecyc);
                database = FirebaseDatabase.getInstance();
                dref = (DatabaseReference) database.getReference("requests");
                dref.keepSynced(true);
                query = dref.orderByChild("dID");
                // Toast.makeText(DonorMain.this,"okay",Toast.LENGTH_SHORT);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        list1 = new ArrayList<Req>();
                        //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                            Req value = dataSnapshot1.getValue(Req.class);
                            if(value.getStatus().equals("incomplete")) {
                                Req fire = new Req();
                                String name = value.getinstitutionName();
                                String bloodgroup = value.getbloodGroup();
                                String phone1 = value.contact2();
                                String phone = value.contactNumber();
                                String location = value.getlocation();
                                String status = value.getStatus();
                                String stat = value.getStat();
                                String deadline = value.getdeadline();
                                Long did = value.getdonationId();
                                fire.setinstitutionName(name);
                                fire.setcontactNumber(phone);
                                fire.setStatus(status);
                                fire.setbloodGroup(bloodgroup);
                                fire.setcontact2(phone1);
                                fire.setlocation(location);
                                fire.setdonationId(did);
                                fire.setdeadline(deadline);
                                fire.setStat(stat);
                                //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                list1.add(fire);
                            }
                        }

                        RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,DonorMain.this);
                        RecyclerView.LayoutManager recyce = new GridLayoutManager(DonorMain.this,1);
                        donrecycler.setLayoutManager(recyce);
                        donrecycler.setItemAnimator( new DefaultItemAnimator());
                        donrecycler.setAdapter(recyclerAdapter);
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
                donrecycler = (RecyclerView) findViewById(R.id.donrecyc);
                database = FirebaseDatabase.getInstance();
                dref = (DatabaseReference) database.getReference("requests");
                dref.keepSynced(true);
                query = dref.orderByChild("dID");
                // Toast.makeText(DonorMain.this,"okay",Toast.LENGTH_SHORT);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        list1 = new ArrayList<Req>();
                        //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                            Req value = dataSnapshot1.getValue(Req.class);
                            if(value.getStat().equals("true") && value.getStatus().equals("incomplete")) {
                                Req fire = new Req();
                                String name = value.getinstitutionName();
                                String bloodgroup = value.getbloodGroup();
                                String phone1 = value.contact2();
                                String phone = value.contactNumber();
                                String location = value.getlocation();
                                String status = value.getStatus();
                                String stat = value.getStat();
                                String deadline = value.getdeadline();
                                Long did = value.getdonationId();
                                fire.setinstitutionName(name);
                                fire.setStatus(status);
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

                        RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,DonorMain.this);
                        RecyclerView.LayoutManager recyce = new GridLayoutManager(DonorMain.this,1);
                        donrecycler.setLayoutManager(recyce);
                        donrecycler.setItemAnimator( new DefaultItemAnimator());
                        donrecycler.setAdapter(recyclerAdapter);
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Hello", "Failed to read value.", error.toException());
                    }
                });
            }
        });
        donrecycler = (RecyclerView) findViewById(R.id.donrecyc);
        database = FirebaseDatabase.getInstance();
        dref = (DatabaseReference) database.getReference("requests");
        dref.keepSynced(true);
        query = dref.orderByChild("dID");
        // Toast.makeText(DonorMain.this,"okay",Toast.LENGTH_SHORT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list1 = new ArrayList<Req>();
                //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    Req value = dataSnapshot1.getValue(Req.class);
                    assert value != null;
                    if(value.getStatus().equals("incomplete")) {
                        Req fire = new Req();
                        String name = value.getinstitutionName();
                        String bloodgroup = value.getbloodGroup();
                        String phone1 = value.contact2();
                        String deadline = value.getdeadline();
                        String phone = value.contactNumber();
                        String status = value.getStatus();
                        String stat = value.getStat();
                        String location = value.getlocation();
                        Long did = value.getdonationId();
                        fire.setinstitutionName(name);
                        fire.setcontactNumber(phone);
                        fire.setbloodGroup(bloodgroup);
                        fire.setStatus(status);
                        fire.setcontact2(phone1);
                        fire.setStat(stat);
                        fire.setdeadline(deadline);
                        fire.setlocation(location);
                        fire.setdonationId(did);
                        //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                        list1.add(fire);
                    }
                }

                RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list1,DonorMain.this);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(DonorMain.this,1);
                donrecycler.setLayoutManager(recyce);
                donrecycler.setItemAnimator( new DefaultItemAnimator());
                donrecycler.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
        
    }
}
