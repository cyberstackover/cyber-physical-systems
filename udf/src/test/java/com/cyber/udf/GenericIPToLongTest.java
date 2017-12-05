package com.cyber.udf;

import com.cyber.udf.GenericIPToLong;
import junit.framework.TestCase;

public class GenericIPToLongTest extends TestCase {
    
    /**
     * Test of IPToLong method, of class GenericIPToLong.
     */
    public void testIPToLong() {
        System.out.println("IPToLong");
        String addr = "0.0.0.0";
        GenericIPToLong instance = new GenericIPToLong();
        long expResult = 0L;
        long result = instance.IPToLong(addr);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of IPToLong method, of class GenericIPToLong.
     */
    public void testInvalidIPToLong() {
        System.out.println("IPToLong");
        String addr = "000.0";
        GenericIPToLong instance = new GenericIPToLong();
        long expResult = 0L;
        long result = instance.IPToLong(addr);
        assertEquals(expResult, result);
    }
    
}
