package com.example.dattamber.cfg_uwh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class checklist extends AppCompatActivity {
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        getSupportActionBar().setTitle("Terms and Conditions");
        cb = (CheckBox)findViewById(R.id.checkBox);
    }

    public void clicked(View v)
    {
        Intent intent = new Intent(this, donor_signup.class);
        startActivity(intent);
    }

}
