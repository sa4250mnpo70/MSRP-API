package se.lbroman.msrp.impl.data;

import org.junit.Test;
import static org.junit.Assert.*;

public class ByteArraysTest {
    
    ByteArrays arrays = new ByteArrays();
    
    @Test
    public void compateTwoArrays() {
        byte[] test = "Test".getBytes();
        assertTrue(arrays.equalsSubRange(test, 0, test, 0, 4));
    }
    
    @Test
    public void compateTwoArraysDifferentSubRange() {
        byte[] test1 = "Test".getBytes();
        byte[] test2 = " Test".getBytes();
        assertTrue(arrays.equalsSubRange(test1, 0, test2, 1, 4));
    }

}
