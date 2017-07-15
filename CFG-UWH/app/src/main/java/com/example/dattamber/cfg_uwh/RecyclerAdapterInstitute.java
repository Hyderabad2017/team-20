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

public class RecyclerAdapterInstitute extends RecyclerView.Adapter<RecyclerAdapterInstitute.MyHoder>{

    List<Institute> list;
    Context contex;    private int expandedPosition = -1;
    public RecyclerAdapterInstitute(List<Institute> list, Context context) {
        this.list = list;
        this.contex = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(contex).inflate(R.layout.institute,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterInstitute.MyHoder holder, int position) {
        Institute mylist = list.get(position);
        holder.name.setText(mylist.getiName());
    //    holder..setText(mylist.getiID());
      //  holder.email.setText(mylist.getiEmail());
        holder.contact1.setText(mylist.getIContact1());
        holder.contact2.setText(mylist.getIContact2());
        holder.location.setText(mylist.getLocation());
        String ide = String.valueOf(mylist.getiID());
        holder.id.setText(ide);
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
          //  id = (TextView) itemView.findViewById(R.id.b);
            name = (TextView) itemView.findViewById(R.id.ciname);
        //    email = (TextView) itemView.findViewById(R.id.ciemail);
            contact1= (TextView) itemView.findViewById(R.id.ciphone);
            contact2 = (TextView) itemView.findViewById(R.id.ciphone1);
            location = (TextView) itemView.findViewById(R.id.ciplace);
            // amount= (TextView) itemView.findViewById(R.id.cdamount);
            id = (TextView) itemView.findViewById(R.id.ciid);
        }
    }
}