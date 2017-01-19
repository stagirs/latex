package com.github.stagirs.latex.lexical;

import com.github.stagirs.latex.lexical.item.Command;
import com.github.stagirs.latex.lexical.item.CommandParam;
import com.github.stagirs.latex.lexical.item.Group;
import com.github.stagirs.latex.lexical.item.Item;
import com.github.stagirs.latex.lexical.item.Placeholder;
import com.github.stagirs.latex.lexical.item.PlainText;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class Chain{
    
    private List<Item> list = new ArrayList();
    private StringBuilder current = new StringBuilder();

    
    public Chain append(char c){
        current.append(c);
        return this;
    }

    public List<Item> getList() {
        return list;
    }
    
    
    
    public Chain add(Item o){
        flush();
        list.add(o);
        return this;
    }
    
    public Chain flush(){
        if(current.length() != 0){
            list.add(new PlainText(current.toString()));
            current = new StringBuilder();
        }
        return this;
    }

    
    public <T> T get(Class<T> c, int index){
        return list.size() <= index ? null : (T) list.get(index);
    }
    
    public <T> T get(int index){
        return list.size() <= index ? null : (T) list.get(index);
    }

    public int size(){
        return list.size();
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
    
    public void replace(Command command){
        List<Item> list = new ArrayList();
        for (Item item : this.list) {
            if(item instanceof Placeholder){
                list.addAll(command.geParams().get(((Placeholder)item).getIndex() - 1).getText().getCopy().getList());
            }else if(item instanceof Command){
                for(CommandParam cp : ((Command)item).geParams()){
                    cp.getText().replace(command);
                }
                list.add(item);
            }else if(item instanceof Group){
                ((Group)item).getText().replace(command);
                list.add(item);
            }else{
                list.add(item);
            }
        }
        this.list = list;
    }

    public Chain getCopy() {
        Chain text = new Chain();
        for (Item item : list) {
            text.list.add(item.getCopy());
        }
        return text;
    }

    @Override
    public String toString() {
        return StringUtils.join(list, "");
    }
}
