package com.example.jduff.glowup;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

public class Element_selection extends AppCompatActivity {
    public final static String BASE = "com.example.jduff.glowup.BASE";
    public final static String RING = "com.example.jduff.glowup.RING";
    public final static String ELEMENT = "com.example.jduff.glowup.ELEMENT";

    private Base lightBase;
    private BaseRingEnum ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_selection);
        Intent i = getIntent();
        lightBase = (Base)i.getSerializableExtra(Ring_selection_activity.BASE);
        ring = (BaseRingEnum)i.getSerializableExtra(Ring_selection_activity.RING);

        //Change the Label to specify what ring is being edited
        String ringLbl;
        switch (ring) {
            case OUTER:
                ringLbl = "OUTER RING";
                break;
            case MIDDLE:
                ringLbl = "MIDDLE RING";
                break;
            case INNER:
                ringLbl = "INNER RING";
                break;
            default:
                ringLbl = "UNKNOWN";
        }
        TextView text = (TextView)findViewById(R.id.Ring_ID_Lbl);
        text.setText(ringLbl);

        //Populate the Activity with the Elements from the ring that the user can select from
        LightGroup group = lightBase.getGroup(ring);

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.lightGroupParentLayout);

        for (SequenceElement element: group.getPattern()) {
            LinearLayout frame = new LinearLayout(this);

            Button btn = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10,0, 10);
            btn.setLayoutParams(params);
            frame.addView(btn);
            setOnClick(btn, group.getPattern().indexOf(element));
            btn.setText("" + (element.getLength() / 1000.0));

            //Set the text color based on the brightness of the background color
            float[] bgColor = new float[3];
            Color.RGBToHSV(element.getRedComponent(), element.getGreenComponent(), element.getBlueComponent(), bgColor);
            if(bgColor[2] < 0.5) {
                btn.setTextColor(Color.WHITE);
            }
            btn.setBackgroundColor(Color.argb(255,element.getRedComponent(), element.getGreenComponent(), element.getBlueComponent()));



            parentLayout.addView(frame);
        }
//        //Add a Frame for adding a new element to the pattern
//        LinearLayout frame = new LinearLayout(this);
//        Button btn = new Button(this);
//        btn.setText("Add +");
//        setOnClick(btn, -1);
//        frame.addView(btn);
//        parentLayout.addView(frame);
    }

    private void setOnClick(final Button btn, final int elementNo) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfig(elementNo);
            }
        });
    }

    public void returnToBase(View view) {
        Intent intent = new Intent(this, Ring_selection_activity.class);
        intent.putExtra(BASE, lightBase);
        startActivity(intent);
    }

    public void addElement(View view) {
        openConfig(-1);
    }

    public void openConfig(int elementNo) {
        //Open the Element Selection Activity from here
        Intent intent = new Intent(Element_selection.this, Element_config.class);

        intent.putExtra(BASE, lightBase);
        intent.putExtra(RING, ring);
        intent.putExtra(ELEMENT, elementNo);
        startActivity(intent);
    }
}
