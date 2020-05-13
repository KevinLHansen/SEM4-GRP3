package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Graph;

public class Enemy extends Entity {
    
    private Pathfinder pf;
    
    public Enemy(Graph graph) {
        pf = new Pathfinder(graph);
        assignTexture("/img/enemy.png");
        setType("enemy");
    }

    public Pathfinder getPathfinder() {
        return pf;
    }
    
    
}
