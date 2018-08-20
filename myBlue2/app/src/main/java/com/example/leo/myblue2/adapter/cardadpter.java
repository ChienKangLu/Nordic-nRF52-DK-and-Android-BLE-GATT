package com.example.leo.myblue2.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leo.myblue2.BLE.BtBondUtils;
import com.example.leo.myblue2.BLE.ClsUtils;
import com.example.leo.myblue2.ConnectPage;
import com.example.leo.myblue2.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leo on 2016/3/1.
 */
public class cardadpter extends RecyclerView.Adapter<cardadpter.ViewHolder> {

        private Status mystatus;
        private RecyclerView mList;
        Context context;
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView device;
            public TextView address;
            public View card;
            public Button bondBtn;
            public ViewHolder(View v) {
                super(v);
                device = (TextView) v.findViewById(R.id.device);
                address=(TextView) v.findViewById(R.id.address);
                bondBtn=(Button) v.findViewById(R.id.bondBtn);
                card=v;
            }
        }

        public static class Status{
            public ArrayList<String> deviceName;
            public ArrayList<String> address;
            public ArrayList<BluetoothDevice> mLeDevices;
            //public static int size=0;
            Context context;
            public  Status(Context context){
                this.deviceName=new ArrayList<>();
                this.address=new ArrayList<>();
                this.mLeDevices=new ArrayList<>();
                this.context=context;
            }
            public void add(BluetoothDevice device){
                if(!mLeDevices.contains(device)) {
                    //size++;
                    mLeDevices.add(device);
                    if (device.getName() != null &&device.getName().length() > 0) {
                        deviceName.add(device.getName());
                    } else {
                        deviceName.add("Unknown Device");
                    }
                    address.add(device.getAddress());

                    //Toast.makeText(context,""+device.getAddress(),Toast.LENGTH_SHORT).show();
                }
            }

            public void clear() {
                this.deviceName.clear();
                this.address.clear();
                this.mLeDevices.clear();
                //size=0;
            }
        }
        public cardadpter(Context context, RecyclerView mList) {
            this.mystatus=new Status(context);
            /*
            this.size=10;

            for(int i=0;i<10;i++){
                this.mystatus.device.add("device"+i);
                this.mystatus.address.add("address"+i);
            }
            */
            this.context=context;
            this.mList=mList;
        }

        @Override
        public cardadpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.device.setText(mystatus.deviceName.get(position));
            holder.address.setText(mystatus.address.get(position));


            holder.bondBtn.setTag(position);
            holder.bondBtn.setOnClickListener(Bond);


        }

        @Override
        public int getItemCount() {
            return this.mystatus.address.size();
        }
        public void add(BluetoothDevice device){
            mystatus.add(device);
        }
        public void clear(){
            mystatus.clear();
        }
        View.OnClickListener Bond=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    final int position = Integer.parseInt(v.getTag().toString());
                    BluetoothDevice nowDeivce = mystatus.mLeDevices.get(position);

                    if (nowDeivce.getBondState() == BluetoothDevice.BOND_NONE) {
                        Toast.makeText(context, "bonding", Toast.LENGTH_SHORT).show();
                        //這裏只需要createBond就行了
                        BtBondUtils.createBond(nowDeivce.getClass(),nowDeivce);
                    } else if (nowDeivce.getBondState() == BluetoothDevice.BOND_BONDED) {
                        Toast.makeText(context, "bonded", Toast.LENGTH_SHORT).show();
                    }
                    ConnectPage.setmDevice(nowDeivce);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };




}

