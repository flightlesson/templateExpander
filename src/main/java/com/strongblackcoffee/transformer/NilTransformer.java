package com.strongblackcoffee.transformer;

import java.util.List;

/**
 * Allows {@link Transformer} classes to be be "extends NilTransformer" instead of "implements Transformer".
 */
public class NilTransformer implements Transformer {

    @Override
    public String getName() {
        return "nil";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public int getMaxArgs() {
        return 0;
    }

    @Override
    public List<String> transform(List<String> raw, List<String> args) {
        return raw;
    }
    
}
