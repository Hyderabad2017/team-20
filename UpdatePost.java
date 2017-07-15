package com.example.dattamber.sdhs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdatePost extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    EditText title,content,date;
    AutoCompleteTextView team;
    Spinner category;
    String cat,datestr="";
    Button upsubmit;
    int flag=0,mYear,mMonth,mDay;
    FirebaseDatabase database;
    String[] teams ={"Hyderabad","Khammam","Doodivenkatapuram","Bangalore","Chennai","Guntur","Dharmavaram","Mysore","Machilipatnam","Mekedatu","Mumbai","Cochin","Malaysia","United Kingdom","United States of America","Srikakulam","Rajamundry","Vizag","Makavaram","Pulugartha","Pithapuram","Vijayawada","Gannavaram","Gudiwada","Dharmajigudem","Nuziveedu","Ongole","Chirala","Addanki","Paruchuru","Eluru","Bhimavaram","Kalla","Kaikaluru","Akiveedu","Kadapa","Nellore","Tirupathi","Proddutur","Ananthapur","Dharmavaram","Jayalakshmipuram","Kurnool","Kamareddy","Nizamabad","Banswada","Warangal","Karimnagar","Chinakodepaka","Janawada","Mahabubnagar"};
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        title = (EditText) findViewById(R.id.uptitle);
        category = (Spinner) findViewById(R.id.upcategory);
        content = (EditText) findViewById(R.id.upcontent);
        team = (AutoCompleteTextView) findViewById(R.id.upteam);
        upsubmit = (Button) findViewById(R.id.upsubmit);
        date = (EditText) findViewById(R.id.upddate);
        date.setOnClickListener(this);
        final String fcontent,ftitle,fteam;
        fcontent = getIntent().getStringExtra("fcontent");
        ftitle = getIntent().getStringExtra("ftitle");
        fteam = getIntent().getStringExtra("fteam");
        content.setText(fcontent);
        title.setText(ftitle);
        team.setText(fteam);
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
        category.setOnItemSelectedListener(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");
        upsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference objRef = FirebaseDatabase.getInstance().getReference("posts");
                Query pendingTasks = objRef.orderByChild("content").equalTo(fcontent);
                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {
                        for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                            DatabaseReference ps = FirebaseDatabase.getInstance().getReference("posts").child(snapshot.getKey());
                            ps.child("content").setValue(content.getText().toString());
                            ps.child("team").setValue(team.getText().toString());
                            ps.child("title").setValue(title.getText().toString());
                            ps.child("date").setValue(date.getText().toString());
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        cat = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    }

}
