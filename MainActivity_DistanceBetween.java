package com.example.mohammedarshad.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float distance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    Toast.makeText(getActivity(), "This is my message" + "Hi",Toast.LENGTH_LONG).show();


    public float getDistanceInMiles()
    {
        float[] dist =new float[1];
        Location.distanceBetween(17.3850,78.4867,12.9716,77.5946,dist);
        return dist[0];
    }
}
