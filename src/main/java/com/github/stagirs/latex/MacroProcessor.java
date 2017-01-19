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
public class MacroProcessor {
    private static final Set<String> MACRO_COMMANDS = new HashSet<String>(){{
        add("\\newcommand");
        add("\\renewcommand");
        add("\\providecommand");
    }};
    private final Map<String, Chain> macro2command = new HashMap<>();
    
    public static void process(Chain text){
        new MacroProcessor().processText(text);
    }
    
    private void processText(Chain text){
        List<Item> newList = new ArrayList<>();
        for (Item item : text.getList()) {
            if(item instanceof Command){
                Command command = (Command) item;
                for (CommandParam commandParam : command.geParams()) {
                    processText(commandParam.getText());
                }
                if(macro2command.containsKey(command.getName())){
                    Chain copy = macro2command.get(command.getName()).getCopy();
                    copy.replace(command);
                    newList.addAll(copy.getList());
                    continue;
                }
            }
            if(item instanceof Group){
                processText(((Group) item).getText());
            }
            if(item instanceof Command && MACRO_COMMANDS.contains(((Command) item).getName())){
                CommandParam macro = ((Command) item).get(0);
                CommandParam command = ((Command) item).get(1);
                if(command.isOptional()){
                    command = ((Command) item).get(2);
                }
                macro2command.put(macro.getText().get(Command.class, 0).getName(), command.getText());
                continue;
            }
            newList.add(item);
        }
        text.setList(newList);
    }
}
