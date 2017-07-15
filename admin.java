package com.example.dattamber.sdhs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class admin extends AppCompatActivity {

    DataSnapshot snappy = null;
    private TextView mTextMessage;
    Query query;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<Card> list;
    List<Cardunp> list1;
    List<Donation> list2;
    RecyclerView recycle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.unpublished:
                    recycle = (RecyclerView) findViewById(R.id.recyc);
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("posts");
                    myRef.keepSynced(true);
                    Query q = database.getReference().child("images");
                    q.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            snappy = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    query = myRef.orderByChild("published").equalTo("false");
                    // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            list1 = new ArrayList<Cardunp>();
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                Cardunp value = dataSnapshot1.getValue(Cardunp.class);
                                Cardunp fire = new Cardunp();
                                String title = value.gettitle();
                                String content = value.getcontent();
                                String category = value.getcategory();
                                String team = value.getteam();
                                String date = value.getdate();
                                String filePath = value.getfilePath();
                                String published = value.getpublished();
                                fire.seturl(value.geturl());
                                Long pid = value.getpid();
                                if(snappy.child(filePath).child("thumbnail").getValue() != null) {
                                    fire.seturl(snappy.child(filePath).child("thumbnail").getValue().toString());
                                }
                                fire.setfilePath(filePath);
                                fire.settitle(title);
                                fire.setcategory(category);
                                fire.setcontent(content);
                                fire.setteam(team);
                                fire.setpid(pid);
                                fire.setdate(date);
                                fire.setpublished(published);
                                list1.add(fire);

                            }
                            Collections.reverse(list1);
                            RecyclerAdapterUnp recyclerAdapter = new RecyclerAdapterUnp(list1,admin.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("Hello", "Failed to read value.", error.toException());
                        }
                    });

                    return true;
                case R.id.donations:
                    recycle = (RecyclerView) findViewById(R.id.recyc);
                    database = FirebaseDatabase.getInstance();
                    myRef = (DatabaseReference) database.getReference("donations");
                    myRef.keepSynced(true);
                    query = myRef.orderByChild("date");
                    // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            list2 = new ArrayList<Donation>();
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                Donation value = dataSnapshot1.getValue(Donation.class);
                                Donation fire = new Donation();
                                String name = value.getname();
                                String phone = value.getphone();
                                String place = value.getplace();
                                String date = value.getdate();
                                String amount = value.getamount();
                                String type = value.gettype();
                                Long did = value.getdid();
                                String status = value.getstatus();
                                fire.setname(name);
                                fire.setphone(phone);
                                fire.setstatus(status);
                                fire.setplace(place);
                                fire.setdid(did);
                                fire.setdate(date);
                                fire.setamount(amount);
                                fire.settype(type);
                                //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                list2.add(fire);

                            }

                            RecyclerAdapterDonation recyclerAdapter = new RecyclerAdapterDonation(list2,admin.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("Hello", "Failed to read value.", error.toException());
                        }
                    });

                    return true;
                case R.id.logout:
                    SDHS gb = (SDHS) getApplicationContext();
                    gb.setMyString("false");
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recycle = (RecyclerView) findViewById(R.id.recyc);
        database = FirebaseDatabase.getInstance();
        myRef = (DatabaseReference) database.getReference("posts");
        myRef.keepSynced(true);
        Query q = database.getReference().child("images");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                snappy = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query = myRef.orderByChild("published").equalTo("false");
        // Toast.makeText(admin.this,"okay",Toast.LENGTH_SHORT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list1 = new ArrayList<Cardunp>();
                //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    Cardunp value = dataSnapshot1.getValue(Cardunp.class);
                    Cardunp fire = new Cardunp();
                    String title = value.gettitle();
                    String content = value.getcontent();
                    String category = value.getcategory();
                    String team = value.getteam();
                    String date = value.getdate();
                    String filePath = value.getfilePath();
                    String published = value.getpublished();
                    fire.seturl(value.geturl());
                    Long pid = value.getpid();
                    if(snappy.child(filePath).child("thumbnail").getValue() != null) {
                        fire.seturl(snappy.child(filePath).child("thumbnail").getValue().toString());
                    }
                    fire.setfilePath(filePath);
                    fire.settitle(title);
                    fire.setcategory(category);
                    fire.setcontent(content);
                    fire.setteam(team);
                    fire.setpid(pid);
                    fire.setdate(date);
                    fire.setpublished(published);
                    list1.add(fire);

                }
                Collections.reverse(list1);
                RecyclerAdapterUnp recyclerAdapter = new RecyclerAdapterUnp(list1,admin.this);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(admin.this,1);
                recycle.setLayoutManager(recyce);
                recycle.setItemAnimator( new DefaultItemAnimator());
                recycle.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
    }
     @Override
    public void onBackPressed()
     {
         Intent intent = new Intent(getApplicationContext(),MainActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(intent);
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.flashtext) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Update the flash text");

// Set up the input
            final String[] m_Text = new String[1];
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text[0] = input.getText().toString();
                    DatabaseReference mdatabase;
                    mdatabase = FirebaseDatabase.getInstance().getReference();
                    mdatabase.child("flash").setValue(m_Text[0]);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adminmain, menu);
        return true;
    }
}
