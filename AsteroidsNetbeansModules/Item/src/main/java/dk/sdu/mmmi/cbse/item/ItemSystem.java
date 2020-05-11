package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.item.powerups.PowerUp;
import dk.sdu.mmmi.cbse.item.powerups.EnlargePlayerPowerUp;
import dk.sdu.mmmi.cbse.item.powerups.EnlargeBulletPowerUp;
import dk.sdu.mmmi.cbse.common.collision.CollisionDetector;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ItemPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.item.powerups.IncreaseFireRatePowerUp;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})

public class ItemSystem implements IEntityProcessingService {

    CollisionDetector collisionDetector = new CollisionDetector();

    @Override
    public void process(GameData gameData, World world) {
        Entity player = null;
        
        // get player entity
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == "player") {
                player = entity;
                break;
            }
        }
        // if player is still alive
        if (player != null) {
            ItemPart ip = player.getPart(ItemPart.class);
            
            // if no item active, revert stats to original
            if (!ip.isActive()) {
                EnlargePlayerPowerUp pwrUp1 = new EnlargePlayerPowerUp();
                EnlargeBulletPowerUp pwrUp2 = new EnlargeBulletPowerUp();
                IncreaseFireRatePowerUp pwrUp3 = new IncreaseFireRatePowerUp();
                
                pwrUp1.unaffectPlayer(player);
                pwrUp2.unaffectPlayer(player);
                pwrUp3.unaffectPlayer(player);
            }
            
            for (Entity entity : world.getEntities()) {
                if (entity instanceof PowerUp) {
                    if (collisionDetector.circleCollision(entity, player)) {
                        if (ip.isActive()) { // if player is already affected by an item
                            // don't affect
                        } else {
                            // affect player and start countdown
                            PowerUp powerUp = (PowerUp) entity;
                            powerUp.affectPlayer(player);
                            ip.setIsActive(true);
                            ip.setTimer(5000);
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }
}
