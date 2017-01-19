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

import com.github.stagirs.latex.lexical.Chain;

/**
 *
 * @author Dmitriy Malakhov
 */
public class CommandParam implements Item{
    private Chain text;
    private boolean optional;

    public CommandParam(Chain text, boolean optional) {
        this.text = text;
        this.optional = optional;
    }

    @Override
    public CommandParam getCopy() {
        return new CommandParam(text.getCopy(), optional);
    }

    public boolean isOptional() {
        return optional;
    }

    public Chain getText() {
        return text;
    }
    
    
    
    @Override
    public String toString() {
        if(optional){
            return "[" + text.toString() + "]";
        }else{
            return "{" + text.toString() + "}";
        }
    }
}
