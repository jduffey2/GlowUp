package com.example.jduff.glowup;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Base.java - A class for storing all the data for a pattern of lights to be sent to the base
 *      It will contain all the different lights and lengths for the entire pattern, can be saved to the
 *      device or sent to the base
 * @author Jason Duffey
 * @version 1.0 - 11/2016
 */

public class Base implements Serializable{
    private ArrayList<LightGroup> circuit; //Store the different Light Groups that can have different patterns
    private long patternID;
    private String patternName;


    public Base() {
        circuit = new ArrayList<>();
        patternID = -1;
        patternName = "New Pattern";
    }

    /**
     * addGroup - Add a LightGroup to the Base, to be used if different sized bases are used
     * @param group - a LightGroup object to be added to the base
     */
    public void addGroup(LightGroup group) {
        circuit.add(group);
    }

    /**
     * removeGroup - removes a LightGroup from the base
     * @param group - the LightGroup object that should be removed from the ArrayList of LightGroups in the base
     */
    public void removeGroup(LightGroup group) {
        circuit.remove(group);
    }

    public long getPatternID() {
        return patternID;
    }

    public void setPatternID(long id) {
        patternID = id;
    }

    public String getPatternName() { return patternName;}

    public void setPatternName(String name) {
        patternName = name;
    }

    /**
     * toJSON - converts the pattern data into a JSON-like structured string
     * @return - a JSON-like string containing an array of the different LightGroup data
     */
    public String toJSON() {
        String str = "{[";

        //Iterate through the LightGroups in the circuit and call their toJSON methods
        for (LightGroup group: circuit) {
            str += group.toJSON() + ",";
        }

        str = str.substring(0,str.length() - 1) + "]}";
        return str;
    }

    /**
     * getGroup - get the LightGroup object at a certain index in the ArrayList
     * @param index - the index of the LightGroup to retrieve from the ArrayList
     * @return - the LightGroup object at the selected index
     */
    public LightGroup getGroup(int index) {
        return circuit.get(index);
    }

    /**
     * getGroup - get the LightGroup object that is indexed based on the BaseRingEnum value
     * @param index - the BaseRingEnum value of the LightGroup to retrieve
     * @return - the LightGroup object with the BaseRingEnum
     */
    public LightGroup getGroup(BaseRingEnum index) {
        switch (index) {
            case OUTER:
                return circuit.get(0); //The Outer ring is at index 0
            case MIDDLE:
                return circuit.get(1); //The middle ring is at index 1
            case INNER:
                return circuit.get(2); //The innder ring is at index 2
            default:
                return null;
        }
    }

    /**\
     * updateGroup - update a LightGroup with a new object that has new information
     * @param ring - the BaseRingEnum of the LightGroup that should be updated
     * @param group - a LightGroup object that is going to replace the current LightGroup at the selected index
     */
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
                //Log an error if a correct Enum is not passed
                Log.e("BaseRingEnum Error","Could not update the light Group");
        }
    }
}
