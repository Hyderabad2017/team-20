package com.example.dattamber.sdhs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dattamber.
 */


public class RecyclerAdapterDonation extends RecyclerView.Adapter<RecyclerAdapterDonation.MyHoder>{

    List<Donation> list;
    Context contex;
    private int expandedPosition = -1;
    public RecyclerAdapterDonation(List<Donation> list, Context context) {
        this.list = list;
        this.contex = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(contex).inflate(R.layout.donation,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterDonation.MyHoder holder, int position) {
        Donation mylist = list.get(position);
        holder.blood.setText(mylist.getBloodGroup());
        holder.contact1.setText(mylist.getdContact1());
        holder.contact2.setText(mylist.getdContact2());
        holder.email.setText(mylist.getdEmail());
        holder.did.setText(mylist.getdid());
        holder.location.setText(mylist.getdLocation());
        String ide = String.valueOf(mylist.getdid());
        holder.name.setText(getdName);
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
        TextView contact1,email,blood,contact2,did,name,location;
        public MyHoder(View itemView) {
            super(itemView);
            blood = (TextView) itemView.findViewById(R.id.getBloodGroup);
            contact1 = (TextView) itemView.findViewById(R.id.getdContact1);
            contact2 = (TextView) itemView.findViewById(R.id.getdContact2);
            email= (TextView) itemView.findViewById(R.id.getdEmail);
            did = (TextView) itemView.findViewById(R.id.getdID);
            location = (TextView) itemView.findViewById(R.id.getdLocation);
            name= (TextView) itemView.findViewById(R.id.getdName);

        }
    }
}