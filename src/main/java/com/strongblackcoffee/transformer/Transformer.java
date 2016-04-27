package com.strongblackcoffee.transformer;

import java.util.List;

/**
 *
 */
public interface Transformer {
    
    public String getName();
    public String getDescription();
    public int getMinArgs();
    public int getMaxArgs();
    
    /**
     * Transforms raw using args.
     * @param raw 
     * @param args 
     * @return 
     */
    public String transform(String raw, List<String> args);
    
}
