package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = { @ServiceProvider(service = IEntityProcessingService.class), })

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            TimerPart timerPart = player.getPart(TimerPart.class);

            movingPart.setUp(gameData.getKeys().isDown(GameKeys.W));
            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.A));
            movingPart.setDown(gameData.getKeys().isDown((GameKeys.S)));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.D));

            // Shooting processing
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                shootingPart.setIsShooting(true);
                shootingPart.setDirection("up");
            }         
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                shootingPart.setIsShooting(true);
                shootingPart.setDirection("left");
            }           
            if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                shootingPart.setIsShooting(true);
                shootingPart.setDirection("down");
            }         
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                shootingPart.setIsShooting(true);
                shootingPart.setDirection("right");
            }
            

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            shootingPart.process(gameData, player);
            lifePart.process(gameData, player);
            timerPart.process(gameData, player);
        }
    }
}
