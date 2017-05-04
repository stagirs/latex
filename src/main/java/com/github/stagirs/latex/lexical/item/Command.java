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
package com.github.stagirs.latex.lexical.item;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Dmitriy Malakhov
 */
public class Command implements Item{
    private String name;
    private List<CommandParam> params;
    private boolean star;

    public Command(String name, List<CommandParam> list, boolean star) {
        this.name = name;
        this.params = list;
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return params.size();
    }

    public List<CommandParam> geParams() {
        return params;
    }
    
    public String getFirstParamText(){
        if(params.isEmpty()){
            return "";
        }
        return params.get(0).getText().toString().trim();
    }
    
    public CommandParam get(int i){
        return i < params.size() ? params.get(i) : null;
    }

    @Override
    public Item getCopy() {
        List<CommandParam> list = new ArrayList<>();
        for (CommandParam commandParam : this.params) {
            list.add(commandParam.getCopy());
        }
        return new Command(name, list, star);
    }
    
    

    @Override
    public String toString() {
        return name + StringUtils.join(params, "");
    }
}
