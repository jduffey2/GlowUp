package org.onucs.glowup.glowup;

import org.junit.Test;

import static org.junit.Assert.*;
import org.onucs.glowup.glowup.SequenceElement;

import dalvik.annotation.TestTargetClass;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SequenceElementUnitTest {
    @Test
    public void instantiation_isCorrect() throws Exception {
        SequenceElement foo = new SequenceElement();

        assertEquals(foo.getRedComponent(), 0);
        assertEquals(foo.getGreenComponent(), 0);
        assertEquals(foo.getBlueComponent(), 0);
    }

    @Test
    public void instantiation2_isCorrect() throws Exception {
        SequenceElement foo = new SequenceElement(128,200,50);

        assertEquals(foo.getRedComponent(), 128);
        assertEquals(foo.getGreenComponent(), 200);
        assertEquals(foo.getBlueComponent(), 50);
    }

    @Test
    public void setters_isCorrect() throws Exception {
        SequenceElement foo = new SequenceElement();

        foo.setRedComponent(500);
        foo.setBlueComponent(-50);
        foo.setGreenComponent(128);

        assertEquals(foo.getRedComponent(), 255);
        assertEquals(foo.getGreenComponent(), 128);
        assertEquals(foo.getBlueComponent(), 0);
    }

    @Test
    public void setComponents_isCorrect() throws Exception {
        SequenceElement foo = new SequenceElement();
        foo.setComponents(70,140,210);

        assertEquals(foo.getRedComponent(), 70);
        assertEquals(foo.getGreenComponent(), 140);
        assertEquals(foo.getBlueComponent(), 210);
    }

    @Test
    public void toHex_isCorrect() throws Exception {
        SequenceElement foo = new SequenceElement(0,0,0);
        assertEquals("000000", foo.toHex());

        foo.setComponents(70, 140, 210);
        assertEquals("468CD2", foo.toHex());

        foo.setComponents(255, 255, 255);
        assertEquals("FFFFFF", foo.toHex());

        foo.setComponents(5, 10, 128);
        assertEquals("050A80", foo.toHex());
    }

    @Test
    public void length_isCorrect() throws Exception {
        
    }
}