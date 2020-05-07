package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class EnlargeBullet extends Item implements IPowerUp {

    public EnlargeBullet() {
        assignTexture("/img/enlargebullet.png");
        setSpriteConfig(32, 32, 1);
    }

    @Override
    public Entity affectPlayer(Entity playerEntity) {
        return null;
    }
}
