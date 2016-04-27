package com.strongblackcoffee.transformer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MockTransformerMap implements TransformerMap {

    private Map<String,Transformer> map;
    
    public MockTransformerMap() {
        map = new HashMap<>();
        map.put("abc",new NilTransformer() { @Override public String getName() {return "abc";} });
        map.put("def",new NilTransformer() { @Override public String getName() {return "def";} });
    }
    
    @Override
    public Map<String, Transformer> getMap() {
        return map;
    }
    
}
