package com.strongblackcoffee.transformer;

/**
 *
 */
public class TransformerFactory {
    
    final private TransformerRegistry registry;
    
    public TransformerFactory(TransformerRegistry registry) {
        this.registry = registry;
    }
    
    public Transformer getTransformer(String name) {
        return registry.getRegistry().get(name);
    }
    
}
