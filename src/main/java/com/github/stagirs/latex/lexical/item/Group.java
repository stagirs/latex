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
public class Group implements Item{
    private Chain text;

    public Group(Chain text) {
        this.text = text;
    }

    @Override
    public Group getCopy() {
        return new Group(text.getCopy());
    }

    public Chain getText() {
        return text;
    }

    
    @Override
    public String toString() {
        return "{" + text.toString() + "}";
    }
}
