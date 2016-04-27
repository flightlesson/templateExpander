/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strongblackcoffee.templateexpander;

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
public class ReplaceableTransformationTest {
    
    public ReplaceableTransformationTest() {
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

    @Test public void testQuote01() {
        String unquoted = "abc";
        assertEquals(unquoted, "abc", TemplateListenerImpl.Transformation.quote(unquoted));
    }
    
    @Test public void testQuote02() {
        
        String unquoted = "a b c";
        assertEquals(unquoted, "\"a b c\"", TemplateListenerImpl.Transformation.quote(unquoted));
    }
    
    @Test public void testQuote03() {
        
        String unquoted = "a \b c";
        assertEquals(unquoted, "\"a \b c\"", TemplateListenerImpl.Transformation.quote(unquoted));
    }
    
    @Test public void testQuote04() {
        
        String unquoted = "a \t \\ \t c";
        assertEquals(unquoted, "\"a \t \\\\ \t c\"", TemplateListenerImpl.Transformation.quote(unquoted));
    }
    
    @Test public void testQuote05() {
        
        String unquoted = "a \\\\b c";
        assertEquals(unquoted, "\"a \\\\\\\\b c\"", TemplateListenerImpl.Transformation.quote(unquoted));
    }
    
    @Test public void testQuote06() {
        
        String unquoted = "a \\ \" \\ c";
        assertEquals(unquoted, "\"a \\\\ \\\" \\\\ c\"", TemplateListenerImpl.Transformation.quote(unquoted));
    }
    
}
