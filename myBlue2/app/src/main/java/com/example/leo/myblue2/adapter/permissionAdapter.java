package com.example.leo.myblue2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.leo.myblue2.Navigator;
import com.example.leo.myblue2.R;
import com.example.leo.myblue2.database.DB;
import com.example.leo.myblue2.database.dbitem;
import com.example.leo.myblue2.database.device;

import java.util.ArrayList;

/**
 * Created by leo on 2016/3/1.
 */
public class permissionAdapter extends RecyclerView.Adapter<permissionAdapter.ViewHolder> {

        private Status mystatus;
        private RecyclerView mList;
        Context context;
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView deviceName;
            public TextView address;
            public TextView deviceDescribe;
            public View card;
            public Button bondBtn;
            public ViewHolder(View v) {
                super(v);
                deviceName = (TextView) v.findViewById(R.id.deviceName);
                address=(TextView) v.findViewById(R.id.address);
                deviceDescribe=(TextView) v.findViewById(R.id.deviceDescribe);
                bondBtn=(Button) v.findViewById(R.id.bondBtn);
                card=v;
            }
        }

        public static class Status{
            public ArrayList<String> deviceid;
            public ArrayList<String> deviceName;
            public ArrayList<String> address;
            public ArrayList<String> deviceDescribe;
            public ArrayList<String> key;
            //public static int size=0;
            Context context;
            public  Status(Context context){
                this.deviceid=new ArrayList<>();
                this.deviceName=new ArrayList<>();
                this.address=new ArrayList<>();
                this.deviceDescribe=new ArrayList<>();
                this.key=new ArrayList<>();
                this.context=context;
            }

            public void clear() {
                this.deviceid.clear();
                this.deviceName.clear();
                this.address.clear();
                this.deviceDescribe.clear();
                this.key.clear();
                //size=0;
            }
        }
        public permissionAdapter(String userid,Context context, RecyclerView mList) {
            this.mystatus=new Status(context);
            this.context=context;
            this.mList=mList;
            DB db=new DB(context);
            db.permissionQuery(userid,new DB.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<dbitem> result) {
                        for(int i=0;i<result.size();i++){
                            device d=(device)result.get(i);
                            Log.e("LEOgo",d.getDeviceid());
                            Log.e("LEOgo",d.getDeviceName());
                            Log.e("LEOgo",d.getDeviceAddress());
                            Log.e("LEOgo",d.getRealkey());
                            Log.e("LEOgo",d.getDeviceDescribe());
                            permissionAdapter.this.mystatus.deviceid.add(d.getDeviceid());
                            permissionAdapter.this.mystatus.deviceName.add(d.getDeviceName());
                            permissionAdapter.this.mystatus.address.add(d.getDeviceAddress());
                            permissionAdapter.this.mystatus.key.add(d.getRealkey());
                            permissionAdapter.this.mystatus.deviceDescribe.add(d.getDeviceDescribe());

                        }
                        permissionAdapter.this.notifyDataSetChanged();
                        Log.e("LEOgo",""+permissionAdapter.this.mystatus.address.size());
                }
            });
            /*
            this.size=10;

            for(int i=0;i<10;i++){
                this.mystatus.device.add("device"+i);
                this.mystatus.address.add("address"+i);
            }
            */
        }

        @Override
        public permissionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.permissioncard, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.deviceName.setText(mystatus.deviceName.get(position));
            holder.address.setText(mystatus.address.get(position));
            holder.deviceDescribe.setText(mystatus.deviceDescribe.get(position));
            holder.bondBtn.setTag(position);
            holder.bondBtn.setOnClickListener(getKey);


        }

        @Override
        public int getItemCount() {
            return this.mystatus.address.size();
        }

        public void clear(){
            mystatus.clear();
        }
        View.OnClickListener getKey=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    final int position = Integer.parseInt(v.getTag().toString());
                    String key = mystatus.key.get(position);
                    String deviceid = mystatus.deviceid.get(position);
                    Log.e("LEOgo",key);
                    Navigator.INSTANCE.navigateToSecond(context,key,deviceid);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };




}

