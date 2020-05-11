package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public abstract class PowerUp extends Entity {

    public PowerUp() {
        assignTexture("/img/item.png");
        setSpriteConfig(32, 32, 1);
    }

    public abstract void affectPlayer(Entity entity);
}
