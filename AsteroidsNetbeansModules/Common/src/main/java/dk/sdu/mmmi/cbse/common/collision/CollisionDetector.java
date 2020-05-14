package dk.sdu.mmmi.cbse.common.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = { @ServiceProvider(service = IPostEntityProcessingService.class), })
public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            // exclude items from this processing loop
            if (e.getType() == "enlargeplayerpowerup" || e.getType() == "enlargebulletpowerup" || e.getType() == "increasefireratepowerup") {
                continue;
            }
            for (Entity f : world.getEntities()) {
                if (e.getID().equals(f.getID())) { // so that entity cannot collide with oneself
                    continue;
                }

                // exclude items from this processing loop
                if (f.getType() == "enlargeplayerpowerup" || f.getType() == "enlargebulletpowerup" || f.getType() == "increasefireratepowerup") {
                    continue;
                }
                
                // so that bullets cannot collide with bullets
                if (f.getType() == "bullet" && e.getType() == "bullet") {
                    continue;
                }
                
                // so that enemies cannot collide with enemies
                if (f.getType() == "enemy" && e.getType() == "enemy") {
                    continue;
                }

                if (circleCollision(e, f)) {
                    // enemy <-> player
                    if ((e.getType() == "enemy" && f.getType() == "player") || (e.getType() == "player" && f.getType() == "enemy")) {
                        if (e.getType() == "player") {
                            world.removeEntity(e);
                        } else {
                            world.removeEntity(f);
                        }
                    }
                    // bullet <-> player
                    if ((e.getType() == "bullet" && f.getType() == "player") || (e.getType() == "player" && f.getType() == "bullet")) {
                        continue;
                    }
                    
                    // bullet <-> enemy
                    if ((e.getType() == "bullet" && f.getType() == "enemy") || (e.getType() == "enemy" && f.getType() == "bullet")) {
                        world.removeEntity(f);
                        world.removeEntity(e);
                    }
                    gameData.setScore(gameData.getScore() + world.removeEntity(f, 1));
                }
                
                // remove dead entities
                if (e.getPart(LifePart.class) != null) {
                    LifePart lpe = e.getPart(LifePart.class);
                    if (lpe.isDead()) {
                        gameData.setScore(gameData.getScore() + world.removeEntity(e, 1));
                    }
                }
                if (f.getPart(LifePart.class) != null) {
                    LifePart lpf = f.getPart(LifePart.class);
                    if (lpf.isDead()) {
                        gameData.setScore(gameData.getScore() + world.removeEntity(f, 1));
                    }
                }
            }
        }
    }

    public boolean circleCollision(Entity e, Entity f) {
        PositionPart ep = e.getPart(PositionPart.class);
        PositionPart fp = f.getPart(PositionPart.class);

        if ((ep.getX() - fp.getX()) * (ep.getX() - fp.getX())
                + (ep.getY() - fp.getY()) * (ep.getY() - fp.getY())
                < (e.getRadius() + f.getRadius()) * (e.getRadius() + f.getRadius())) {
            return true;
        }
        return false;
    }
}
