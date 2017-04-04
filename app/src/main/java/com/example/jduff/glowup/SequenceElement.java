package com.example.jduff.glowup;


import android.graphics.Point;
import android.icu.text.SelectFormat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * SequenceElement: Represents one element in the pattern for one group of lights
 *              Contains what color the lights should be and the length of
 * @author Jason Duffey
 * @version 1.0 - 10/2016
 *
 */

class SequenceElement implements Serializable{
    private int redComponent; //The red component of the color to display
    private int greenComponent; //The green component of the color to display
    private int blueComponent; //The blue component of the color to display
    private int length; //The length of time in milliseconds to display the color
    private int indexID; //The index for its position in the LightGroup, so that when returning it can fill in the right place
    private int x;
    private int y;

    //Constructors

    /**
     * Constructor for the object to pass in values for all the components
     * @param red [0-2550 value of the red component
     * @param green [0-255] value of the green component
     * @param blue [0-255] value of the blue component
     * @param milliseconds time in milliseconds the base should stay the specified color
     * @param index the index in the pattern that this element appears on the ring
     */
    SequenceElement(int red, int green, int blue, int milliseconds, int index) {
        redComponent = Math.max(Math.min(red, 255), 0);
        greenComponent = Math.max(Math.min(green, 255), 0);
        blueComponent = Math.max(Math.min(blue, 255), 0);
        length = Math.max(milliseconds, 1000);
        indexID = index;
        x = 0;
        y = 0;
    }

    /**
     * Constructor for the object with default values
     * @param index the index in the pattern that this element appears on the ring
     */
    SequenceElement(int index) {
        redComponent = 0;
        greenComponent = 0;
        blueComponent = 0;
        length = 1000;
        indexID = index;
        x = 0;
        y = 0;
    }

    /**
     * get the red component of the element
     * @return integer value [0-255] of the red component
     */
    int getRedComponent() {
        return redComponent;
    }

    /**
     * get the green component of the element
     * @return integer value [0-255] of the green component
     */
    int getGreenComponent() {
        return greenComponent;
    }

    /**
     * get the blue component of the element
     * @return integer value [0-255] of the blue component
     */
    int getBlueComponent() {
        return blueComponent;
    }

    /**
     * get the time in milliseconds of the element
     * @return integer value length to remain this color in milliseconds
     */
    int getLength() { return length; }


    /**
     * set the value of the red color component
     * @param red integer [0-255] of the red value of the color
     */
    private void setRedComponent(int red) {
        redComponent = Math.max(Math.min(red, 255), 0);
    }

    /**
     * set the value of the green color component
     * @param green integer [0-255] of the green value of the color
     */
    private void setGreenComponent(int green) {
        greenComponent = Math.max(Math.min(green, 255), 0);
    }

    /**
     * set the value of the blue color component
     * @param blue integer [0-255] of the blue value of the color
     */
    private void setBlueComponent(int blue) {
        blueComponent = Math.max(Math.min(blue, 255), 0);
    }

    /**
     * set all three color components that the ring should display
     * @param red integer [0-255] of the red value of the color
     * @param green integer [0-255] of the green value of the color
     * @param blue integer [0-255] of the blue value of the color
     */
    void setComponents(int red, int green, int blue) {
        setRedComponent(red);
        setGreenComponent(green);
        setBlueComponent(blue);
    }

    /**
     * set the length of time in milliseconds that the ring should remain its specified color
     * @param milliseconds time in milliseconds that the ring should remain its specified color
     */
    void setLength(int milliseconds) {
        length = Math.max(milliseconds, 1000);
    }

    /**
     * Convert the color to Hex format xxxxxx
     * @return the string containing the #XXXXXX format of the color chosen
     */
    public String toHex() {
        return (String.format("%02x", redComponent) + String.format("%02x", greenComponent) + String.format("%02x", blueComponent)).toUpperCase();
    }

    /**
     * toJSON - convert the element data to a JSON-like string
     * @return a JSON-like string containing the red, green, blue, and time components
     */
    String toJSON() {
        String str = "" + redComponent + "," + greenComponent + "," + blueComponent  + "," + length;
        return str;
    }
}
