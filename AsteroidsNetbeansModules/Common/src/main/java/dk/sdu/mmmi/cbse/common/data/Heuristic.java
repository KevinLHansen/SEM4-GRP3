/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

/**
 *
 * @author rasmusstamm
 */
public class Heuristic {
    private static int tileWidth;
    private static int tileHeight;
    
    public static void setTileSize(int width, int height){
        tileWidth = width;
        tileHeight = height;
    }
    
    public static float estimate(Node startNode, Node endNode) {
        int startIndex = startNode.getIndex();
        int endIndex = endNode.getIndex();
        
        int startX = startIndex * tileWidth;
        int startY = startIndex * tileHeight;
        
        int endX = endIndex * tileWidth;
        int endY = endIndex * tileHeight;
        
        float distance = Math.abs(startX - endX) + Math.abs(startY - endY);
        return distance;
    }
}
