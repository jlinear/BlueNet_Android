package com.example.marco.bluenet_01;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;

public class MainActivity extends Activity {

    // TODO: Put messages into a database, do chat view
    // TODO: Make sidebar in map view to display closest users

    /*// Globals
    TextView nameView;
    SharedPreferences bluetoothInfo;
    String originalBluetoothName;

    public void chatClick(View view){
        Intent i = new Intent(MainActivity.this, Chat2Activity.class);
        startActivity(i);
    }

    public void mapClick(View view){
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
    }

    // saves original bluetooth name the first time app runs
    private void setSharedPreferences(){
        //nameView = findViewById(R.id.savedBluetoothName);
        bluetoothInfo = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        if(bluetoothInfo.getString("originalName", "").isEmpty()){
            try {
                bluetoothInfo.edit().putString("originalName", BluetoothAdapter.getDefaultAdapter().getName()).apply();
                nameView.setText(bluetoothInfo.getString("originalName", ""));
            }catch (Exception e){
                e.printStackTrace();
                bluetoothInfo.edit().putString("originalName", "").apply();
            }
        }else {
            nameView.setText(bluetoothInfo.getString("originalName", ""));
        }
    }
    */
    // Requests location permissions - required for bluetooth searching
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkPermissions(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }
    /*
    // Enable bluetooth if it is not already enabled
    private void enableBluetooth(){
        if(BluetoothAdapter.getDefaultAdapter().isEnabled()){
            showToast("Bluetooth On");
        }else{
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.startActivityForResult(enableIntent, 0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            showToast("Bluetooth On");
        }else{
            showToast("Bluetooth Failed");
        }
    }
    */
    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
        //enableBluetooth();

        //setSharedPreferences();
        //originalBluetoothName = bluetoothInfo.getString("originalName", "");

    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void loginClick(View view) {
        Intent i = new Intent(MainActivity.this, navigationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        hideSoftKeyboard();
        startActivity(i);
        finish();
    }
}
