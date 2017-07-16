package com.example.dattamber.cfg_uwh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by chandana on 7/15/2017.
 */

public class RecyclerAdapterHospi extends RecyclerView.Adapter<RecyclerAdapterHospi.MyHoder>{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    List<Req> list;
    String phoneNo;
    Context contex;    private int expandedPosition = -1;
    public RecyclerAdapterHospi(List<Req> list, Context context) {
        this.list = list;
        this.contex = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(contex).inflate(R.layout.donation_hospi,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterHospi.MyHoder holder, int position) {
        final Req mylist = list.get(position);
        holder.name.setText(mylist.getinstitutionName());
        holder.Location.setText(mylist.getlocation());
        holder.BloodGroup.setText(mylist.getbloodGroup());
        holder.IContact1.setText(mylist.contactNumber());
        holder.IContact2.setText(mylist.getcontact2());
        holder.Date.setText(mylist.getdeadline());
        holder.ph.setVisibility(View.INVISIBLE);
        holder.IContact2.setLinkTextColor(Color.parseColor("#000000"));
        holder.IContact1.setLinkTextColor(Color.parseColor("#000000"));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mylist.contactNumber()));
                if(true)
                {
                    v.getContext().startActivity(intent);
                }
            }
        });
        if(mylist.getStat().equals("true"))
        {
            holder.cview.setCardBackgroundColor(Color.parseColor("#ED3C3C"));
            holder.bfff.setTextColor(Color.parseColor("#FFFFFF"));
            holder.cfff.setTextColor(Color.parseColor("#FFFFFF"));
            holder.name.setTextColor(Color.parseColor("#FFFFFF"));
            holder.Location.setTextColor(Color.parseColor("#FFFFFF"));
            holder.BloodGroup.setTextColor(Color.parseColor("#FFFFFF"));
            holder.Date.setTextColor(Color.parseColor("#FFFFFF"));
            holder.IContact1.setLinkTextColor(Color.parseColor("#FFFFFF"));
            holder.IContact2.setLinkTextColor(Color.parseColor("#FFFFFF"));
            holder.ph.setHintTextColor(Color.parseColor("#FFFFFF"));
            holder.btn.setTextColor(Color.parseColor("#FFFFFF"));
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String message = "Hey ! Thanks for saving a life :)";
                SmsManager smsManager = SmsManager.getDefault();

                if (ContextCompat.checkSelfPermission(v.getContext(),
                        android.Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) v.getContext(),
                            android.Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions((Activity) v.getContext(),
                                new String[]{android.Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                }*/
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("requests").child("request"+String.valueOf(mylist.getdonationId()));
                dref.child("status").setValue("complete");
            }
        });
    }


    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }
        }catch (Exception e){

        }

        return arr;

    }

    class MyHoder extends RecyclerView.ViewHolder {
        TextView name,Location,BloodGroup,IContact1,IContact2,Date,bfff,cfff;
        Button btn;
        EditText ph;
        CardView cview;
        public MyHoder(View itemView) {
            super(itemView);
            // place = (TextView) itemView.findViewById(R.id.);
            name = (TextView) itemView.findViewById(R.id.chname);
            Date = (TextView) itemView.findViewById(R.id.chdate);
            Location= (TextView) itemView.findViewById(R.id.chplace);
            BloodGroup = (TextView) itemView.findViewById(R.id.chgroup);
            IContact1 = (TextView) itemView.findViewById(R.id.chphone1);
            IContact2= (TextView) itemView.findViewById(R.id.chphone1);
            btn = (Button) itemView.findViewById(R.id.comp);
            bfff = (TextView)itemView.findViewById(R.id.bfff);
            ph = (EditText) itemView.findViewById(R.id.donemail);
            cfff = (TextView) itemView.findViewById(R.id.cfff);
            cview = (CardView) itemView.findViewById(R.id.hospi_card_view);
        }
    }
}
