package com.example.jduff.glowup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        final float[] currentColor = new float[3];
        Color.RGBToHSV(element.getRedComponent(), element.getGreenComponent(), element.getBlueComponent(), currentColor);

        final SeekBar val = (SeekBar) findViewById(R.id.val_choiceSkbr);
        val.setProgress((int)(currentColor[2] * 100));

        final ColorPickerView colorView = (ColorPickerView)findViewById(R.id.colorPickerView);
        colorView.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                TextView preview = ((TextView) findViewById(R.id.colorPreviewView));
                float[] colorAr = new float[3];
                Color.colorToHSV(color, colorAr);
                colorAr[2] = (float)(val.getProgress() / 100.0);
                int finalColor = Color.HSVToColor(colorAr);
                preview.setBackgroundColor(finalColor);
                element.setComponents(Color.red(finalColor),Color.green(finalColor),Color.blue(finalColor));


            }
        });

        SeekBar sk = (SeekBar) findViewById(R.id.time_choiceSkbr);
        sk.setProgress((int)((element.getLength() / 1000.0) * 2) - 2);

        //changeColor();
        TextView tv = (TextView) findViewById(R.id.time_displayLbl);
        //Convert the SeekBar progress integer to the appropriate seconds value
        double seconds = (sk.getProgress()/ 2.0) + 1;
        tv.setText(Double.toString(seconds)); // set the label to the current setting


        //Set the seekbar to change the time when it is changed
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

        TextView colorPrev = (TextView) findViewById(R.id.colorPreviewView);
        colorPrev.setBackgroundColor(Color.HSVToColor(currentColor));
    }

    /**
     * changeColor - get the HSV values from the three seekbars and update the object colors preview
     */
     private void changeColor() {
         final ColorPickerView colorView = (ColorPickerView)findViewById(R.id.colorPickerView);
         float[] colors = new float[3];

         Color.colorToHSV(colorView.getColor(), colors);
//        //Get the current values of the three sliders
        int hue = (int)Math.floor(colors[0]);
        int sat = (int)Math.floor(colors[1] * 100);
        int val = ((SeekBar)findViewById(R.id.val_choiceSkbr)).getProgress();
         Log.d("Color", hue + ", " + sat + ", " + val);

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
