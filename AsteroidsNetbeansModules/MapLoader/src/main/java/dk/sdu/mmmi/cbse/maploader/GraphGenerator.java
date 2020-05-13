/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.maploader;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Node;

/**
 *
 * @author rasmusstamm
 */
public class GraphGenerator {
    
    public static Graph generateGraph(TiledMap map){
        Array<Array<Node>> nodes = new Array<Array<Node>>();
        TiledMapTileLayer tiles = (TiledMapTileLayer) map.getLayers().get("objects");
        
        int mapHeight = TileLoader.getMapHeight();
        int mapWidth = TileLoader.getMapWidth();
        int tileWidth = TileLoader.getTileWidth();
        int tileHeight = TileLoader.getTileHeight();
        
        for (int y = 0; y < mapHeight; y++) {
            nodes.add(new Array<Node>());
            for (int x = 0; x < mapWidth; x++) {
                
                Node node = new Node();
                node.setType(Node.Type.FLOOR);
                nodes.get(y).add(node);
            }
        }
        
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                
                Node targetNode = nodes.get(y).get(x);
                if (target == null) {
                    if(y != 0 && down == null) {
                        Node downNode = nodes.get(y-1).get(x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if(y != mapHeight -1 && up == null) {
                        Node upNode = nodes.get(y+1).get(x);
                        targetNode.createConnection(upNode, 1);
                    }
                    if(x != 0 && left == null) {
                        Node leftNode = nodes.get(y).get(x-1);
                        targetNode.createConnection(leftNode, 1);
                    }
                    if(x != mapWidth - 1 && right == null) {
                        Node rightNode = nodes.get(y).get(x+1);
                        targetNode.createConnection(rightNode, 1);
                    }
                }
                else {
                    targetNode.setType(Node.Type.WALL);
                } 
            }
        }
        
        return new Graph(mapWidth, tileHeight, tileWidth, nodes);
    }
}
