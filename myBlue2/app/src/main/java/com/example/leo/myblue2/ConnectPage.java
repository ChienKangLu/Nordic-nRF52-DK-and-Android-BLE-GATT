package com.example.leo.myblue2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leo.myblue2.BLE.BluetoothLeService;
import com.example.leo.myblue2.BLE.SampleGattAttributes;
import com.example.leo.myblue2.adapter.cardadpter;
import com.example.leo.myblue2.database.saveTemperature;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * Created by leo on 2017/5/5.
 */

public class ConnectPage extends Fragment {

    public static final String Title = "ConnectPage";
    View rootView;


    private final static String TAG = "TAGME";

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String device= "device";

    private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    public static String mDeviceAddress;
    public static BluetoothDevice nowBleDevice;
    private Button Bond;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private final String LEOgo="LEOgo";

    public static void setmDevice(BluetoothDevice device){
        mDeviceAddress=device.getAddress();
        nowBleDevice=device;
        //((TextView)rootView.findViewById(R.id.device_address)).setText(mDeviceAddress);

    }

    public ConnectPage() {
    }


    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                getActivity().finish();
            }
            // Automatically connects to the device upon successful start-up initialization.;
            //Toast.makeText(getActivity(),"onServiceConnected-->connect",LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                //invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                //invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                if(intent.getStringExtra(BluetoothLeService.EXTRA_DATA)!=null) {
                    displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                    //及時更改資料!!
                    //ShowPage.setData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                    String data=intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                    String temp=data.substring(0,data.indexOf("("));

                    double realTemp=Double.parseDouble(temp)/4;
                    if(realTemp!=63.75) {

                        ShowPage.alert.setVisibility(View.GONE);
                        ShowPage.set(ShowPage.Tempetature, "" + realTemp);
                        saveTemperature saveT = new saveTemperature(getActivity());
                        saveT.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.deviceid, "" + realTemp);
                        MainActivity.HAHAlist.add(saveT);


                        if (realTemp >= 50) {
                            //ShowPage.set(ShowPage.alert,"溫度過高請注意!!");
                            ShowPage.alert2.setVisibility(View.VISIBLE);
                        } else {
                            ShowPage.alert2.setVisibility(View.GONE);
                        }
                    }else{

                        ShowPage.set(ShowPage.Tempetature, "?");
                        //ShowPage.set(ShowPage.alert,"尚未輸入金鑰\n無法存取溫度");
                        ShowPage.alert.setVisibility(View.VISIBLE);
                    }


                }
                if(intent.getStringExtra(BluetoothLeService.String_Device_name)!=null) {
                    displayData(intent.getStringExtra(BluetoothLeService.String_Device_name));
                    Log.e(LEOgo,"WONDERFUL");
                    //及時更改資料!!
                    //ShowPage.setData(intent.getStringExtra(BluetoothLeService.String_Device_name));
                    ShowPage.set(ShowPage.DeviceName,intent.getStringExtra(BluetoothLeService.String_Device_name));
                }
                if(intent.getStringExtra(BluetoothLeService.Other_DATA)!=null) {
                    displayData(intent.getStringExtra(BluetoothLeService.Other_DATA));
                    Log.e(LEOgo,"hahaha");
                }

            }
        }

    };

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (mGattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                            Log.e(LEOgo,"bad");
                        }
                        //持續notify
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            Log.e(LEOgo,"GOOD");
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        return true;
                    }
                    return false;
                }
            };

    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField.setText(R.string.no_data);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.connectpage_layout, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getActivity(),mDeviceAddress,LENGTH_SHORT).show();
                mBluetoothLeService.connect(mDeviceAddress);
                Snackbar.make(view, "Connet to device", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ((TextView)rootView.findViewById(R.id.device_address)).setText(nowBleDevice.getAddress());
                ((TextView)rootView.findViewById(R.id.device_name)).setText(nowBleDevice.getName());

            }
        });

        // Sets up UI references.
        ((TextView) rootView.findViewById(R.id.device_address)).setText(mDeviceAddress);
        mGattServicesList = (ExpandableListView) rootView.findViewById(R.id.gatt_services_list);
        mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) rootView.findViewById(R.id.connection_state);
        mDataField = (TextView) rootView.findViewById(R.id.data_value);

        //getActionBar().setTitle(mDeviceName);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
        getActivity().bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        Button userKeyButton=(Button)rootView.findViewById(R.id.userKeyButton);
        userKeyButton.setOnClickListener(setUserKey);
        Button disconnectButton=(Button)rootView.findViewById(R.id.disconnect);
        disconnectButton.setOnClickListener(disconnetEvent);
        //Toast.makeText(getActivity(),"onCreateView",LENGTH_SHORT).show();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }
    private void updateConnectionState(final int resourceId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();

                if(SampleGattAttributes.Device_name.equals(uuid)){
                    Log.e(LEOgo,"devicename: " +uuid);
                    final byte[] data = gattCharacteristic.getValue();
                    if (data != null && data.length > 0) {
                        final StringBuilder stringBuilder = new StringBuilder(data.length);
                        for(byte byteChar : data)
                            stringBuilder.append(String.format("%02X ", byteChar));
                        String a=String.format("%02X ", data[0]);
                        int value = Integer.parseInt(a.trim(), 16);
                        Log.e(LEOgo,value+"");
                    }else{
                        Log.e(LEOgo,"no data");
                    }

                }
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                getActivity(),
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 },
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        mGattServicesList.setAdapter(gattServiceAdapter);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public static StringBuilder Hex2Ascii(byte[] data){

        //String hex = "6C";
        StringBuilder output = new StringBuilder();
        if (data != null && data.length > 0) {
            final StringBuilder rawdata = new StringBuilder(data.length);
            for (byte byteChar : data)
                rawdata.append(String.format("%02X ", byteChar));



            for (int i = 0; i < rawdata.length(); i += 2) {
                String str = rawdata.substring(i, i + 2);
                output.append((char) Integer.parseInt(str, 16));
            }
        }
        return output;
    }
    View.OnClickListener setUserKey= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBluetoothLeService.writeCharacteristic(MainActivity.key);
        }
    };
    View.OnClickListener disconnetEvent= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBluetoothLeService.writeCharacteristic(MainActivity.initailkey);
            mBluetoothLeService.disconnect();
        }
    };
}
