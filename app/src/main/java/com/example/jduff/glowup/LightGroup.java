package com.example.jduff.glowup;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * LightGroup - Represents an LED or LED group that can have a sequence programmed independently from
 *      the other groups, stores the array of sequence elements
 * @author Jason Duffey
 * @version 1.0 - 10/2016
 */

public class LightGroup implements Serializable{
    private ArrayList<SequenceElement> pattern;
    private BaseRingEnum ringID;

    public LightGroup(BaseRingEnum ring) {
        pattern = new ArrayList<>();
        ringID = ring;
    }

    /**
     * toJSON - convert the LightGroup object into a JSON-like string
     * @return - a JSON-like String of the LightGroup data
     */
    public String toJSON() {
        String str = "{" + ringID.ordinal() + ",[";
        //iterate through each element of the LightGroup calling the Element toJSON method
        for (SequenceElement element: pattern) {
            str += element.toJSON() + ",";
        }
        if(pattern.size() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        str += "]}";

        return str;
    }

    /**
     * addElement - add a new SequenceElement to the LightGroup
     * @param element - the SequenceElement to add to the LightGroup
     */
    public void addElement(SequenceElement element) {
        pattern.add(element);
    }

    /**
     * removeElement - remove a SequenceElement based on the element passed in
     * @param element - the element to remove from the LightGroup
     */
    public void removeElement(SequenceElement element) {
        if(pattern.contains(element)) {
            pattern.remove(element);
        }
    }

    /**
     * removeElement - remove a SequenceElement based on its index in the pattern
     * @param index - the index of the SequenceElement to remove
     */
    public void removeElement(int index) {
        if(index < pattern.size()) {
            pattern.remove(index);
        }
    }

    /**
     * getPattern - a getter for the ArrayList of the SequenceElements
     * @return - the ArrayList containing the SequenceElements in the pattern
     */
    public ArrayList<SequenceElement> getPattern() {
        return pattern;
    }

    /**
     * getElement - get a SequenceElement based on a passed index
     * @param index - the index of the SequenceElement to return
     * @return - the SequenceElement at the specified index
     */
    public SequenceElement getElement(int index) {
        return pattern.get(index);
    }

    /**
     * updateElement - update a SequenceElement with a new Element
     * @param index - the index of the Element to replace
     * @param element - the SequenceElement to replace the old one
     */
    public void  updateElement(int index, SequenceElement element) {
        if(index >= pattern.size()) {
            pattern.add(index, element);
        } else {
            pattern.set(index, element);
        }
    }
}
