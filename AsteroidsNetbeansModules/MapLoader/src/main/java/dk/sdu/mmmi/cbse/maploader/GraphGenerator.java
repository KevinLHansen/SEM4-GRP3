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
                
                Node node = new Node(x * tileWidth, y * tileHeight);
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
                
                TiledMapTileLayer.Cell upLeft = tiles.getCell(x-1, y+1);
                TiledMapTileLayer.Cell upRight = tiles.getCell(x+1, y+1);
                TiledMapTileLayer.Cell downLeft = tiles.getCell(x-1, y-1);
                TiledMapTileLayer.Cell downRight = tiles.getCell(x+1, y-1);
                
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
                    
//                    if(y != mapHeight - 1 && x != 0 && upLeft == null) {
//                        Node upLeftNode = nodes.get(y+1).get(x-1);
//                        targetNode.createConnection(upLeftNode, 1);
//                    }
//                    if(y != mapHeight - 1 && x != mapWidth - 1 && upRight == null) {
//                        Node upRightNode = nodes.get(y+1).get(x+1);
//                        targetNode.createConnection(upRightNode, 1);
//                    }
//                    if(y != 0 && x != 0 && downLeft == null) {
//                        Node downLeftNode = nodes.get(y-1).get(x-1);
//                        targetNode.createConnection(downLeftNode, 1);
//                    }
//                    if(y != 0 && x != mapWidth -1 && downRight == null) {
//                        Node downRightNode = nodes.get(y-1).get(x+1);
//                        targetNode.createConnection(downRightNode, 1);
//                    }
                    
                }
                else {
                    targetNode.setType(Node.Type.WALL);
                } 
            }
        }
        
        System.out.println("\n\n\n\n\n\n\n\n\nGraph Generated!!!!");
        
        return new Graph(mapWidth, tileHeight, tileWidth, nodes);
    }
}
