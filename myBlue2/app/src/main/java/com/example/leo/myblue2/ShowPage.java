package com.example.leo.myblue2;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.leo.myblue2.BLE.BluetoothLeService;
import com.example.leo.myblue2.BLE.SampleGattAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by leo on 2017/5/5.
 */

public class ShowPage extends Fragment {

    public static final String Title = "ShowPage";
    View rootView;
    public static TextView show;
    public static TextView DeviceName;
    public static TextView Tempetature;
    public static RelativeLayout alert;
    public static RelativeLayout alert2;

    private final String LEOgo="LEOgo";


    public ShowPage() {
    }

    public static void setData(String data){
        show.setText(data);

    }

    public static void setDeviceName(String data){
        DeviceName.setText(data);
    }

    public static void set(TextView v,String data){
        v.setText(data);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.showpage_layout, container, false);
        show=((TextView) rootView.findViewById(R.id.show));
        DeviceName=((TextView) rootView.findViewById(R.id.DeviceName));
        Tempetature=((TextView) rootView.findViewById(R.id.Tempetature));
        alert=((RelativeLayout) rootView.findViewById(R.id.alert));
        alert2=(RelativeLayout) rootView.findViewById(R.id.alert2);

        return rootView;
    }

}
