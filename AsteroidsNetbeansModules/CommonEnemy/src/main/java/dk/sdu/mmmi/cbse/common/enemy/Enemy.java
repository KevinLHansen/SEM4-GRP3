package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;

public class Enemy extends Entity {
    
    private AStar as;
    
    public Enemy() {
        as = new AStar();
        assignTexture("/img/enemy.png");
        setType("enemy");
    }
    
    public Path getPath(Node current, Node goal) {
        Path path = as.doTheThing(current, goal);
        return path;
    }
    
}
