package com.strongblackcoffee.templateexpander;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class TemplateExpanderTest {
    
    public TemplateExpanderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void templateTest1() {
        Replaceables replaceables = new Replaceables();
        replaceables.put("def", "DEF");
        conductTemplateTest("abc {def} xyz",replaceables,"abc DEF xyz");
    } 
    @Test
    public void templateTest2() {
        Replaceables replaceables = new Replaceables();
        replaceables.put("def", "DEF");
        conductTemplateTest("abc {def|ghi} xyz", replaceables, "abc DEF|ghi xyz"); 
    }
    @Test
    public void templateTest3() {
        Replaceables replaceables = new Replaceables();
        replaceables.put("def", "DEF");
        conductTemplateTest("abc {def|ghi|jkl:\"mno\":pqr} xyz", replaceables, "abc DEF|ghi|jkl:mno:pqr xyz");
    }
    @Test
    public void templateTest4() {
        Replaceables replaceables = new Replaceables();
        replaceables.put("def", "DEF");
        conductTemplateTest("abc {def|ghi|jkl:\"mn\\\"o\":pqr} xyz", replaceables, "abc DEF|ghi|jkl:\"mn\\\"o\":pqr xyz");
    }
    
    void conductTemplateTest(String template, Replaceables replaceables, String expected) {
        TemplateExpander t = new TemplateExpander();
        String actual = t.expand(template,replaceables);
        assertEquals(template, expected, actual);
    }

    //@Test
    public void lexerTest() {
        String raw = "zzz}.{ab.c_d|efg:h.i:\"j k\"}";
        TemplateLexer lexer = new TemplateLexer(new ANTLRInputStream(raw));
        Vocabulary vocabulary = lexer.getVocabulary();
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        //TemplateParser parser = new TemplateParser(tokens);
        
        System.out.println("tokens="+tokens.toString());
        for (Token tok: tokens.getTokens()) {
            int type = tok.getType();
            System.out.println("token displayName-" + vocabulary.getDisplayName(type)
                  + ", literalName=" + vocabulary.getLiteralName(type)
                  + ", symbolicName=" + vocabulary.getSymbolicName(type)
                  + ", text=" + tok.getText());
        }
    }
    
    // @Test
    public void parserTest() {
        testParser("a+b={c|d.e}");
    }
    
    
    void testParser(String template) {
        TemplateParser parser = new TemplateParser(new CommonTokenStream(new TemplateLexer(new ANTLRInputStream(template))));
        Vocabulary v = parser.getVocabulary();
        TemplateParser.TemplateContext templateContext = parser.template();
        ParseTreeWalker walker = new ParseTreeWalker();
        TemplateListener listener = new TemplateListener() {
            @Override
            public void enterTemplate(TemplateParser.TemplateContext ctx) {
                System.out.println("enterTemplate: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitTemplate(TemplateParser.TemplateContext ctx) {
                System.out.println("exitTemplate: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterText(TemplateParser.TextContext ctx) {
                System.out.println("enterText: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitText(TemplateParser.TextContext ctx) {
                System.out.println("exitText: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterReplaceable(TemplateParser.ReplaceableContext ctx) {
                System.out.println("enterReplaceable: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitReplaceable(TemplateParser.ReplaceableContext ctx) {
                System.out.println("exitReplaceable: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterKey(TemplateParser.KeyContext ctx) {
                System.out.println("enterKey: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitKey(TemplateParser.KeyContext ctx) {
                System.out.println("exitKey: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterTransformation(TemplateParser.TransformationContext ctx) {
                System.out.println("enterTransformation: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitTransformation(TemplateParser.TransformationContext ctx) {
                System.out.println("exitTransformation: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterTarg(TemplateParser.TargContext ctx) {
                System.out.println("enterArg: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitTarg(TemplateParser.TargContext ctx) {
                System.out.println("exitArg: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterPlainarg(TemplateParser.PlainargContext ctx) {
                System.out.println("enterPlainarg: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitPlainarg(TemplateParser.PlainargContext ctx) {
                System.out.println("exitPlainarg: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterQuotedarg(TemplateParser.QuotedargContext ctx) {
                System.out.println("enterQuotedarg: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitQuotedarg(TemplateParser.QuotedargContext ctx) {
                System.out.println("exitQuotedarg: " + ctx.toStringTree(parser));
            }

            @Override
            public void enterUnexpected_rc(TemplateParser.Unexpected_rcContext ctx) {
                System.out.println("enterUnexpected_rc: " + ctx.toStringTree(parser));
            }

            @Override
            public void exitUnexpected_rc(TemplateParser.Unexpected_rcContext ctx) {
                System.out.println("exitUnexpected_rc: " + ctx.toStringTree(parser));
            }

            @Override
            public void visitTerminal(TerminalNode tn) {
                // System.out.println("visitTerminal: " + tn.toStringTree(parser));
            }

            @Override
            public void visitErrorNode(ErrorNode en) {
                System.out.println("visitErrorNode: " + en.toStringTree(parser));
            }

            @Override
            public void enterEveryRule(ParserRuleContext prc) {
                System.out.println("enterEveryRule: " + prc.toStringTree(parser));
            }

            @Override
            public void exitEveryRule(ParserRuleContext prc) {
                System.out.println("exitEveryRule: " + prc.toStringTree(parser));
            }
        };
        walker.walk(listener, templateContext);
    }
    
}
