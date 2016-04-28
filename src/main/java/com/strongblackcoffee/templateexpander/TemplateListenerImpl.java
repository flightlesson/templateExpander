package com.strongblackcoffee.templateexpander;

import com.strongblackcoffee.transformer.Transformer;
import com.strongblackcoffee.transformer.TransformerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
class TemplateListenerImpl implements TemplateListener {
    
    static class Transformation {
        private String name;
        private List<String> args = new ArrayList<>();
        Transformation(String name) {
            this.name = name;
        }
        void addArg(String arg) {
            args.add(arg);
        }
        
        @Override public String toString() {
            StringBuilder sb = new StringBuilder(name);
            for (String a: args) { sb.append(":").append(quote(a)); }
            return sb.toString();
        }
        
        /**
         * Adds quotes, but only if needed.
         * @param unquoted
         * @return 
         */
        static String quote(String unquoted) {
            for (int c: new int[] { ' ', '\t', '{', '}', '|', ':', '\\', '"' }) {
                if (unquoted.indexOf(c) >= 0) return "\"" + unquoted.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\\\"") + "\"";
            }
            return unquoted;
        }
    }
    
    private final TransformerFactory transformerFactory;
    
    /**
     * 
     * @param transformerFactory
     */
    TemplateListenerImpl(TransformerFactory transformerFactory) {
        this.transformerFactory = transformerFactory;
    }
    
    
    private boolean verbose = true;
    private StringBuilder expansion;
    private Map<String,List<String>> replaceables = null;
    private TemplateParser parser = null;
    
    /**
     * 
     * @param template
     * @param replaceables
     * @return 
     */
    String expand(String template, Map<String,List<String>> replaceables) {
        this.replaceables = replaceables;
        expansion = null;
       
        parser = new TemplateParser(new CommonTokenStream(new TemplateLexer(new ANTLRInputStream(template))));
        TemplateParser.TemplateContext templateContext = parser.template();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, templateContext);
        return expansion.toString();
    }
    
    @Override public void enterTemplate(TemplateParser.TemplateContext ctx) {
        if (verbose) System.out.println("enterTemplate: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser));
        expansion = new StringBuilder();
    }
    
    @Override public void exitText(TemplateParser.TextContext ctx) {
        if (verbose) System.out.println("exitText: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser));
        expansion.append(ctx.getText());
    }
    
    private String key = null;
    private List<Transformation> transformations = null;
    private Transformation currentTransformation;
    
    @Override public void enterReplaceable(TemplateParser.ReplaceableContext ctx) {
        if (verbose) System.out.println("enterReplaceable: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
        transformations = new ArrayList<>();
    }

    @Override public void enterKey(TemplateParser.KeyContext ctx) {
        if (verbose) System.out.println("enterKey: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
        key = ctx.getText();
        System.out.println("... got replaceable key="+key);
    }
    
    @Override public void exitKey(TemplateParser.KeyContext ctx) {
        if (verbose) System.out.println("exitKey: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }
    
    @Override public void exitReplaceable(TemplateParser.ReplaceableContext ctx) {
        if (verbose) System.out.println("exitReplaceable: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
        if (verbose) System.out.println("... expanding replaceable \""+ctx.getText()+"\" with key="+key);
        List<String> valueList = replaceables.get(key);
        if (valueList == null) {
            expansion.append(ctx.getText());
        } else {
            for (Transformation t: transformations) {
                Transformer transformer = transformerFactory.getTransformer(t.name);
                valueList = transformer.transform(valueList, t.args);
            }
            expansion.append(StringUtils.join(valueList, " "));
        }
    }
    
    @Override public void enterTransformation(TemplateParser.TransformationContext ctx) {
        if (verbose) System.out.println("enterTransformation: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
        currentTransformation = new Transformation(ctx.getText());
        transformations.add(currentTransformation);
    }

    @Override public void exitTransformation(TemplateParser.TransformationContext ctx) {
        if (verbose) System.out.println("exitTransformation: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }
    
    @Override public void enterPlainarg(TemplateParser.PlainargContext ctx) { 
        if (verbose) System.out.println("enterPlainarg: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
        currentTransformation.addArg(ctx.getText());
    }

    @Override public void exitTemplate(TemplateParser.TemplateContext ctx) {
        if (verbose) System.out.println("exitTemplate: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void enterText(TemplateParser.TextContext ctx) {
        if (verbose) System.out.println("enterText: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void enterTarg(TemplateParser.TargContext ctx) {
        if (verbose) System.out.println("enterTarg: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void exitTarg(TemplateParser.TargContext ctx) {
        if (verbose) System.out.println("exitTarg: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void exitPlainarg(TemplateParser.PlainargContext ctx) {
        if (verbose) System.out.println("exitPlainarg: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void enterQuotedarg(TemplateParser.QuotedargContext ctx) {
        if (verbose) System.out.println("enterQuotedarg: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
        currentTransformation.addArg(ctx.getText());
    }

    @Override public void exitQuotedarg(TemplateParser.QuotedargContext ctx) {
        if (verbose) System.out.println("exitQuotedarg: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void enterUnexpected_rc(TemplateParser.Unexpected_rcContext ctx) {
        if (verbose) System.out.println("enterUnexpected_rc: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void exitUnexpected_rc(TemplateParser.Unexpected_rcContext ctx) {
        if (verbose) System.out.println("exitUnexpected_rc: text=\""+ctx.getText()+"\", ctx=\""+ctx.toStringTree(parser)+"\"");
    }

    @Override public void visitTerminal(TerminalNode tn) {
    }

    @Override public void visitErrorNode(ErrorNode en) {
        System.out.println("visitErrorNode: text="+en.getText()+", ctx="+en.toStringTree(parser));
    }

    @Override public void enterEveryRule(ParserRuleContext prc) {
        if (verbose) System.out.println("enterEveryRule: text="+prc.getText()+", ctx="+prc.toStringTree(parser));
    }

    @Override public void exitEveryRule(ParserRuleContext prc) {
        if (verbose) System.out.println("exitEveryRule: text="+prc.getText()+", ctx="+prc.toStringTree(parser));
    }
}
