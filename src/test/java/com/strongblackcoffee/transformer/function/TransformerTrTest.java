/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strongblackcoffee.transformer.function;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tsingle
 */
public class TransformerTrTest {
    
    public TransformerTrTest() {
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

    @Test public void testTransform01() { performTransformTest("abcabc",new String[] {"bcd","ghi"},"aghagh"); }
    @Test public void testTransform02() { performTransformTest("Happiness",new String[] {"a-zA-Z","n-za-mN-ZA-M"},"Unccvarff"); }
    
    void performTransformTest(String initial, String[] args, String expected) {
        assertEquals(initial,expected,(new TransformerTr()).transform(initial, Arrays.asList(args)));
    }

    @Test public void testExpandRanges01() { performExpandRangesTest("a-h","abcdefgh"); }
    @Test public void testExpandRanges02() { performExpandRangesTest("-ade","-ade"); }
    @Test public void testExpandRanges03() { performExpandRangesTest("ade-","ade-"); }
    @Test public void testExpandRanges04() { performExpandRangesTest("abp-tz","abpqrstz"); }
    @Test public void testExpandRanges05() { performExpandRangesTest("abt-pz","abt-pz"); }
    
    void performExpandRangesTest(String unexpanded, String expected) {
        assertEquals(unexpanded, expected, TransformerTr.expandRanges(unexpanded));
    }
    
}
