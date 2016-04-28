package com.strongblackcoffee.transformer;

import com.strongblackcoffee.transformer.function.TransformerTr;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class TransformerRegistry {
    
    public TransformerRegistry() {
        register("lowercase", new Transformer() {
            @Override public String getName() { return "lowercase"; }
            @Override public String getDescription() { return "Converts all chars to lowercase."; }
            @Override public int getMinArgs() { return 0;}
            @Override public int getMaxArgs() { return 0; }
            @Override public String transform(String raw, List<String> args) { return raw.toLowerCase(); }
        });
        register("uppercase", new Transformer() {
            @Override public String getName() { return "uppercase"; }
            @Override public String getDescription() { return "Converts all chars to uppercase."; }
            @Override public int getMinArgs() { return 0;}
            @Override public int getMaxArgs() { return 0; }
            @Override public String transform(String raw, List<String> args) { return raw.toUpperCase(); }
        });
        register("tr", new TransformerTr());
    }
    
    private Map<String,Transformer> registry = new TreeMap<>();
    
    public void register(String transformerMapClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName(transformerMapClassName);
        TransformerMap tm = (TransformerMap) c.newInstance();
        register(tm);
    }
    
    public void register(TransformerMap transformers) {
        register(transformers.getMap());
    }
    
    public void register(Map<String,Transformer> transformers) {
        registry.putAll(transformers);
    }
    
    public void register(String transformerName, Transformer transformer) {
        registry.put(transformerName, transformer);
    }
    
    public void unregister(String transformerName) {
        registry.remove(transformerName);
    }
    
    Map<String,Transformer> getRegistry() {
        return registry;
    }
    
    @Override public String toString() {
        return registry.toString();
    }
    
}
