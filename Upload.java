package com.example.dattamber.sdhs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upload extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "Upload" ;
    EditText title,content, date;
    AutoCompleteTextView team;
    ImageView test;
    Spinner category;
    String url,cat, datestr="";
    Uri uri[] = new Uri[8];
    Long fe = 0L;
    String ce ="";
    int Numb = 0;
    ArrayList<Uri> imageUri = new ArrayList<Uri>();
    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    public static final int GALLERY_INTENT = 2;
    public StorageReference storageReference;
    String[] teams ={"Hyderabad","Khammam","Doodivenkatapuram","Bangalore","Chennai","Guntur","Dharmavaram","Mysore","Machilipatnam","Mekedatu","Mumbai","Cochin","Malaysia","United Kingdom","United States of America","Srikakulam","Rajamundry","Vizag","Makavaram","Pulugartha","Pithapuram","Vijayawada","Gannavaram","Gudiwada","Dharmajigudem","Nuziveedu","Ongole","Chirala","Addanki","Paruchuru","Eluru","Bhimavaram","Kalla","Kaikaluru","Akiveedu","Kadapa","Nellore","Tirupathi","Proddutur","Ananthapur","Dharmavaram","Jayalakshmipuram","Kurnool","Kamareddy","Nizamabad","Banswada","Warangal","Karimnagar","Chinakodepaka","Janawada","Mahabubnagar"};
    Button upload,sub;
    ImageView img;
    ArrayList<Integer> flags = new ArrayList<Integer>(8);
    ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    int mYear,mMonth,mDay;
    final ArrayList<Uri> downs = new ArrayList<Uri>();
    final ArrayList<String> lastPaths = new ArrayList<String>();
    ProgressDialog pd;
    final Long[] f = new Long[1];
    final String[] c = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getActionBar().setTitle("Add a post");
        getSupportActionBar().setTitle("Add a post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_upload);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        pd = new ProgressDialog(Upload.this);

        title = (EditText) findViewById(R.id.title);
        category = (Spinner) findViewById(R.id.category);
        content = (EditText) findViewById(R.id.content);
        team = (AutoCompleteTextView) findViewById(R.id.team);
        img = (ImageView) findViewById(R.id.imageButton);
        date = (EditText) findViewById(R.id.date);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        img7 = (ImageView) findViewById(R.id.img7);
        img8 = (ImageView) findViewById(R.id.img8);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);
        img8.setOnClickListener(this);
        date.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        sub = (Button) findViewById(R.id.submit);
        List<String> categories = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,teams);
        categories.add("Social");
        categories.add("Medical");
        categories.add("Spiritual");
        categories.add("Charity");
        category.setPrompt("Select category");
        team.setThreshold(1);
        team.setAdapter(adapter);
        team.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    // on focus off
                    String str = team.getText().toString();
                    ListAdapter listAdapter = team.getAdapter();
                    for(int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if(str.compareTo(temp) == 0) {
                            return;
                        }
                    }
                    team.setText("");

                }
            }
        });
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
        category.setOnItemSelectedListener(this);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ptitle,pcontent,pteam;
                ptitle = title.getText().toString();
                pcontent = content.getText().toString();
                pteam = team.getText().toString();
                if(!pteam.equals("") && !ptitle.equals("") && !cat.equals("") && !pcontent.equals("") && flags.size()>0 && !datestr.equals("")) {
                    pd.setMessage("Uploading images ...");
                    pd.setCancelable(false);
                    pd.show();
                    for(final Uri uri : imageUri) {
                        StorageReference filepath = storageReference.child(uri.getLastPathSegment());
                        Log.d(TAG, "File path is : " + filepath.toString());
                        //Toast.makeText(getApplicationContext(), "File path " + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                        filepath.putFile(uri);
                        downs.add(Uri.parse("blaah"));
                        lastPaths.add(uri.getLastPathSegment());
                    }
                    Log.d("Downs size ",String.valueOf(downs.size()));
                    up(downs,lastPaths);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void up(final ArrayList<Uri> downs, final ArrayList<String> filePaths) {

        pd.setMessage("Submitting post ...");
        final String ptitle,pcategory,pcontent,pteam,purl,ppublished,pdate;
        purl = downs.get(0).toString();
        pcategory = cat;
        ppublished="false";
        Calendar calendar;
        int year,month,day;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year);
        pdate = datestr;
        ptitle = title.getText().toString();
        pcontent = content.getText().toString();
        pteam = team.getText().toString();
        if(!pteam.equals("") && !ptitle.equals("Appaji's audio message") && !ptitle.equals("Appaji's video message") && !ptitle.equals("Appaji's message for volunteers") && !ptitle.equals("")  && !pcategory.equals("") && !pcontent.equals("") && flags.size()>0 && !purl.equals("") && !datestr.equals("")) {
            DatabaseReference red = FirebaseDatabase.getInstance().getReference("count");
            red.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    fe = dataSnapshot.getValue(Long.class);
                    ce = String.valueOf(fe);
                    Long pid = fe;
                    Post post = new Post(pid, pcategory, ptitle, pcontent, purl, pteam, ppublished, pdate, filePaths.get(0));
                    Map<String, Object> postValues = post.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    String key1 = mDatabase.child("posts").push().getKey();
                    childUpdates.put("/posts/post" + String.valueOf(fe), postValues);
                    fe++;
                    mDatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                sub.setText("Add another post");
                                pd.dismiss();
                                mDatabase.child("count").setValue(fe);
                                for(int i=0;i<=downs.size();i++) {
                                    mDatabase.child("posts").child("post" + ce).child("urls").child("url"+String.valueOf(i)).setValue(downs.get(i).toString());
                                    mDatabase.child("posts").child("post" + ce).child("filePaths").child("filePath"+String.valueOf(i)).setValue(lastPaths.get(i).toString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(Upload.this, "Post adding failed", Toast.LENGTH_LONG);
                            }
                            title.setText("");
                            team.setText("");
                            content.setText("");
                            date.setText("");
                            datestr = "";
                            flags.clear();
                            downs.clear();
                            lastPaths.clear();
                            img1.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img2.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img3.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img4.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img5.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img6.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img7.setImageResource(R.drawable.ic_add_box_black_24dp);
                            img8.setImageResource(R.drawable.ic_add_box_black_24dp);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(Upload.this, "Post adding failed", Toast.LENGTH_LONG);
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
        else
        {

            Toast.makeText(this,"Make sure all fields are filled or please try again",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void upload(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {
            if(Numb == 0) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img1);
                Numb++;
            }
            else if(Numb == 1) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img2);
                Numb++;
            }
            else if(Numb == 2) {
                flags.add(1);
                imageUri.add(data.getData());
                uri[Numb] = data.getData();
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img3);
                Numb++;
            }
            else if(Numb == 3) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img4);
                Numb++;
            }
            else if(Numb == 4) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img5);
                Numb++;
            }
            else if(Numb == 5) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img6);
                Numb++;
            }
            else if(Numb == 6) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img7);
                Numb++;
            }
            else if(Numb == 7) {
                flags.add(1);
                uri[Numb] = data.getData();
                imageUri.add(data.getData());
                Picasso.with(Upload.this).load(data.getData()).fit().centerInside().into(img8);
                Numb++;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        cat = item;
    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onClick(View v) {
        if ( v == date)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datestr = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();

        }

        if( v == img1 || v == img2 ||v == img3 ||v == img4 ||v == img5 ||v == img6 ||v == img7 || v == img8)
        {
            upload(v);
        }
    }
}

