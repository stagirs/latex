package com.github.stagirs.latex.lexical;



import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author NPSP-MalakhovDA
 */
public class LatexLexicalAnalyzerTest {
    @Test
    public void test1(){
        Chain text = LatexLexicalAnalyzer.parse("\\newcommand{\\tg\n}{\\mathop{\\rm tg}\\nolimits}");
        assertEquals(text.size(), 1);
        assertEquals(text.toString(), "\\newcommand{\\tg}{\\mathop{\\rm tg}\\nolimits}");
    }
    
    @Test
    public void test2(){
        Chain text = LatexLexicalAnalyzer.parse("\\righthyphenmin=2 % comment text \n\\righthyphenmin=3");
        assertEquals(text.size(), 4);
        assertEquals(text.toString(), "\\righthyphenmin=2 % comment text \n\\righthyphenmin=3");
    }
    
}
