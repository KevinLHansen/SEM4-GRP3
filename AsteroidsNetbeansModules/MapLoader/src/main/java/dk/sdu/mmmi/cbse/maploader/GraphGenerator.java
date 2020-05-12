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
        Array<Node> nodes = new Array<Node>();
        TiledMapTileLayer tiles = (TiledMapTileLayer) map.getLayers().get("objects");
        
        int mapHeight = TileLoader.getMapHeight();
        int mapWidth = TileLoader.getMapWidth();
        int tileWidth = TileLoader.getTileWidth();
        int tileHeight = TileLoader.getTileHeight();
        
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                
                Node node = new Node();
                node.setType(1);
                nodes.add(node);
            }
        }
        
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                
                Node targetNode = nodes.get(mapWidth * y + x);
                if (target == null) {
                    if(y != 0 && down == null) {
                        Node downNode = nodes.get(mapWidth * (y-1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if(y != mapHeight -1 && up == null) {
                        Node downNode = nodes.get(mapWidth * (y-1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if(x != 0 && left == null) {
                        Node downNode = nodes.get(mapWidth * (y-1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if(x != mapWidth - 1 && right == null) {
                        Node downNode = nodes.get(mapWidth * (y-1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                }
            }
        }
        
        return new Graph(mapWidth, tileHeight, tileWidth, nodes);
    }
}
