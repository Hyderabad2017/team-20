package com.example.dattamber.sdhs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class donate extends AppCompatActivity implements View.OnClickListener{
    EditText dtype,damount,dname,dphone;
    AutoCompleteTextView dplace;
    ProgressDialog pd;
    Button dsub;
    TextView dlink;
    FirebaseDatabase database;
    private DatabaseReference mDatabase,crf;
    String c;
    long f;
    String[] teams ={"Hyderabad","Bangalore","Chennai","Guntur","Dharmavaram","Mysore","Machilipatnam","Mekedatu"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        pd = new ProgressDialog(donate.this);
        dplace = (AutoCompleteTextView) findViewById(R.id.dplace);
        dtype = (EditText) findViewById(R.id.dtype);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        dlink = (TextView) findViewById(R.id.dlink);
        setSupportActionBar(toolbar);
        dlink.setOnClickListener(this);
        getSupportActionBar().setTitle("Donate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        damount = (EditText) findViewById(R.id.damount);
        dname = (EditText) findViewById(R.id.dname);
        dphone = (EditText) findViewById(R.id.dphone);
        dsub = (Button) findViewById(R.id.dsub);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        crf = FirebaseDatabase.getInstance().getReference("countd");
        crf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                f = dataSnapshot.getValue(Long.class);
                c=String.valueOf(f);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,teams);
        dplace.setThreshold(1);
        dplace.setAdapter(adapter);
        /*dplace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    // on focus off
                    String str = dplace.getText().toString();
                    ListAdapter listAdapter = dplace.getAdapter();
                    for(int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if(str.compareTo(temp) == 0) {
                            return;
                        }
                    }
                    dplace.setText("");

                }
            }
        });*/
    }

    public void dsubmit(View view)
    {
        String dp,dt,da,dn,dph;
        dp = dplace.getText().toString();
        dt = dtype.getText().toString();
        da = damount.getText().toString();
        dn = dname.getText().toString();
        dph = dphone.getText().toString();
        Calendar calendar;
        int year,month,day;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year);
        String dd = sb.toString();
        if(!da.equals("") && !dn.equals("") && dph.length() == 10 && dph.length()==10 && !dt.equals("") && !dp.equals("")) {
            DonUp post = new DonUp(f, dph, dn, "finished", da, dp, dt, dd);
            Map<String, Object> postValues = post.toMap();
            f++;
            c=String.valueOf(f);

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/donations/donation"+f, postValues);

            mDatabase.updateChildren(childUpdates);
            try {
                mDatabase.child("countd").setValue(f);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Thanks. Our team will contact you soon.")
                        .setPositiveButton("Go to home", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .create().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this,"Make sure all fields are filled and please try again",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == dlink)
        {
            String url = "http://www.dattapeetham.org/donations";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }
}
