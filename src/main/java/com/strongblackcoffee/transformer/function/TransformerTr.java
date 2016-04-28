package com.strongblackcoffee.transformer.function;

import com.strongblackcoffee.transformer.Transformer;
import java.util.List;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class TransformerTr implements Transformer {

    @Override
    public String getName() {
        return "tr";
    }

    @Override
    public String getDescription() {
        return "{word|tr:a-zA-Z:n-za-mN-ZA-M} perfoms a ROT13 trasformation on word's value.";
    }

    @Override
    public int getMinArgs() {
        return 2;
    }

    @Override
    public int getMaxArgs() {
        return 2;
    }

    @Override
    public String transform(String raw, List<String> args) {
        String searchChars  = expandRanges(args.get(0));
        String replaceChars = expandRanges(args.get(1));
        return StringUtils.replaceChars(raw,searchChars,replaceChars);
    }
    
    static String expandRanges(String unexpanded) {
        StringBuilder expansion = new StringBuilder();
        
        for (int i=0; i < unexpanded.length(); ++i) {
            if (i+2 < unexpanded.length() 
                && unexpanded.charAt(i+1)=='-'
                && unexpanded.charAt(i) < unexpanded.charAt(i+2)) {
                char c = unexpanded.charAt(i);
                while (c <= unexpanded.charAt(i+2)) {
                    expansion.append(c);
                    ++c;
                }
                i+=2;
            } else {
                expansion.append(unexpanded.charAt(i));
            }
        }
        
        return expansion.toString();
    }
}
