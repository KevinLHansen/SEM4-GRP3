package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;

public class EnlargeBulletPowerUp extends PowerUp {

    public EnlargeBulletPowerUp() {
        assignTexture("/img/enlargebullet.png");
        setType("enlargebulletpowerup");
    }

    @Override
    public void affectPlayer(Entity player) {
        ShootingPart shootingPart = player.getPart(ShootingPart.class);
        shootingPart.setBulletRadius(shootingPart.getBulletRadius() * 2);
    }

    @Override
    public void unaffectPlayer(Entity player) {
        Entity playerOrigin = player.getOrigin();
        ShootingPart shootingPart = player.getPart(ShootingPart.class);
        ShootingPart shootingPartOrigin = playerOrigin.getPart(ShootingPart.class);
        shootingPart.setBulletRadius(shootingPartOrigin.getBulletRadius());
    }
}
