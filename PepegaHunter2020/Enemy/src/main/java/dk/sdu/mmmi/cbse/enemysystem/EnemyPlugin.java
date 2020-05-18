package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AIPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PathFinderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

// @author Group 3

@ServiceProviders(value = { @ServiceProvider(service = IGamePluginService.class), })

public class EnemyPlugin implements IGamePluginService {

    static float safezoneRadius = 250;
    EnemySpawnSystem spawner;

    @Override
    public void start(GameData gameData, World world) {
        // enable enemies spawning
        gameData.setSpawnEnemies(true);
        gameData.setNewGame(true);
    }

    public static Entity createEnemy(GameData gameData, World world, float speed, int life, Entity player) {
        
        float x = 69; // default x
        float y = 420; // default y
        float radius = 12;
        Circle safezone;
 
        PositionPart playerPos = player.getPart(PositionPart.class);
        safezone = new Circle(playerPos.getX(), playerPos.getY(), safezoneRadius);
        
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
        
        Entity enemy = new Enemy(500);
        enemy.setRadius(radius);
        PathFinderPart pathPart = new PathFinderPart();
        pathPart.setGameData(gameData);
        enemy.add(pathPart);
        enemy.add(new AIPart(speed));
        enemy.add(new MovingPart());
        enemy.add(new PositionPart(x, y));
        enemy.add(new LifePart(life));

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // deactive spawning enemies and remove all enemy entities from world
        gameData.setSpawnEnemies(false);
        
        for (Entity entity : world.getEntities(Enemy.class)) {
            world.removeEntity(entity);
        }
    }
}
