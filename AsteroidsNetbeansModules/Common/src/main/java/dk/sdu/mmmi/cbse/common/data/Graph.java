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
    private Array<Node> nodes = new Array<Node>();
    
    public Graph(int mapWidth, int tileHeigth, int tileWidth, Array<Node> nodes) {
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
    
    public Node getNodeByPosition(int x, int y) {
        int modX = x / tileWidth;
        int modY = y / tileHeight;
        
        return nodes.get(tileWidth * modY + modX);
    }
    
}
