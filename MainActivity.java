package com.example.dattamber.sdhs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {

    private static final String TAG = "MainActivity";
    FirebaseDatabase database;
    DatabaseReference myRef, imki, likref ;
    List<Card> list;
    final DataSnapshot[] sanppy = new DataSnapshot[1];
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager recyce;
    RecyclerView recycle;
    String str;
    final String[] string = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().subscribeToTopic("sdhs");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Token rarey" + token);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView mark = (TextView)  findViewById(R.id.marquee);
        final String[] string = new String[1];
        mark.setSelected(true);
        mark.setLinksClickable(true);
        list = new ArrayList<Card>();
        myRef = FirebaseDatabase.getInstance().getReference("flash");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                string[0] = dataSnapshot.getValue(String.class);
                mark.setText(string[0]);
                str = string[0];
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
        SDHS gb = new SDHS();
        NavigationView nv = (NavigationView)findViewById(R.id.nav_view);
        Menu mn = nv.getMenu();
        if(gb.getMyString().equals("true"))
        {
            mn.findItem(R.id.adminport).setVisible(true);
            mn.findItem(R.id.logout).setVisible(true);
        }
        else
        {
            mn.findItem(R.id.adminport).setVisible(false);
            mn.findItem(R.id.logout).setVisible(false);
        }
        FirebaseApp.initializeApp(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sanppy[0] = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        imki = FirebaseDatabase.getInstance().getReference("images");
        recycle = (RecyclerView) findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");
        myRef.keepSynced(true);
        likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
        Query query = imki.orderByChild("id");
        Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Query quer = myRef.orderByChild("id");
                Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sanppy[0] = dataSnapshot;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                quer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                        list = new ArrayList<Card>();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                            Card value = dataSnapshot1.getValue(Card.class);
                            assert value != null;
                            if (value.getpublished().equals("true")) {
                                Card fire = new Card();
                                String title = value.gettitle();
                                String content = value.getcontent();
                                String category = value.getcategory();
                                String team = value.getteam();
                                String url = value.geturl();
                                String date = value.getdate();
                                String filePath = value.getfilePath();
                                String published = value.getpublished();
                                Long pid = value.getpid();
                                Map<String, Long> likes = new HashMap<String, Long>();
                                Long likeCount = Long.valueOf(0);
                                assert sanppy[0] != null;
                                if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                    likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                    likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                }
                                fire.seturl(url);
                                fire.settitle(title);
                                fire.setlikeCount(likeCount);
                                fire.setlikeslist(likes);
                                fire.setcategory(category);
                                if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                    fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                    fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                }
                                fire.setcontent(content);
                                fire.setfilePath(filePath);
                                fire.setteam(team);
                                fire.setpid(pid);
                                fire.setdate(date);
                                fire.setpublished(published);
                                //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                list.add(fire);
                            }
                        }
                        Collections.reverse(list);

                        recycle.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                        ((SimpleItemAnimator) recycle.getItemAnimator()).setSupportsChangeAnimations(false);
                        if(recycle.getAdapter() == null)
                        {
                            recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            recycle.setAdapter(recyclerAdapter);
                        }
                        else
                        {
                            ((RecyclerAdapter)recycle.getAdapter()).updateData(list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void flashclick(View view)
    {
        final SpannableString s = new SpannableString(str);
        Linkify.addLinks(s, Linkify.ALL);

        final AlertDialog d = new AlertDialog.Builder(view.getContext())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(R.drawable.logo)
                .setMessage( s )
                .create();

        d.show();

        // Make the textview clickable. Must be called after show()
        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.refresh)
        {
            Log.d("Lolaha","Lolalsya");
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
            myRef.keepSynced(true);
            likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            list = new ArrayList<Card>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = 0L;
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                        likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                        likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    }
                                    fire.seturl(url);
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);

                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.all) {
            // Handle the camera action
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = (DatabaseReference) database.getReference("posts");
            myRef.keepSynced(true);
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            list = new ArrayList<Card>();

                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = 0L;
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                        likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                        likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    }
                                    fire.seturl(url);
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);

                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        } else if (id == R.id.teams) {
            Intent intent = new Intent(this,Teams1.class);
            startActivity(intent);
        } else if (id == R.id.social) {
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
            myRef.keepSynced(true);
            likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            list = new ArrayList<Card>();

                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true") && value.getcategory().equals("Social")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = 0L;
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                        likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                        likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    }
                                    fire.seturl(url);
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);
                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        } else if (id == R.id.spiritual) {
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
            myRef.keepSynced(true);
            likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            list = new ArrayList<Card>();

                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true") && value.getcategory().equals("Spiritual")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = 0L;
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                        likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                        likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    }
                                    fire.seturl(url);
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);
                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        } else if (id == R.id.annual) {
                Intent intent = new Intent(this,Annuals.class);
            startActivity(intent);
        } else if (id == R.id.charity) {
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
            myRef.keepSynced(true);
            likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            list = new ArrayList<Card>();

                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true") && value.getcategory().equals("Charity")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = 0L;
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                        likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                        likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    }
                                    fire.seturl(url);
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);
                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        } else if (id == R.id.medical) {
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
            myRef.keepSynced(true);
            likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this,"okay",Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            list = new ArrayList<Card>();

                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true") && value.getcategory().equals("Medical")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = 0L;
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    if(sanppy[0].hasChild("post"+String.valueOf(pid))) {
                                        likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                        likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    }
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                        fire.seturl(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);
                            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,MainActivity.this);
                            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this,1);
                            recycle.setLayoutManager(recyce);
                            recycle.setItemAnimator( new DefaultItemAnimator());
                            recycle.setAdapter(recyclerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        } else if (id == R.id.adminport) {
            SDHS gb = (SDHS) getApplicationContext();
            //Toast.makeText(this,gb.getMyString(),Toast.LENGTH_LONG).show();
            if(gb.getMyString() == "true") {

                Intent intent = new Intent(this, admin.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"Please login as admin",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.logout) {
            SDHS gb = (SDHS) getApplicationContext();
            gb.setMyString("false");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        else  if (id == R.id.appaji)
        {
            Intent intent = new Intent(this,Appaji.class);
            startActivity(intent);
        }
        else  if (id == R.id.anacts)
        {
            Intent intent = new Intent(this,VolActs.class);
            startActivity(intent);
        }
        else  if (id == R.id.aboutus)
        {
            Intent intent = new Intent(this,Aboutus.class);
            startActivity(intent);
        }
        else if(id == R.id.contact)
        {
            Intent intent = new Intent(this,Contact.class);
            startActivity(intent);
        }
        else if(id == R.id.donat)
        {
            Intent intent = new Intent(this,donate.class);
            startActivity(intent);
        }
        else if(id == R.id.sign)
        {
            Intent intent = new Intent(this,Signin.class);
            startActivity(intent);
        }
        else if(id == R.id.support)
        {
            Intent intet = new Intent(this,Support.class);
            startActivity(intet);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void donact(View view)
    {
        Intent intent = new Intent(this,donate.class);
        startActivity(intent);
    }

    public void signinact(View view)
    {
        Intent intent = new Intent(this,Signin.class);
        startActivity(intent);
    }

    public void conact(View view)
    {
        Intent intent = new Intent(this,Contact.class);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.refresh) {
            Log.d("Lololol","lolol");
            imki = FirebaseDatabase.getInstance().getReference("images");
            recycle = (RecyclerView) findViewById(R.id.recycle);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("posts");
            myRef.keepSynced(true);
            likref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            Query query = imki.orderByChild("id");
            Toast.makeText(MainActivity.this, "okay", Toast.LENGTH_SHORT);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Query quer = myRef.orderByChild("id");
                    final DataSnapshot[] sanppy = new DataSnapshot[1];
                    Query q = FirebaseDatabase.getInstance().getReference().child("/postlikes");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            sanppy[0] = dataSnapshot;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    quer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            //Toast.makeText(MainActivity.this,"entered",Toast.LENGTH_SHORT);
                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Card value = dataSnapshot1.getValue(Card.class);
                                assert value != null;
                                if (value.getpublished().equals("true")) {
                                    Card fire = new Card();
                                    String title = value.gettitle();
                                    String content = value.getcontent();
                                    String category = value.getcategory();
                                    String team = value.getteam();
                                    String url = value.geturl();
                                    String date = value.getdate();
                                    String filePath = value.getfilePath();
                                    String published = value.getpublished();
                                    Long pid = value.getpid();
                                    Long likeCount = sanppy[0].child("post" + String.valueOf(pid)).child("likeCount").getValue(Long.class);
                                    Map<String, Long> likes = new HashMap<String, Long>();
                                    likes = (Map<String, Long>) sanppy[0].child("post" + String.valueOf(pid)).child("likes").getValue();
                                    fire.seturl(url);
                                    fire.settitle(title);
                                    fire.setlikeCount(likeCount);
                                    fire.setlikeslist(likes);
                                    fire.setcategory(category);
                                    if (dataSnapshot.child(filePath).child("thumbnail").getValue() != null && dataSnapshot.child(filePath).child("path").getValue() != null) {
                                        fire.setthumbnail(dataSnapshot.child(filePath).child("thumbnail").getValue().toString());
                                        fire.setpath(dataSnapshot.child(filePath).child("path").getValue().toString());
                                    }
                                    fire.setcontent(content);
                                    fire.setfilePath(filePath);
                                    fire.setteam(team);
                                    fire.setpid(pid);
                                    fire.setdate(date);
                                    fire.setpublished(published);
                                    //Toast.makeText(MainActivity.this,url, Toast.LENGTH_SHORT).show();
                                    list.add(fire);
                                }
                            }
                            Collections.reverse(list);

                            recycle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                            ((SimpleItemAnimator) recycle.getItemAnimator()).setSupportsChangeAnimations(false);
                            if (recycle.getAdapter() == null) {
                                recyclerAdapter = new RecyclerAdapter(list, MainActivity.this);
                                recycle.setAdapter(recyclerAdapter);
                            } else {
                                ((RecyclerAdapter) recycle.getAdapter()).updateData(list);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });
        }
        return false;
    }
}
