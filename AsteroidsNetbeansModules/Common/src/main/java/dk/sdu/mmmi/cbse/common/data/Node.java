/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.utils.Array;

/**
 *
 * @author rasmusstamm
 */
public class Node {
    private Array<Connection<Node>> connections = new Array<Connection<Node>>();
    private int index;
    private int type; 
    
    public Node() {
        index = Node.Indexer.getIndex();
    }
    
    public void createConnection(Node toNode, float cost) {
        connections.add(new Connection(this, toNode, cost));
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    private static class Indexer {
        private static int index = 0;
        
        public static int getIndex(){
            return index++;
        }
    }
    
    
}
