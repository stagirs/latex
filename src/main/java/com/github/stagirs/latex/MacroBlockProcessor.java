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

import com.github.stagirs.latex.lexical.Chain;
import com.github.stagirs.latex.lexical.item.Command;
import com.github.stagirs.latex.lexical.item.CommandParam;
import com.github.stagirs.latex.lexical.item.Group;
import com.github.stagirs.latex.lexical.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Dmitriy Malakhov
 */
public class MacroBlockProcessor {
    private static final Set<String> MACRO_BLOCK_COMMANDS = new HashSet<String>(){{
        add("\\newtheorem");
        add("\\newtheorem*");
    }};
    private final Map<String, Chain> macroblock2command = new HashMap<>();
    
    
    public static void process(Chain text){
        new MacroBlockProcessor().processText(text);
    }
    
    private void processText(Chain text){
        List<Item> newList = new ArrayList<>();
        for (Item item : text.getList()) {
            if(item instanceof Command){
                Command command = (Command) item;
                if(command.getName().equals("\\begin") && command.get(0) != null){
                    String blockName = command.get(0).getText().toString();
                    if(macroblock2command.containsKey(blockName)){
                        newList.add(item);
                        newList.addAll(macroblock2command.get(blockName).getCopy().getList());
                        continue;
                    }
                }
            }
            if(item instanceof Group){
                processText(((Group) item).getText());
            }
            if(item instanceof Command && MACRO_BLOCK_COMMANDS.contains(((Command) item).getName())){
                Command command = (Command) item;
                CommandParam macro = command.get(0);
                CommandParam newcommand = command.get(1);
                if(newcommand == null){
                    continue;
                }
                if(newcommand.isOptional()){
                    newcommand = command.get(2);
                }
                macroblock2command.put(macro.getText().toString(), newcommand.getText());
                continue;
            }
            newList.add(item);
        }
        text.setList(newList);
    }
}
