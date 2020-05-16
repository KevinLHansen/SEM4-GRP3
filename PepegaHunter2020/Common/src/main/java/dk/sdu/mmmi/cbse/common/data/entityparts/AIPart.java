/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;

/**
 *
 * @author rasmusstamm
 */
public class AIPart implements EntityPart {

    private Path path;
    private Node currentNode;
    private Node goalNode;
    private Entity target;
    private float speed = 3;

    public void setTarget(Entity target) {
        this.target = target;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart entityPos = entity.getPart(PositionPart.class);
        PositionPart targetPos = target.getPart(PositionPart.class);

        Vector2 entityVector = new Vector2(entityPos.getX(), entityPos.getY());
        Vector2 targetVector = new Vector2(targetPos.getX(), targetPos.getY());
        PathFinderPart pfp = entity.getPart(PathFinderPart.class);

        if (path == null) {
            currentNode = gameData.getGraph().getNodeByPosition((int) entityVector.x, (int) entityVector.y);
            goalNode = gameData.getGraph().getNodeByPosition((int) targetVector.x, (int) targetVector.y);

            path = pfp.findPath(currentNode, goalNode);
        }

        float x = entityPos.getX();
        float y = entityPos.getY();

        Node target = path.get(0);

        // calculate vector from entity to target
        float vectX = target.getX() - x;
        float vectY = target.getY() - y;
        Vector2 vector = new Vector2(vectX, vectY);

        // length of vector = how far entity moves per frame
        if (!(speed >= vector.len())) {
            vector.setLength(speed);
        } else {
            currentNode = gameData.getGraph().getNodeByPosition((int) entityVector.x, (int) entityVector.y);
            goalNode = gameData.getGraph().getNodeByPosition((int) targetVector.x, (int) targetVector.y);

            path = pfp.findPath(currentNode, goalNode);

            path.remove(0);
        }

        x += vector.x;
        y += vector.y;
        
        entityPos.setX(x);
        entityPos.setY(y);
    }

}
