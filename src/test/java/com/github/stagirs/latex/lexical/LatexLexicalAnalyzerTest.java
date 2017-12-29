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
        assertEquals(text.toString(), "\\newcommand{\\tg}{\\mathop{\\rm tg }\\nolimits}");
    }
    
    @Test
    public void test2(){
        Chain text = LatexLexicalAnalyzer.parse("\\righthyphenmin=2 % comment text \n\\righthyphenmin=3");
        assertEquals(text.size(), 3);
        assertEquals(text.toString(), "\\righthyphenmin=2% comment text \n\\righthyphenmin=3");
        text = LatexLexicalAnalyzer.parse("\\begin{equation} \\label{e8} |\\chi_S(r)-np_{r}|\\le \\Delta_{r},\n \\end {equation}");
        assertEquals(text.size(), 11);
        text = LatexLexicalAnalyzer.parse("\\label{begin}\n" +
                                                "\n" +
                                                "\n" +
                                                "Данная работа посвящена установлению условий разрешимости и изучению свойств решений\n" +
                                                "бесконечных систем алгебраических уравнений вида\n" +
                                                "\\begin{equation}");
        assertEquals(text.size(), 5);
        
        
    }
    
    @Test
    public void test3(){
        Chain text = LatexLexicalAnalyzer.parse("\\begin{exam}Нелинейная система");
        assertEquals(text.size(), 2);
    }
}
