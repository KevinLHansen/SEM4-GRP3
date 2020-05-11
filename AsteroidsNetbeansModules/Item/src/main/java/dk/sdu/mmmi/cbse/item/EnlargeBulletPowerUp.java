package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class EnlargeBulletPowerUp extends PowerUp {

    public EnlargeBulletPowerUp() {
        assignTexture("/img/enlargebullet.png");
        setType("enlargebulletpowerup");
    }

    @Override
    public void affectPlayer(Entity playerEntity) {
//        playerEntity.setSpriteConfig(10, 10, 2);
//        playerEntity.setRadius(32);
    }
}
