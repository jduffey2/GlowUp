package com.example.jduff.glowup;

import android.bluetooth.*;
import android.util.Log;

import org.json.JSONStringer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Base.java - A class for storing all the data for a pattern of lights to be sent to the base
 *      It will contain all the different lights and lengths for the entire pattern, can be saved to the
 *      device or sent to the base
 * Author: Jason Duffey
 * Date: 11/2016
 */

public class Base implements Serializable{
    private ArrayList<LightGroup> circuit; //Store the different Light Groups that can have different patterns

    public Base() {
        circuit = new ArrayList<>();
    }

    public Base(String jsonString) {
        fromJSON(jsonString);
    }

    public void addGroup(LightGroup group) {
        circuit.add(group);
    }
    public void removeGroup(LightGroup group) {
        circuit.remove(group);
    }

    //TODO: Implement this. This should use the android bluetooth library to send the pattern instructions to the base
    public void send() {
        String data = toJSON();
    }

    public String toJSON() {
        String str = "{[";

        for (LightGroup group: circuit) {
            str += group.toJSON() + ",";
        }

        str = str.substring(0,str.length() - 1) + "]}";
        return str;
    }

    //TODO: Implement this after determining how this will be stored in memory
    private void fromJSON(String jsonString) {

    }

    //TODO: Implement this, to save this class data to the device
    public void save() {
    }

    public LightGroup getGroup(int index) {
        return circuit.get(index);
    }

    public LightGroup getGroup(BaseRingEnum index) {
        switch (index) {
            case OUTER:
                return circuit.get(0);
            case MIDDLE:
                return circuit.get(1);
            case INNER:
                return circuit.get(2);
            default:
                return null;
        }
    }

    public void updateGroup(BaseRingEnum ring, LightGroup group) {
        switch (ring) {
            case OUTER:
                circuit.set(0, group);
                break;
            case MIDDLE:
                circuit.set(1, group);
                break;
            case INNER:
                circuit.set(2, group);
                break;
            default:
                Log.e("BaseRingEnum Error","Could not update the light Group");
        }
    }
}
