package dk.sdu.mmmi.cbse.item.powerups;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;

public class IncreaseFireRatePowerUp extends PowerUp {

    public IncreaseFireRatePowerUp() {
        assignTexture("/img/increasefirerate.png");
        setType("increasefireratepowerup");
    }

    @Override
    public void affectPlayer(Entity player) {
        ShootingPart shootingPart = player.getPart(ShootingPart.class);
        shootingPart.setBulletRadius(shootingPart.getBulletRadius() * 0.7f);
        shootingPart.setFireRate((int) (shootingPart.getFireRate() / 3)); // increase fire rate by lowering attribute
    }

    @Override
    public void unaffectPlayer(Entity player) {
        Entity playerOrigin = player.getOrigin();
        ShootingPart shootingPart = player.getPart(ShootingPart.class);
        ShootingPart shootingPartOrigin = playerOrigin.getPart(ShootingPart.class);
        shootingPart.setBulletRadius(shootingPartOrigin.getBulletRadius());
        shootingPart.setFireRate(shootingPartOrigin.getFireRate());
    }
}
