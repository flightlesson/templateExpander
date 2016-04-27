package com.strongblackcoffee.templateexpander;

import com.strongblackcoffee.transformer.TransformerFactory;
import com.strongblackcoffee.transformer.TransformerRegistry;
import java.util.List;
import java.util.Map;

/**
 * Expands templates. A template is a String that may contain replaceables, for example:
 * <pre>cp {files} {destdir}</pre>
 * Here, {files} and {destdir} are replaceables.
 * 
 * <p>When a template is expanded, replaceables are, well, replaced with their values.
 * A replaceable may contain transformatations and transformations may take arguments, for example:
 * <pre>{key|trans1|trans2:arg1:arg2|trans3}</pre>
 * Here, the replaceable is expanded by looking up key in a {@link Replaceables} and then processing
 * the value through the trans1, then trans2, and finally trans3 transformations. The trans2 transformation 
 * takes two arguments, arg1 and arg2.
 * 
 * Outside a replaceable, { and } must be escaped, i.e. \{ and \{.
 * 
 * Transformation arguments must be quoted wint " if they contain anything other than letters, digits, ...
 * Inside quotes \\ and \" to represent the respective characters.
 */
public class TemplateExpander {
    
    private TemplateListenerImpl wrapped;
    
    public TemplateExpander() {
        this(new TransformerRegistry());
    }
    
    /**
     * @param transformerFactory 
     */
    public TemplateExpander(TransformerRegistry transformerRegistry) {
        wrapped = new TemplateListenerImpl(new TransformerFactory(transformerRegistry));
    }
    
    /**
     * 
     * @param template
     * @param replaceables
     * @return the expanded template
     */
    public String expand(String template, Replaceables replaceables) {
        return wrapped.expand(template,replaceables.getMap());
    }
    
}
