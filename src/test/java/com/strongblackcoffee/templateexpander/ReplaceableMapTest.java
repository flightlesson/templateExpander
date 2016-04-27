/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strongblackcoffee.templateexpander;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ReplaceableMapTest {
    
    public ReplaceableMapTest() {
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

    @Test
    public void testPut() {
        System.out.println("put");
        String key = "";
        String value = "";
        
        Map<String,List<String>> expected = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("A1");
        expected.put("a", list);
        list = new ArrayList<>();
        list.add("B1");
        list.add("B2");
        expected.put("b",list);
        Replaceables instance = (new Replaceables()).put("b","B1").put("a","A1").put("b", "B2");
        Replaceables expResult = null;
        assertEquals("keys={\"a\",\"b\"}",expected.keySet(),instance.getMap().keySet());
        assertEquals("a:{A1}",expected.get("a"),instance.getMap().get("a"));
        assertEquals("b:{B1,B2}",expected.get("b"),instance.getMap().get("b"));
        assertEquals("{a=[A1], b=[B1, B2]}",instance.toString());
    }
    
    @Test
    public void testToString() {
        Replaceables instance;
        
        instance = (new Replaceables()).put("dog","D").put("cat","C").put("bird","B").put("horse","H").put("goat","G").put("dog","OG");
        assertEquals("{bird=[B], cat=[C], dog=[D, OG], goat=[G], horse=[H]}",instance.toString());
    }
}
