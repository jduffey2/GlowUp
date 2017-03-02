package com.example.jduff.glowup;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Set;

public class Ring_selection_activity extends AppCompatActivity {

    private Base lightBase;
    private BluetoothAdapter bta;
    public final static String BASE = "com.example.jduff.glowup.BASE";
    public final static String RING = "com.example.jduff.glowup.RING";
    public final static int REQUEST_ENABLE_BT = 1;
    private MyReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_selection_activity);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null && extras.containsKey(BASE)) {
            lightBase = (Base)intent.getSerializableExtra(BASE);
        }
        else {
            lightBase = new Base();
            LightGroup outer = new LightGroup(BaseRingEnum.OUTER);
            LightGroup mid = new LightGroup(BaseRingEnum.MIDDLE);
            LightGroup inner = new LightGroup(BaseRingEnum.INNER);
            lightBase.addGroup(outer);
            lightBase.addGroup(mid);
            lightBase.addGroup(inner);
        }
            pairDevice();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
    }

    public void selectRingOuter(View view) {
        openLightGroupActivity(BaseRingEnum.OUTER);
    }

    public void selectRingMiddle(View view) {
        openLightGroupActivity(BaseRingEnum.MIDDLE);
    }

    public void selectRingInner(View view) {
        openLightGroupActivity(BaseRingEnum.INNER);
    }

    private void openLightGroupActivity(BaseRingEnum ring) {
        Intent intent = new Intent(this, Element_selection.class);
        intent.putExtra(BASE, lightBase);
        intent.putExtra(RING, ring);
        startActivity(intent);
    }

    public void uploadData(View view) {
        Log.d("TEST",lightBase.toJSON());
    }

    public void pairDevice() {

        mReceiver = new MyReceiver();

        if(bta == null) {
            bta = BluetoothAdapter.getDefaultAdapter();
            if(bta == null) {
                Log.d("Bluetooth Incompatible", "Device does not support Bluetooth");
                return;
            }

            if(!bta.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            //Discovering Devices
            Set<BluetoothDevice> pairedDevices = bta.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress();

                    Log.d("Device",deviceName + ":" + deviceHardwareAddress);
                }
            }
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(BluetoothDevice.ACTION_FOUND);
//            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//
//            registerReceiver(mReceiver, filter);
//            bta.startDiscovery();

        }
    }
}
