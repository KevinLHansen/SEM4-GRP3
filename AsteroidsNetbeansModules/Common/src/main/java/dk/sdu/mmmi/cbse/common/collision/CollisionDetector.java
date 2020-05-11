package dk.sdu.mmmi.cbse.common.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
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

                if (circleCollision(e, f)) {
                    // avoid bullets damaging the entity that created said bullet
                    if (e.getPart(ProjectilePart.class) != null) {
                        ProjectilePart epp = e.getPart(ProjectilePart.class);
                        if (f.getPart(ShootingPart.class) != null) {
                            ShootingPart fsp = f.getPart(ShootingPart.class);
                            if (epp.getID().equals(fsp.getID())) {
                                continue;
                            }
                        }
                    }
                    // avoid bullets damaging the entity that created said bullet
                    if (f.getPart(ProjectilePart.class) != null) {
                        ProjectilePart fpp = f.getPart(ProjectilePart.class);
                        if (e.getPart(ShootingPart.class) != null) {
                            ShootingPart esp = e.getPart(ShootingPart.class);
                            if (fpp.getID().equals(esp.getID())) {
                                continue;
                            }
                        }
                    }
                    
                    world.removeEntity(f);
                }
                if (e.getPart(LifePart.class) != null) {
                    LifePart lpe = e.getPart(LifePart.class);
                    if (lpe.isDead()) {
                        world.removeEntity(e);
                    }
                }
                if (f.getPart(LifePart.class) != null) {
                    LifePart lpf = f.getPart(LifePart.class);
                    if (lpf.isDead()) {
                        world.removeEntity(f);
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
