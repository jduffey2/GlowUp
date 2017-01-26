package com.example.jduff.glowup;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * LightGroup - Represents an LED or LED group that can have a sequence programmed independently from
 *      the other groups, stores the array of sequence elements
 * Author: Jason Duffey
 * Date: 10/2016
 */

public class LightGroup implements Serializable{
    private ArrayList<SequenceElement> pattern;
    private BaseRingEnum ringID;

    public LightGroup(BaseRingEnum ring) {
        pattern = new ArrayList<>();
        ringID = ring;
    }

    public LightGroup(String jsonString) {
        fromJSON(jsonString);
    }

    public String toJSON() {
        String str = "{'ringID':" + ringID.ordinal() + ",'pattern':[";
        for (SequenceElement element: pattern) {
            str += element.toJSON() + ",";
        }
        if(pattern.size() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        str += "]}";

        return str;
    }

    //TODO: implement this
    private void fromJSON(String jsonString) {


    }

    public void addElement(SequenceElement element) {
        pattern.add(element);
    }

    public void removeElement(SequenceElement element) {
        if(pattern.contains(element)) {
            pattern.remove(element);
        }
    }

    public void removeElement(int index) {
        if(index < pattern.size()) {
            pattern.remove(index);
        }
    }

    public ArrayList<SequenceElement> getPattern() {
        return pattern;
    }

    public SequenceElement getElement(int index) {
        return pattern.get(index);
    }

    public void  updateElement(int index, SequenceElement element) {
        if(index >= pattern.size()) {
            pattern.add(index, element);
        } else {
            pattern.set(index, element);
        }
    }
}
