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

/**
 * Ring_selection_activity - the main activity that allows a user to select a ring to modify
 * @author Jason Duffey
 * @version 1.0 - 01/2017
 */
public class Ring_selection_activity extends AppCompatActivity {

    private Base lightBase;
    private BluetoothAdapter bta;
    public final static String BASE = "com.example.jduff.glowup.BASE";
    public final static String RING = "com.example.jduff.glowup.RING";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_selection_activity);

        //Get the Base that is passed from the previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //Check to see if anything has been passed
        if(extras != null && extras.containsKey(BASE)) {
            lightBase = (Base)intent.getSerializableExtra(BASE);
        }
        //If not create a new base and add three rings to it
        else {
            lightBase = new Base();
            LightGroup outer = new LightGroup(BaseRingEnum.OUTER);
            LightGroup mid = new LightGroup(BaseRingEnum.MIDDLE);
            LightGroup inner = new LightGroup(BaseRingEnum.INNER);
            lightBase.addGroup(outer);
            lightBase.addGroup(mid);
            lightBase.addGroup(inner);
        }
    }

    /****************************************************************************************/
    // These methods are the event handlers for the buttons for editing a ring
    /****************************************************************************************/
    public void selectRingOuter(View view) {
        openLightGroupActivity(BaseRingEnum.OUTER);
    }

    public void selectRingMiddle(View view) {
        openLightGroupActivity(BaseRingEnum.MIDDLE);
    }

    public void selectRingInner(View view) {
        openLightGroupActivity(BaseRingEnum.INNER);
    }

    /*****************************************************************************************/

    //Start the Element_selection activity with the selected ring
    private void openLightGroupActivity(BaseRingEnum ring) {
        Intent intent = new Intent(this, Element_selection.class);
        intent.putExtra(BASE, lightBase);
        intent.putExtra(RING, ring);
        startActivity(intent);
    }

    //Send the data to the base
    public void uploadData(View view) {
        Log.d("TEST",lightBase.toJSON());
    }

}
