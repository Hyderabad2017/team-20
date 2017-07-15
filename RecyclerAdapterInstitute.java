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


public class RecyclerAdapterInstitute extends RecyclerView.Adapter<RecyclerAdapterDonation.MyHoder>{

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
        holder.getiName.setText(mylist.getiName());
        holder.getiID.setText(mylist.getiID());
        holder.getiEmail.setText(mylist.getiEmail());
        holder.getIContact1.setText(mylist.getIContact1());
        holder.getIContact2.setText(mylist.getIContact2());
        holder.getLocation.setText(mylist.getLocation());
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
        TextView name,id,email,contact1,contact2,location;
        public MyHoder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.getiID);
            name = (TextView) itemView.findViewById(R.id.getiName);
            email = (TextView) itemView.findViewById(R.id.getiEmail);
            contact1= (TextView) itemView.findViewById(R.id.getIContact1);
            contact2 = (TextView) itemView.findViewById(R.id.getIContact2);
          location = (TextView) itemView.findViewById(R.id.getLocation);
           // amount= (TextView) itemView.findViewById(R.id.cdamount);

        }
    }
}
