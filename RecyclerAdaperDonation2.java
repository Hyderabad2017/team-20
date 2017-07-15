package com.example.dattamber.sdhs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;



public class RecyclerAdapterDonation extends RecyclerView.Adapter<RecyclerAdapterDonation.MyHoder>{

    List<Donation> list;
    Context contex;    private int expandedPosition = -1;
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
        holder.getIName.setText(mylist.getIName());
        holder.getLocation.setText(mylist.getLocation());
        holder.getBloodGroup.setText(mylist.getBloodGroup());
        holder.getIContact1.setText(mylist.getIContact1());
        holder.getIContact2.setText(mylist.getIContact2());
       
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
        TextView name,Location,BloodGroup,IContact1,IContact2;
        public MyHoder(View itemView) {
            super(itemView);
           // place = (TextView) itemView.findViewById(R.id.);
            name = (TextView) itemView.findViewById(R.id.getIName);
        //    date = (TextView) itemView.findViewById(R.id.cddate);
            Location= (TextView) itemView.findViewById(R.id.getLocation);
            BloodGroup = (TextView) itemView.findViewById(R.id.getBloodGroup);
            IContact1 = (TextView) itemView.findViewById(R.id.getIContact1);
            IContact2= (TextView) itemView.findViewById(R.id.getIContact2);

        }
    }
}
