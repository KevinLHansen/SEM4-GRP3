/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.enemy;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.List;

/**
 *
 * @author rasmusstamm
 */
public class Pathfinder {

    private Graph graph;
    
    public Pathfinder(Graph graph) {
        this.graph = graph;
    }

    
    
    public Graph calculatePath(Vector2 startPos, Vector2 goalPos) {
        
        AStar as = new AStar();
        as.doTheThing(graph.getNodeByPosition((int) startPos.x, (int) startPos.y), graph.getNodeByPosition((int) goalPos.x, (int) goalPos.y));
        
        
        return graph;
    }
}
