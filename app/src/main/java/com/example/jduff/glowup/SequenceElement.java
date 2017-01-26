package com.example.jduff.glowup;


import android.icu.text.SelectFormat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * SequenceElement: Represents one element in the pattern for one group of lights
 *              Contains what color the lights should be and the length of
 * Author: Jason Duffey
 * Date: 10/2016
 *
 */

public class SequenceElement implements Serializable{
    private int redComponent; //The red component of the color to display
    private int greenComponent; //The green component of the color to display
    private int blueComponent; //The blue component of the color to display
    private int length; //The length of time in milliseconds to display the color
    private int indexID; //The index for its position in the LightGroup, so that when returning it can fill in the right place

    //Constructors
    public SequenceElement(int red, int green, int blue, int milliseconds, int index) {
        redComponent = Math.max(Math.min(red, 255), 0);
        greenComponent = Math.max(Math.min(green, 255), 0);
        blueComponent = Math.max(Math.min(blue, 255), 0);
        length = Math.max(milliseconds, 1000);
        indexID = index;
    }
    public SequenceElement(int index) {
        redComponent = 0;
        greenComponent = 0;
        blueComponent = 0;
        length = 1000;
        indexID = index;
    }
    public SequenceElement(String jsonString) {
        fromJSON(jsonString);
    }

    //Getters
    public int getRedComponent() {
        return redComponent;
    }
    public int getGreenComponent() {
        return greenComponent;
    }
    public int getBlueComponent() {
        return blueComponent;
    }
    public int getLength() { return length; }

    //Setters
    public void setRedComponent(int red) {
        redComponent = Math.max(Math.min(red, 255), 0);
    }
    public void setGreenComponent(int green) {
        greenComponent = Math.max(Math.min(green, 255), 0);
    }
    public void setBlueComponent(int blue) {
        blueComponent = Math.max(Math.min(blue, 255), 0);
    }
    public void setComponents(int red, int green, int blue) {
        setRedComponent(red);
        setGreenComponent(green);
        setBlueComponent(blue);
    }
    public void setLength(int milliseconds) {
        length = Math.max(milliseconds, 1000);
    }

    //Other Methods
    public String toHex() {
        return (String.format("%02x", redComponent) + String.format("%02x", greenComponent) + String.format("%02x", blueComponent)).toUpperCase();
    }
    public String toJSON() {
        String str = "{'ID':" + indexID + ",'length':" + length + ",'red':" + redComponent + ",'green':" + greenComponent + ",'blue':" + blueComponent + "}";
        return str;
    }

    //TODO: implement this
    private void fromJSON(String jsonString) {

    }

}
