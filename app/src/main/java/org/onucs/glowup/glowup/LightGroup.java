package org.onucs.glowup.glowup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 10/25/2016.
 */

public class LightGroup {
    private ArrayList<SequenceElement> pattern;

    public LightGroup() {
        pattern = new ArrayList<SequenceElement>();
    }

    // TODO: Implement this after deciding if we want to do transfer over bluetooth as JSON, XML, or just some formatted String
    public String toJSON() {
        return "";
    }

    public void addElement(SequenceElement element) {
        pattern.add(element);
    }

    public void removeElement(SequenceElement element) {
        pattern.remove(element);
    }

    public void removeElement(int index) {
        pattern.remove(index);
    }
}
