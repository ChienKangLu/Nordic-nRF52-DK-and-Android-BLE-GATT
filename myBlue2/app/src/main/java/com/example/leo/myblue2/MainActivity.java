package com.example.leo.myblue2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.leo.myblue2.database.saveTemperature;

import java.util.ArrayList;

import static android.Manifest.permission.*;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    String[] permissionsWeNeed={BLUETOOTH_ADMIN,BLUETOOTH,ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION};
    final int PERMISSION_REQUEST_CODE= 3;
    static String key;
    static String initailkey="12:34:56:78";
    static String deviceid;
    static ArrayList<saveTemperature> HAHAlist=new ArrayList<saveTemperature>();


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupPermissions();
        setContentView(R.layout.activity_main);

        getPara();
        Toast.makeText(this,key+"/"+deviceid,Toast.LENGTH_SHORT).show();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                Toast.makeText(MainActivity.this,"back",Toast.LENGTH_SHORT).show();
                onBackPressed();
                MainActivity.this.finish();
                for(saveTemperature task:HAHAlist){
                    task.cancel(true);
                    //task=null;
                }


            }
        });
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    if(position==1){
                        //getMenuInflater().inflate(R.menu.menu_main, menu);
                        //toolbar.inflateMenu(R.menu.menu_connect);

                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_scan:
                //mLeDeviceListAdapter.clear();//清除LIST
                ScanPage.myAdapter.clear();
                ((ScanPage)(mSectionsPagerAdapter.scanPage)).scanLeDevice(true);
                break;
            case R.id.menu_stop:
                ((ScanPage)(mSectionsPagerAdapter.scanPage)).scanLeDevice(false);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (!((ScanPage)(mSectionsPagerAdapter.scanPage)).mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */


    //setupPermissions
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public Fragment scanPage;
        public Fragment connectPage;
        public Fragment showPage;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0){
                scanPage=new ScanPage();
                return scanPage;
            }else if (position==1){
                connectPage=new ConnectPage();
                return connectPage;
            }else if (position==2){
                showPage=new ShowPage();
                return showPage;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return ((ScanPage)getItem(0)).Title;
                case 1:
                    return ((ConnectPage)getItem(1)).Title;
                case 2:
                    return ((ConnectPage)getItem(2)).Title;
            }
            return null;
        }
    }
    private void setupPermissions() {
        boolean isGranted = true;
        for (String permission : permissionsWeNeed) {
            isGranted &= ActivityCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_GRANTED;
        }
        if (!isGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i("mytest","#");
                requestPermissions(permissionsWeNeed, PERMISSION_REQUEST_CODE);
                Log.i("mytest","X");
            } else {
                Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
                //finishAndRemoveTask ();
            }
        } else {
            //initBluetooth();
        }

    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults)
    {


        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                boolean isGranted = grantResults.length > 0;
                for (int grantResult : grantResults) {
                    Log.i("mytest","W");
                    isGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
                }
                if (isGranted) {
                    //initBluetooth();
                } else {
                    Toast.makeText(this, "no permission_gg", Toast.LENGTH_SHORT).show();
                    //finishAndRemoveTask ();
                }
            }
        }
    }

    private static String EXTRA_KEY = "key";
    private static String EXTRA_deviceid = "deviceid";

    public static Intent getCallingIntent(Context context, String value,String deviceid){
        Intent callingIntent = new Intent(context, MainActivity.class);
        callingIntent.putExtra(EXTRA_KEY, value);
        callingIntent.putExtra(EXTRA_deviceid,deviceid);
        return callingIntent;
    }
    public void getPara(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key= extras.getString(EXTRA_KEY);
            deviceid=extras.getString(EXTRA_deviceid);
            // and get whatever type user account id is
        }
    }
}
