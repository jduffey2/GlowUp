package org.onucs.glowup.glowup;


/**
 * SequenceElement: Represents one element in the pattern for one group of lights
 *              Contains what color the lights should be and the length of
 * Created by Jason on 10/25/2016.
 */

public class SequenceElement {
    private int redComponent;
    private int greenComponent;
    private int blueComponent;
    private int length;

    //Constructors
    public SequenceElement(int red, int green, int blue, int seconds) {
        redComponent = Math.max(Math.min(red, 255), 0);
        greenComponent = Math.max(Math.min(green, 255), 0);
        blueComponent = Math.max(Math.min(blue, 255), 0);
        length = Math.max(seconds, 0);
    }
    public SequenceElement() {
        redComponent = 0;
        greenComponent = 0;
        blueComponent = 0;
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
    public void setLength(int seconds) {
        length = Math.max(seconds, 0);
    }

    //Other Methods
    public String toHex() {
        return (String.format("%02x", redComponent) + String.format("%02x", greenComponent) + String.format("%02x", blueComponent)).toUpperCase();
    }
}
