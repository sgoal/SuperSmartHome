/*
 * Copyright 2015 wulinan.
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
package com.tos.logical.relations.bt.composite;

import java.util.ArrayList;

import com.tos.logical.relations.bt.model.BTNode;

/**
 * BTComposite
 * Abstract class representing Composite node in a behavior tree
 * a Composite node have one or more children
 * Child nodes are represented inside an ArrayList
 * @author wulinan
 */
public abstract class BTComposite extends BTNode {
    
    
    
    /*
    * Initalize the child nodes arraylist
    */
    public BTComposite() {
        this.childrenNode = new ArrayList<BTNode>();
    }
    
    public BTComposite(String name) {
        super(name);
        this.childrenNode = new ArrayList<BTNode>();
    }
    
    
    
    
    public void setChildren(ArrayList<BTNode> nChildren) {
        this.childrenNode = nChildren;
    }
    
    public ArrayList<BTNode> getChildren() {
        return this.childrenNode;
    }
    
    /*
    * Add a ndoe at a specific location
    * @param the node (BTNode) to add
    * @param the specific location
    */
    public void addChildren(BTNode nNode, int position) {
        this.childrenNode.add(position, nNode);
    }
    
    /*
    * Add one node at the end of the list
    * @param the node (BTNode) to add at the end
    */
    public void addChild(BTNode nNode) {
        this.childrenNode.add(nNode);
    }
    
    public void add(BTNode node) {
        this.addChild(node);
    }
    
    @Override
    public BTNode getRunning() {
        
        BTNode runningNode;
        
        if (this.execution.isRunning()) {
            int size = this.childrenNode.size();
            for (int i=0; i<size;++i) {
                runningNode = this.childrenNode.get(i).getRunning();
                if (runningNode != null) {
                    return runningNode;
                }
            }
            // we didn't return any child, so we are the alst running node
            return this;
        }
        
        return null;
    }
}
