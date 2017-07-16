package com.example.dattamber.cfg_uwh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.dattamber.cfg_uwh.R.id.adminrecyc;

public class curract extends AppCompatActivity {
    RecyclerView adminrecyc;
    Button emer,all,addreq,cur;
    FirebaseDatabase database;
    DatabaseReference dref;
    String lat,lon,location,con1,con2,instName;
    Query query;
    List<Req> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curract);
        Bundle bd = getIntent().getExtras();
        instName = bd.getString("instName");
        getSupportActionBar().setTitle(instName);
        database = FirebaseDatabase.getInstance();
        dref = (DatabaseReference) database.getReference("requests");
        dref.keepSynced(true);
        adminrecyc = (RecyclerView) findViewById(R.id.currec);
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
                    if(value.getinstitutionName().equals(instName) && value.getStatus().equals("incomplete")) {
                        Req fire = new Req();
                        String name = value.getinstitutionName();
                        String bloodgroup = value.getbloodGroup();
                        String phone1 = value.contact2();
                        String deadline = value.getdeadline();
                        String phone = value.contactNumber();
                        String stat = value.getStat();
                        String status = value.getStatus();
                        String location = value.getlocation();
                        Long did = value.getdonationId();
                        fire.setinstitutionName(name);
                        fire.setcontactNumber(phone);
                        fire.setbloodGroup(bloodgroup);
                        fire.setStatus(status);
                        fire.setcontact2(phone1);
                        fire.setdeadline(deadline);
                        fire.setStat(stat);
                        fire.setlocation(location);
                        fire.setdonationId(did);
                        //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                        list1.add(fire);
                    }
                }
                    RecyclerAdapterHospi recyclerAdapter = new RecyclerAdapterHospi(list1, curract.this);
                    RecyclerView.LayoutManager recyce = new GridLayoutManager(curract.this, 1);
                    adminrecyc.setLayoutManager(recyce);
                    adminrecyc.setItemAnimator(new DefaultItemAnimator());
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
