package com.example.jduff.glowup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Element_config extends AppCompatActivity {

    private Base lightBase;
    private BaseRingEnum ring;
    private int index;
    private SequenceElement element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_config);

        Intent i = getIntent();
        lightBase = (Base)i.getSerializableExtra(Element_selection.BASE);
        ring = (BaseRingEnum)i.getSerializableExtra(Element_selection.RING);
        index = i.getIntExtra(Element_selection.ELEMENT, -1);

        if(index >= 0) {
            element = lightBase.getGroup(ring).getElement(index);
        }
        else {
            index = lightBase.getGroup(ring).getPattern().size();
            element = new SequenceElement(index);
        }

        //Set the sliders to the elements current values (default values if it is a new element
        float[] currentColor = new float[3];
        Color.RGBToHSV(element.getRedComponent(), element.getGreenComponent(), element.getBlueComponent(), currentColor);

        SeekBar hue = (SeekBar) findViewById(R.id.hue_choiceSkbr);
        hue.setProgress((int)currentColor[0]);

        SeekBar sat = (SeekBar) findViewById(R.id.sat_choiceSkbr);
        sat.setProgress((int)(currentColor[1] * 100));

        SeekBar val = (SeekBar) findViewById(R.id.val_choiceSkbr);
        val.setProgress((int)(currentColor[2] * 100));

        SeekBar time = (SeekBar) findViewById(R.id.time_choiceSkbr);
        time.setProgress((int)((element.getLength() / 1000.0) * 2) - 2);

        changeColor();
        TextView tv = (TextView) findViewById(R.id.time_displayLbl);
        //Convert the SeekBar progress integer to the appropriate seconds value
        double seconds = (time.getProgress()/ 2.0) + 1;
        tv.setText(Double.toString(seconds)); // set the label to the current setting


        //Set the seekbar to change the time when it is changed
        SeekBar sk = (SeekBar) findViewById(R.id.time_choiceSkbr);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tv = (TextView) findViewById(R.id.time_displayLbl);
                //Convert the SeekBar progress integer to the appropriate seconds value
                double seconds = (progress / 2.0) + 1;
                tv.setText(Double.toString(seconds)); // set the label to the current setting

                //set the SE element time to the appropriate value
                element.setLength((int) (seconds * 1000));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        val.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeColor() {
        //Get the current values of the three sliders
        int hue = ((SeekBar)findViewById(R.id.hue_choiceSkbr)).getProgress();
        int sat = ((SeekBar)findViewById(R.id.sat_choiceSkbr)).getProgress();
        int val = ((SeekBar)findViewById(R.id.val_choiceSkbr)).getProgress();

        //Get the color int for the given HSV
        float[] hsv = new float[] {hue,(float)(sat / 100.0),(float)(val / 100.0)};
        int choice = Color.HSVToColor(hsv);

        //Display the current color in the space
        TextView preview = ((TextView) findViewById(R.id.colorPreviewView));
        preview.setBackgroundColor(choice);

        //Change the Sequence Element Data to the new color Color.HSVToColor(hsv)
        element.setComponents(Color.red(choice),Color.green(choice),Color.blue(choice));
    }

    public void colorChoiceReturn(View view) {
        //Update the lightBase
        LightGroup group = lightBase.getGroup(ring);
        group.updateElement(index, element);
        lightBase.updateGroup(ring, group);

        //Return to the color selection activity
        Intent intent = new Intent(this, Element_selection.class);
        intent.putExtra(Ring_selection_activity.BASE, lightBase);
        intent.putExtra(Ring_selection_activity.RING, ring);
        startActivity(intent);
    }

    public void deleteElement(View view) {
        //Update the lightBase
        LightGroup group = lightBase.getGroup(ring);
        group.removeElement(index);
        lightBase.updateGroup(ring, group);

        //Return to the color selection activity
        Intent intent = new Intent(this, Element_selection.class);
        intent.putExtra(Ring_selection_activity.BASE, lightBase);
        intent.putExtra(Ring_selection_activity.RING, ring);
        startActivity(intent);
    }
}
