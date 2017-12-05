package com.cyber.api.job;

import com.cyber.api.job.LeakyBucket;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LeakyBucketTest {

    public LeakyBucketTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fillOrConsume method, of class LeakyBucket.
     */
    @Test
    public void testFillOrConsume() {
        System.out.println("fillOrConsume");
        String key = "test";
        List data = new ArrayList();
        data.add(1);
        data.add(2);

        LeakyBucket instance = new LeakyBucket(3);

        List filled = instance.fillOrConsume(key, data);
        assertEquals(filled, null);

        List data2 = new ArrayList();    
        data2.add(3);
        List consume = instance.fillOrConsume(key, data2);
        assertEquals(consume.size(), 3);

    }

}
