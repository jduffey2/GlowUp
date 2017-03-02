package com.example.jduff.glowup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Element_config - the application activity code for the activity that allows changing the color and time of an element
 * @author Jason Duffey
 * @version 1.0 - 01/2017
 */
public class Element_config extends AppCompatActivity {

    private Base lightBase;
    private BaseRingEnum ring;
    private int index;
    private SequenceElement element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_config);

        //Get the Base object, BaseRingEnum, and Element Index that is passed by the Element Selection Activity
        Intent i = getIntent();
        lightBase = (Base)i.getSerializableExtra(Element_selection.BASE);
        ring = (BaseRingEnum)i.getSerializableExtra(Element_selection.RING);
        index = i.getIntExtra(Element_selection.ELEMENT, -1);

        //If the index passed in > 0 then the element already exists, if not then it is a new element that needs to be created
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

        //Set the seekbar to change the Hue when it is changed
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

        //Set the seekbar to change the Saturation when it is changed
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

        //Set the seekbar to change the Value when it is changed
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

    /**
     * changeColor - get the HSV values from the three seekbars and update the object colors preview
     */
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

    /**
     * colorChoiceReturn - Start the Element_selection activity after a color selection has been made
     * @param view - The ButtonView that triggered the event
     */
    public void colorChoiceReturn(View view) {
        //Update the lightBase
        LightGroup group = lightBase.getGroup(ring);
        group.updateElement(index, element);
        lightBase.updateGroup(ring, group);

        //Start to the color selection activity
        Intent intent = new Intent(this, Element_selection.class);
        intent.putExtra(Ring_selection_activity.BASE, lightBase);
        intent.putExtra(Ring_selection_activity.RING, ring);
        startActivity(intent);
    }

    /**
     * deleteElement - Delete the selected element and start the Element_selection activity
     * @param view - The ButtonView that triggered the event
     */
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
