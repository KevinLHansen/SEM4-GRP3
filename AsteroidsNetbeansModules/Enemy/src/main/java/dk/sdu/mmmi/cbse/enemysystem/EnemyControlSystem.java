package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
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

                float x = enemyPos.getX();
                float y = enemyPos.getY();
                float radius = enemy.getRadius();

                // calculate vector from enemy to player
                float vectX = playerPos.getX() - x;
                float vectY = playerPos.getY() - y;
                Vector2 vector = new Vector2(vectX, vectY);
                // length of vector = how far entity moves per frame
                vector.setLength(speed);

                x += vector.x;
                y += vector.y;
                
                float tempY = y;
                
                // prevent entity from leaving world boundaries
                if (x > gameData.getDisplayWidth() || x < 0) { x = enemyPos.getX(); }
                if (y > gameData.getDisplayHeight() || y < 0) { y = enemyPos.getY(); }

                // get list of walls from GameData
                List<Rectangle> walls = gameData.getWalls();
                // check if entity will collide with any wall
                for (Rectangle wall : walls) {
                    if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                        // try changing only x
                        y = enemyPos.getY();
                    if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                        // try changing only y
                        y = tempY;
                        x = enemyPos.getX();
                    }
                    if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                        y = enemyPos.getY();
                    }
                }
            }
            enemyPos.setX(x);
            enemyPos.setY(y);

            enemyPos.process(gameData, enemy);
            lifePart.process(gameData, enemy);
            }
        }
    }
}