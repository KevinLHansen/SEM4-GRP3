package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

// @author Group 3

@ServiceProviders(value = { @ServiceProvider(service = IEntityProcessingService.class), })

public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {
                ShootingPart shootingPart = entity.getPart(ShootingPart.class);
                TimerPart timerPart = entity.getPart(TimerPart.class);

                // Shoot if isShooting is true
                if (shootingPart.isShooting()) {
                    if (timerPart.getTimer() > shootingPart.getFireRate()) { // if [fireRate] ms has passed since last bullet fired
                        timerPart.resetTimer();
                        PositionPart positionPart = entity.getPart(PositionPart.class);
                        Entity bullet = createBullet(positionPart.getX(), positionPart.getY(), shootingPart.getDirection(), entity.getRadius(), shootingPart.getBulletRadius(), shootingPart.getID());
                        world.addEntity(bullet);
                    }
                    shootingPart.setIsShooting(false);
                }
            }
        }
        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart ppb = bullet.getPart(PositionPart.class);
            MovingPart mpb = bullet.getPart(MovingPart.class);
            TimerPart btp = bullet.getPart(TimerPart.class);
            btp.reduceExpiration(gameData.getDelta());
            LifePart lpb = bullet.getPart(LifePart.class);

            ppb.process(gameData, bullet);
            mpb.process(gameData, bullet);
            btp.process(gameData, bullet);
            lpb.process(gameData, bullet);
        }
    }

    private Entity createBullet(float x, float y, String direction, float entityRadius, float bulletRadius, String uuid) {
        Entity bullet = new Bullet();
        PositionPart pp;
        MovingPart mp = new MovingPart(4);

        // assign direction of bullet and add entity radius to start position of bullet
        switch (direction) {
            case "up":
                mp.setUp(true);
                mp.setLeft(false);
                mp.setDown(false);
                mp.setRight(false);

                pp = new PositionPart(x, y + entityRadius);
                break;
            case "left":
                mp.setUp(false);
                mp.setLeft(true);
                mp.setDown(false);
                mp.setRight(false);

                pp = new PositionPart(x - entityRadius, y);
                break;
            case "down":
                mp.setUp(false);
                mp.setLeft(false);
                mp.setDown(true);
                mp.setRight(false);

                pp = new PositionPart(x, y - entityRadius);
                break;
            case "right":
                mp.setUp(false);
                mp.setLeft(false);
                mp.setDown(false);
                mp.setRight(true);

                pp = new PositionPart(x + entityRadius, y);
                break;
            default:
                pp = new PositionPart(x, y);
                break;
        }

        bullet.add(pp);
        bullet.add(mp);
        bullet.add(new TimerPart(3, true));
        bullet.add(new LifePart(1));
        // Projectile Part only used for better collision detection     
        bullet.add(new ProjectilePart(uuid));
        bullet.setRadius(bulletRadius);

        return bullet;
    }
}
