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


public class RecyclerAdapterUser extends RecyclerView.Adapter<RecyclerAdapterUser.MyHoder>{

    List<User> list;
    Context contex;
    private int expandedPosition = -1;
    public RecyclerAdapterUser(List<User> list, Context context) {
        this.list = list;
        this.contex = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(contex).inflate(R.layout.user_card,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterUser.MyHoder holder, int position) {
        User mylist = list.get(position);
        holder.blood.setText(mylist.getdBloodGroup());
        holder.contact1.setText(mylist.dContact1());
        holder.contact2.setText(mylist.dContact2());
        holder.location.setText(mylist.dLocation());
        holder.name.setText(mylist.dName());
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
            blood = (TextView) itemView.findViewById(R.id.uid);
            contact1 = (TextView) itemView.findViewById(R.id.uphone);
            contact2 = (TextView) itemView.findViewById(R.id.uphone1);
            location = (TextView) itemView.findViewById(R.id.uplace);
            name= (TextView) itemView.findViewById(R.id.uname);
        }
    }
}