/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author rasmusstamm
 */
public class Heuristic {
    private int mapWidth;
    private int mapHeight;
    private static Heuristic h;
    
    public void setTileSize(int width, int height){
        mapWidth = width;
        mapHeight = height;
    }
    
    public static Heuristic getInstance(){
        if (h == null) {
            h = new Heuristic();
        }
        return h;
    }
    
    public float estimate(Node startNode, Node endNode) {
        int startIndex = startNode.getIndex();
        int endIndex = endNode.getIndex();
        
        int startX = startIndex % mapWidth;
        int startY = startIndex / mapWidth;

        int endX = endIndex % mapWidth;
        int endY = endIndex / mapWidth;
        
        Vector2 distVect = new Vector2(endX - startX, endY - startY);

        
        float distance = (float) Math.sqrt(Math.pow(distVect.x, 2) + Math.pow(distVect.y, 2));
        return distance;
    }
}
