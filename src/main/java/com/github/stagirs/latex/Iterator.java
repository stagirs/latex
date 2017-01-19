package com.github.stagirs.latex;

public class Iterator {
    private String text;
    private int i = -1;
    
    public Iterator(String text) {
        this.text = text;
    }
    
    public char next(){
        return text.charAt(++i);
    }
    
    public char next(int shift){
        return text.charAt(i + shift);
    }
    
    public char prev(){
        return text.charAt(--i);
    }
    
    public char prev(int shift){
        return text.charAt(i - shift);
    }
    
    
    public boolean hasNext(){
        return i + 1 < text.length();
    }
    
    public char current(){
        return text.charAt(i);
    }
}
