/*
 * Copyright 2017 Dmitriy Malakhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stagirs.latex;

import com.github.stagirs.latex.lexical.LatexLexicalAnalyzer;
import com.github.stagirs.latex.lexical.Chain;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Dmitriy Malakhov
 */
public class MacroProcessorTest {
    @Test
    public void test1(){
        Chain text = LatexLexicalAnalyzer.parse("\\newcommand{\\tg\n}{\\mathop{\\rm tg}\\nolimits} \\tg \\tg");
        MacroProcessor.process(text);
        assertEquals(text.size(), 4);
        assertEquals(text.toString(), "\\mathop{\\rm tg }\\nolimits\\mathop{\\rm tg }\\nolimits");
    }
    
    @Test
    public void test2(){
        Chain text = LatexLexicalAnalyzer.parse("\\newcommand{\\tg\n}[2]{\\mathop{\\rm tg #1 #2}\\nolimits} \\tg{agr1}{\\tg{arg2}{arg3}}");
        MacroProcessor.process(text);
        assertEquals(text.size(), 2);
        assertEquals(text.toString(), "\\mathop{\\rm tg   agr1 \\mathop{\\rm tg   arg2  arg3 }\\nolimits}\\nolimits");
    }
    
    @Test
    public void test3(){
        Chain text = LatexLexicalAnalyzer.parse("\\newtheorem{name1}{1}\\newtheorem{name2}[counter]{2}\\begin{name1}\\begin{name2}");
        MacroBlockProcessor.process(text);
        assertEquals(text.size(), 4);
        assertEquals(text.toString(), "\\begin{ name1 } 1 \\begin{ name2 } 2 ");
    }
}
