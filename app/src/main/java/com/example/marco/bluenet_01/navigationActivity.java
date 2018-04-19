package com.example.marco.bluenet_01;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;

public class navigationActivity extends AppCompatActivity
        implements
        mapsFragment.OnFragmentInteractionListener,
        chatFragment.OnFragmentInteractionListener,
        profileFragment.OnFragmentInteractionListener,
        protocolFragment.OnFragmentInteractionListener,
        aboutFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener{

    private FusedLocationProviderClient mFusedLocationClient;
    BleBasicService BleBasic;
    //SwitchCompat discoverable;

    Fragment mapsFragment = new mapsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.darker_blue)));


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set navigation drawer header
        View headerView = navigationView.getHeaderView(0);
        TextView navUser = (TextView) headerView.findViewById(R.id.userIDNavBar);
        navUser.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("userName", ""));
        TextView navStatus = (TextView) headerView.findViewById(R.id.statusNavBar);
        navStatus.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("statusPref", ""));

        /* From http://www.arjunsk.com/android/how-to-use-fragment-layout-and-scroll-layout-in-android-studio/ */
        //NOTE:  Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.nav_maps);

        //NOTE:  Open fragment1 initially.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new mapsFragment());
        ft.commit();

        //SwitchCompat discoverable = (SwitchCompat) findViewById(R.id.switcher);
        //discoverable.setSwitchPadding(40);
        //discoverable.setOnCheckedChangeListener(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        BleBasic = new BleBasicService(getApplicationContext(),this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            /*Intent exitIntent = new Intent(Intent.ACTION_MAIN);
            exitIntent.addCategory( Intent.CATEGORY_HOME );
            exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(exitIntent);*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_maps) {
            fragment = new mapsFragment();
        } else if (id == R.id.nav_chat) {
            fragment = new chatFragment();
        } else if (id == R.id.nav_profile) {
            fragment = new profileFragment();
        } else if (id == R.id.nav_protocol) {
            fragment = new protocolFragment();
        } else if (id == R.id.nav_about) {
            fragment = new aboutFragment();
        }

        //NOTE: Fragment changing code
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(String title) {
        // NOTE:  Code to replace the toolbar title based current visible fragment
        getSupportActionBar().setTitle(title);
    }

//    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if(b){
//                Log.d("DDDD", "switch is on!");
//            }
//        }
//    };

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
//
//        if(isChecked){
//            Log.d("DDD" , "haha");
////            mFusedLocationClient.getLastLocation()
////                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
////                        @Override
////                        public void onSuccess(Location location) {
////                            // Got last known location. In some rare situations this can be null.
////                            if (location != null) {
////                                AdvertisementPayload outPayload = new AdvertisementPayload();
////                                outPayload.setUserID(getIntent().getStringExtra("userName"));
////                                outPayload.setLocation(location);
////                                byte[] out = outPayload.getPayload();
////                                BleBasic.startLeAdvertising(out);
////                            }else{
////                                throw new RuntimeException("Switch:" + " null location");
////                            }
////                        }
////                    });
//        }else{
//            //BleBasic.stopAdvertising();
//        }
//    }

}
