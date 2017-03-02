package com.example.jduff.glowup;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jduff on 2/1/2017.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            Log.d("Discovery","Started Discovering Devices");
        }
        else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Log.d("Discovery","Finished Discovering Devices");

        }
        else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent.
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address


            Log.d("Found Device", deviceName + " : " + deviceHardwareAddress);
            //Add to a list to allow the user to pick which device to choose, along with already
            //paired devices. Once user picks a device, pair it/open an active connection. When
            //user hits the upload button send the data over bluetooth via this connection
        }
    }

}
