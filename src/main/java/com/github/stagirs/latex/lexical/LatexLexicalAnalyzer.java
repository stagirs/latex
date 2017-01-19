package com.github.stagirs.latex.lexical;

import com.github.stagirs.latex.Iterator;
import com.github.stagirs.latex.lexical.item.Command;
import com.github.stagirs.latex.lexical.item.CommandParam;
import com.github.stagirs.latex.lexical.item.Comment;
import com.github.stagirs.latex.lexical.item.Group;
import com.github.stagirs.latex.lexical.item.Placeholder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LatexLexicalAnalyzer {
    //секции, где не нужен $$: eqnarray equation 
    
    private Iterator i;

    public static Chain parse(String text) {
        text = text.replace("\r", "");
        LatexLexicalAnalyzer lla = new LatexLexicalAnalyzer();
        lla.i = new Iterator(text);
        return lla.processText(null);
    }
    
    private Chain processText(Character endChar){
        Chain text = new Chain();
        while(i.hasNext()){
            char c = i.next();
            if(endChar != null && c == endChar){
                return text.flush();
            }
            switch(c){
                case '$': 
                    if(i.next(1) != '$'){
                        text.add(new Command("$", Collections.EMPTY_LIST, false)); 
                    }else{
                        i.next();
                        text.add(new Command("$$", Collections.EMPTY_LIST, false)); 
                    }
                    break;
                case '\n': 
                    if(i.hasNext() && i.next(1) == '\n'){
                        text.add(new Command("\n\n", Collections.EMPTY_LIST, false));
                    }
                    break;    
                case '{': text.add(new Group(processText('}'))); break;
                case '\\': text.add(processCommand()); break;
                case '%': text.add(processComment()); break;
                case '#': text.add(new Placeholder(i.next() - '0')); break;
                default: text.append(c);
            }
        }
        return text.flush();
    }
    
    private Command processCommand(){
        char c = i.next();
        List<CommandParam> params = new ArrayList();
        StringBuilder name = new StringBuilder();
        name.append('\\').append(c);
        boolean star = false;
        if(Character.isLetter(c)){
            boolean eq = false;
            while(i.hasNext()){
                c = i.next();
                switch(c){
                    case '{': params.add(new CommandParam(processText('}'), false)); break;
                    case '[': params.add(new CommandParam(processText(']'), true)); break;
                    case '=': eq = true; name.append(c); break;
                    case '*': star = true; break;
                    default:
                        if(Character.isLetter(c)){
                            name.append(c);
                        }else{
                            if(eq && Character.isDigit(c)){
                                name.append(c);
                            }else{
                                i.prev();
                                return new Command(name.toString(), params, star);
                            }
                        }
                }
            }
        }    
        return new Command(name.toString(), params, star);
    }
    
    private Comment processComment(){
        StringBuilder text = new StringBuilder();
        while(i.hasNext()){
            char c = i.next();
            if(c == '\n'){
                break;
            }
            text.append(c);
        }
        return new Comment(text.toString());
    }
}
