package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class EnlargeBulletPowerUp extends Item implements IPowerUp {

    public EnlargeBulletPowerUp() {
        assignTexture("/img/enlargebullet.png");
        setSpriteConfig(32, 32, 1);
    }

    @Override
    public void affectPlayer(Entity playerEntity) {
        playerEntity.setSpriteConfig(10, 10, 2);
        playerEntity.setRadius(4);
    }
}
