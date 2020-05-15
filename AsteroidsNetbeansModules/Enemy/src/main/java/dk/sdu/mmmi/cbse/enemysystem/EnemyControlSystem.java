package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class EnemyControlSystem implements IEntityProcessingService {

    private float speed = 1;
    private Path path;
    private Node currentNode;
    private Node goalNode;
    private Circle aggroCircle;

    @Override
    public void process(GameData gameData, World world) {

        Entity player = null;
        // get player from world
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == "player") {
                player = entity;
                break;
            }
        }
        if (player != null) {
            PositionPart playerPos = player.getPart(PositionPart.class);

            for (Entity enemy : world.getEntities(Enemy.class)) {
                PositionPart enemyPos = enemy.getPart(PositionPart.class);
                LifePart lifePart = enemy.getPart(LifePart.class);

                Vector2 enemyVector = new Vector2(enemyPos.getX(), enemyPos.getY());
                Vector2 playerVector = new Vector2(playerPos.getX(), playerPos.getY());

                aggroCircle = new Circle(enemyVector, ((Enemy) enemy).getAggroRange());
                if (aggroCircle.contains(playerVector)) {

//                if (path == null) {
//                    Node currentNode = gameData.getGraph().getNodeByPosition((int) enemyVector.x, (int) enemyVector.y);
//                    Node goalNode = gameData.getGraph().getNodeByPosition((int) playerVector.x, (int) playerVector.y);
//                    
//                    path = ((Enemy) enemy).getPath(currentNode, goalNode);
//                }
                    if (path == null) {
                        currentNode = gameData.getGraph().getNodeByPosition((int) enemyVector.x, (int) enemyVector.y);
                        goalNode = gameData.getGraph().getNodeByPosition((int) playerVector.x, (int) playerVector.y);

                        path = ((Enemy) enemy).getPath(currentNode, goalNode);
                    }

//                    if (currentNode.equals(path.get(0))) {
//                        path.remove(0);
//                    }
                    System.out.println(path.getNodeCount());

                    float x = enemyPos.getX();
                    float y = enemyPos.getY();
                    float radius = enemy.getRadius();

//                if (gameData.getGraph().getNodeByPosition((int) enemyPos.getX(), (int) enemyPos.getY()).equals(path.getNodes().get(0))) {
//                    path.remove(0);
//                }
                    

//                    if (c.contains(path.get(0).getX(), path.get(0).getY())) {
//                        if (path.getNodeCount() > 1) {
//                            path.remove(0);
//                        }
//                    }
                    //else {
//                        Node currentNode = gameData.getGraph().getNodeByPosition((int) enemyVector.x, (int) enemyVector.y);
//                        Node goalNode = gameData.getGraph().getNodeByPosition((int) playerVector.x, (int) playerVector.y);
//                    
//                        path = ((Enemy) enemy).getPath(currentNode, goalNode);
//                    }
//                }
                    
                    Node target = path.get(0);
                    System.out.println("x: " + path.getNodes().get(0).getX());
                    System.out.println("y: " + path.getNodes().get(0).getY());

                    // calculate vector from enemy to player
                    float vectX = target.getX() - x;
                    float vectY = target.getY() - y;
                    Vector2 vector = new Vector2(vectX, vectY);
                    // length of vector = how far entity moves per frame
                    if (!(speed >= vector.len())) {
                        vector.setLength(speed);
                    } else {
                        currentNode = gameData.getGraph().getNodeByPosition((int) enemyVector.x, (int) enemyVector.y);
                        goalNode = gameData.getGraph().getNodeByPosition((int) playerVector.x, (int) playerVector.y);

                        path = ((Enemy) enemy).getPath(currentNode, goalNode);
                        
                        path.remove(0);
                    }

                    x += vector.x;
                    y += vector.y;

                    enemyPos.setX(x);
                    enemyPos.setY(y);
                }

                enemyPos.process(gameData, enemy);
                lifePart.process(gameData, enemy);
            }
        }
    }
}
