package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AIPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

// @author Group 3

@ServiceProviders(value = { @ServiceProvider(service = IEntityProcessingService.class), })

public class EnemyControlSystem implements IEntityProcessingService {

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
                AIPart aiPart = enemy.getPart(AIPart.class);
                aiPart.setTarget(player);
                PositionPart enemyPos = enemy.getPart(PositionPart.class);
                LifePart lifePart = enemy.getPart(LifePart.class);

                Vector2 enemyVector = new Vector2(enemyPos.getX(), enemyPos.getY());
                Vector2 playerVector = new Vector2(playerPos.getX(), playerPos.getY());

                aggroCircle = new Circle(enemyVector, ((Enemy) enemy).getAggroRange());
                if (aggroCircle.contains(playerVector)) {
                    aiPart.process(gameData, enemy);
                }

                enemyPos.process(gameData, enemy);
                lifePart.process(gameData, enemy);
            }
        }
    }
}
