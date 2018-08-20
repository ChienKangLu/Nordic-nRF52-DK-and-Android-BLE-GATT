package com.example.leo.myblue2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.leo.myblue2.adapter.cardadpter;
import com.example.leo.myblue2.adapter.permissionAdapter;

/**
 * Created by leo on 2017/5/5.
 */

public class permissionPage extends AppCompatActivity {

    public static permissionAdapter myAdapter;
    RecyclerView mList;
    String userid;

    public permissionPage() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissionpage_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getPara();
        Log.e("LEOgo",userid);
        mList = (RecyclerView)findViewById(R.id.listview);

        myAdapter = new permissionAdapter(userid,this, mList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }
    public void getPara(){
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userid = extras.getString(EXTRA_KEY);
            // and get whatever type user account id is
        }
    }

    private static String EXTRA_KEY = "key";
    public static Intent getCallingIntent(Context context, String value){
        Intent callingIntent = new Intent(context,permissionPage.class);
        callingIntent.putExtra(EXTRA_KEY, value);
        return callingIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                permissionPage.this.finish();

                //Toast.makeText(this,"good",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
