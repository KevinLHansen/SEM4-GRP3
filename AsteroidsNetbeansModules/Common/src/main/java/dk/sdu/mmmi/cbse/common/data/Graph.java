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
public class Graph {

    private int mapWidth = 0;
    private int tileHeight = 0;
    private int tileWidth = 0;
    private Array<Array<Node>> nodes = new Array<Array<Node>>();
    
    public Graph(int mapWidth, int tileHeight, int tileWidth, Array<Array<Node>> nodes) {
        this.mapWidth = mapWidth;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.nodes = nodes;
    }
    
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }
    
    public int getNodeCount() {
        return nodes.size;
    }

    public Array<Array<Node>> getNodes() {
        return nodes;
    }
    
    public Node getNodeByPosition(int x, int y) {
        
        System.out.println("\n\n\n\n\n\n\n");
        System.out.println("Y? SIZE:" + nodes.size);
        System.out.println("X? SIZE:" + nodes.get(0).size);
        int modX = x / tileWidth;
        int modY = y / tileHeight;
        System.out.println(modX + " " + modY);
        
        return nodes.get(modY).get(modX);
        
        //return nodes.get(tileWidth * modY + modX);
    }
    
}
