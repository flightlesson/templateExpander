/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strongblackcoffee.transformer;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TransformerRegistryTest {
    
    public TransformerRegistryTest() {
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
     * Test of register method, of class TransformerRegistry.
     */
    @Test
    public void testXXX() throws Exception {
        TransformerRegistry r = new TransformerRegistry();
        try {
            r.register("com.strongblackcoffee.transformer.MockTransformerMap");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            fail(ex.getMessage());
        }
        Map<String,Transformer> m = r.getRegistry();
        assertTrue("contains abc",m.containsKey("abc"));
        
    }
}
