package com.strongblackcoffee.templateexpander;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The set of key:value items that can be replaced in a template.
 */
public class Replaceables {
    
    final private Map<String,List<String>> map;
    
    public Replaceables() {
        map = new TreeMap<>();
    }
    
    public Replaceables(Map<String,List<String>> map) {
        this.map = map;
    }
    
    public Replaceables remove(String key) {
        map.remove(key);
        return this;
    }
    
    public Replaceables put(String key, String value) {
        List<String> list = map.get(key);
        if (list == null) {
            list = new ArrayList<String>();
        }
        list.add(value);
        map.put(key,list);
        return this;
    }
    
    public Replaceables put(String key, Collection<String> values) {
        List<String> list = map.get(key);
        if (list == null) {
            list = new ArrayList<String>();
        }
        list.addAll(values);
        map.put(key,list);
        return this;
    }
    
    @Override public String toString() {
        if (map instanceof TreeMap) return map.toString();
        return (new TreeMap(map)).toString();
    }
    
    Map<String,List<String>> getMap() {
        return map;
    }
}
