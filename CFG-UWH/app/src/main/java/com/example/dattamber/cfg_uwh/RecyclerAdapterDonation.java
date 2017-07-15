package com.example.dattamber.cfg_uwh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chandana on 7/15/2017.
 */

public class RecyclerAdapterDonation extends RecyclerView.Adapter<RecyclerAdapterDonation.MyHoder>{

    List<Req> list;
    Context contex;    private int expandedPosition = -1;
    public RecyclerAdapterDonation(List<Req> list, Context context) {
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
        Req mylist = list.get(position);
        holder.name.setText(mylist.getinstitutionName());
        holder.Location.setText(mylist.getlocation());
        holder.BloodGroup.setText(mylist.getbloodGroup());
        holder.IContact1.setText(mylist.contactNumber());
        holder.IContact2.setText(mylist.getcontact2());
        holder.Date.setText(mylist.getdeadline());
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
        TextView name,Location,BloodGroup,IContact1,IContact2,Date;
        public MyHoder(View itemView) {
            super(itemView);
            // place = (TextView) itemView.findViewById(R.id.);
            name = (TextView) itemView.findViewById(R.id.cdname);
            Date = (TextView) itemView.findViewById(R.id.cddate);
            Location= (TextView) itemView.findViewById(R.id.cdplace);
            BloodGroup = (TextView) itemView.findViewById(R.id.cdgroup);
            IContact1 = (TextView) itemView.findViewById(R.id.cdphone);
            IContact2= (TextView) itemView.findViewById(R.id.cdphone1);

        }
    }
}
