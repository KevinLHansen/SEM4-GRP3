package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

// @author Group 3

@ServiceProviders(value = { @ServiceProvider(service = IEntityProcessingService.class), })

public class EnemySpawnSystem implements IEntityProcessingService {

    int minEnemies = 5; // number of enemies to always exist in world
    int enemiesSpawned = 0; // number of enemies spawned since start of game
    float speed;
    int life;

    @Override
    public void process(GameData gameData, World world) {
        if (gameData.isSpawnEnemies()) {
            Entity player = null;
            // get player to create safezone
            for (Entity entity : world.getEntities()) {
                if ("player".equalsIgnoreCase(entity.getType())) {
                    player = entity;
                }
            }
            if (player != null) {
                if (gameData.isNewGame()) {
                    // on new game, reset spawner values
                    reset();
                    gameData.setNewGame(false);
                }
                int enemyCount = 0;
                // count number of enemies in world
                for (Entity entity : world.getEntities(Enemy.class)) {
                    enemyCount++;
                }
                // add new enemies until minEnemies exist
                while (enemyCount < minEnemies) {
                    // for every 5 enemies spawned, increase enemy speed
                    if (enemiesSpawned % 5 == 0 && enemiesSpawned != 0) {
                        speed += 0.5;
                    }
                    // for every 10 enemies spawned, increase enemy life
                    if (enemiesSpawned % 10 == 0 && enemiesSpawned != 0) {
                        life += 1;
                    }

                    Entity enemy = EnemyPlugin.createEnemy(gameData, world, speed, life, player);
                    world.addEntity(enemy);
                    enemyCount++;
                    enemiesSpawned++;
                }
            }
        }
    }
    
    private void reset() {
        speed = 1;
        life = 3;
        enemiesSpawned = 0;
    }
}
