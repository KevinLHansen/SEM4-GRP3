package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    Circle safezone;
    float safezoneRadius = 250;

    @Override
    public void start(GameData gameData, World world) {
        // create safezone in middle of world to prevent enemies from spawning close to player
        safezone = new Circle(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, safezoneRadius);
        
        // add enemies to the world
        int enemyCount = 10;
        for (int i = 0; i < enemyCount; i++) {
            enemy = createEnemy(gameData);
            world.addEntity(enemy);
        }
    }

    private Entity createEnemy(GameData gameData) {

        float x = 69; // default x
        float y = 420; // default y
        float radius = 12;
        
        boolean isClear = false;
        
        while (!isClear) {  
            // place enemy randomly
            x = (float) (gameData.getDisplayWidth() * Math.random());
            y = (float) (gameData.getDisplayHeight() * Math.random());
            
            boolean inWall = false;
            // prevent enemy from spawning inside wall
            for (Rectangle wall : gameData.getWalls()) {
                if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                    inWall = true;
                }
            }
            boolean inSafezone = false;
            // prevent enemy from spawning inside safezone
            if (Intersector.overlaps(new Circle(x, y, radius), safezone)) {
                inSafezone = true;
            }
            
            if (!inWall && !inSafezone) {
                isClear = true;
            }
        }

        Entity enemy = new Enemy();
        enemy.setRadius(radius);
        enemy.add(new MovingPart());
        enemy.add(new PositionPart(x, y));
        enemy.add(new LifePart(5));

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }
}
