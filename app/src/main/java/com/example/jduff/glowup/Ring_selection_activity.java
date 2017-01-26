package com.example.jduff.glowup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Ring_selection_activity extends AppCompatActivity {

    private Base lightBase;
    public final static String BASE = "com.example.jduff.glowup.BASE";
    public final static String RING = "com.example.jduff.glowup.RING";


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
}
