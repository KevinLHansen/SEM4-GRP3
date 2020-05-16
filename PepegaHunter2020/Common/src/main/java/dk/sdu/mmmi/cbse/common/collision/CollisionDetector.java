package dk.sdu.mmmi.cbse.common.collision;

import com.badlogic.gdx.math.Vector2;
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
            if ("enlargeplayerpowerup".equalsIgnoreCase(e.getType()) || "enlargebulletpowerup".equalsIgnoreCase(e.getType()) || "increasefireratepowerup".equalsIgnoreCase(e.getType())) {
                continue;
            }
            for (Entity f : world.getEntities()) {
                if (e.getID().equals(f.getID())) { // so that entity cannot collide with oneself
                    continue;
                }

                // exclude items from this processing loop
                if ("enlargeplayerpowerup".equalsIgnoreCase(f.getType()) || "enlargebulletpowerup".equalsIgnoreCase(f.getType()) || "increasefireratepowerup".equalsIgnoreCase(f.getType())) {
                    continue;
                }
                
                // so that bullets cannot collide with bullets
                if ("bullet".equalsIgnoreCase(f.getType()) && "bullet".equalsIgnoreCase(e.getType())) {
                    continue;
                }
                
                // so that enemies cannot collide with enemies
                if ("enemy".equalsIgnoreCase(f.getType()) && "enemy".equalsIgnoreCase(e.getType())) {
                    continue;
                }

                if (circleCollision(e, f)) {
                    // enemy <-> player
                    if (("enemy".equalsIgnoreCase(e.getType()) && "player".equalsIgnoreCase(f.getType())) || ("player".equalsIgnoreCase(e.getType()) && "enemy".equalsIgnoreCase(f.getType()))) {
                        Entity player;
                        if ("player".equalsIgnoreCase(e.getType())) {
                            player = e;
                            world.removeEntity(f);
                        } else {
                            player = f;
                            world.removeEntity(e);
                        }
                        LifePart life = player.getPart(LifePart.class);
                        life.setLife(life.getLife() - 1);
                    }
                    // bullet <-> player
                    if (("bullet".equalsIgnoreCase(e.getType()) && "player".equalsIgnoreCase(f.getType())) || ("player".equalsIgnoreCase(e.getType()) && "bullet".equalsIgnoreCase(f.getType()))) {
                        continue;
                    }
                    
                    // bullet <-> enemy
                    if (("bullet".equalsIgnoreCase(e.getType()) && "enemy".equalsIgnoreCase(f.getType())) || ("enemy".equalsIgnoreCase(e.getType()) && "bullet".equalsIgnoreCase(f.getType()))) {
                        Entity enemy;
                        if ("enemy".equalsIgnoreCase(e.getType())) {
                            enemy = e;
                            world.removeEntity(f);
                        } else {
                            enemy = f;
                            world.removeEntity(e);
                        }
                        LifePart life = enemy.getPart(LifePart.class);
                        life.setLife(life.getLife() - 1);
                    }
                }
                
                // remove dead entities
                if (e.getPart(LifePart.class) != null) {
                    LifePart life = e.getPart(LifePart.class);
                    if (life.isDead()) {
                        gameData.setScore(gameData.getScore() + world.removeEntity(e, 1));
                    }
                }
                if (f.getPart(LifePart.class) != null) {
                    LifePart life = f.getPart(LifePart.class);
                    if (life.isDead()) {
                        gameData.setScore(gameData.getScore() + world.removeEntity(f, 1));
                    }
                }
            }
        }
    }

    public boolean circleCollision(Entity e, Entity f) {
        
        PositionPart ePos = e.getPart(PositionPart.class);
        PositionPart fPos = f.getPart(PositionPart.class);
        
        // get vector from e to f
        Vector2 dist = new Vector2(ePos.getX(), ePos.getY()).sub(new Vector2(fPos.getX(), fPos.getY()));
        // if distance smaller than radius of both entities
        if (dist.len() < e.getRadius() + f.getRadius()) {
            return true;
        }
        return false;
    }
}
