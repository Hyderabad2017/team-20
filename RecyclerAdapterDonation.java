package com.example.dattamber.sdhs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dattamber on 19/06/2017.
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
        holder.phone.setText(mylist.getphone());
        holder.name.setText(mylist.getname());
        holder.place.setText(mylist.getplace());
        holder.date.setText(mylist.getdate());
        holder.type.setText(mylist.gettype());
        holder.amount.setText(mylist.getamount());
        String ide = String.valueOf(mylist.getdid());
        holder.did.setText(ide);
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
        TextView name,phone,place,date,type,amount,did;
        public MyHoder(View itemView) {
            super(itemView);
            place = (TextView) itemView.findViewById(R.id.cdplace);
            name = (TextView) itemView.findViewById(R.id.cdname);
            date = (TextView) itemView.findViewById(R.id.cddate);
            phone= (TextView) itemView.findViewById(R.id.cdphone);
            type = (TextView) itemView.findViewById(R.id.cdtype);
            did = (TextView) itemView.findViewById(R.id.cdid);
            amount= (TextView) itemView.findViewById(R.id.cdamount);

        }
    }
}
