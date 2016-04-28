package com.strongblackcoffee.templateexample;

import com.strongblackcoffee.templateexpander.Replaceables;
import com.strongblackcoffee.templateexpander.TemplateExpander;
import com.strongblackcoffee.transformer.Transformer;
import com.strongblackcoffee.transformer.TransformerRegistry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TemplateDemo {
    
    private TemplateExpander expander;
    
    public void initializeTemplateExpander() {
        expander = new TemplateExpander(new TransformerRegistry());
    }
    
    TransformerRegistry getTransformerRegistry() {
        TransformerRegistry registry =  new TransformerRegistry();
        registry.register("rot13",new Transformer() {
            @Override public String getName() { return "rot13"; }
            @Override public String getDescription() { return "A poor encryptor/decryptor."; }
            @Override public int getMinArgs() { return 0; }
            @Override public int getMaxArgs() { return 0; }
            @Override public List<String> transform(List<String> rawList, List<String> args) {
                List<String> results = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for (String raw: rawList) {
                    for (char c: raw.toCharArray()) {
                        if       (c >= 'a' && c <= 'm') c += 13;
                        else if  (c >= 'A' && c <= 'M') c += 13;
                        else if  (c >= 'n' && c <= 'z') c -= 13;
                        else if  (c >= 'N' && c <= 'Z') c -= 13;
                        sb.append(c);
                    }
                    results.add(sb.toString());
                }
                return results;
            }
        });
        return registry;
    }
    
    public String expandTemplate(String template, Replaceables replaceables) {
        return expander.expand(template, replaceables);
    }
    
}
