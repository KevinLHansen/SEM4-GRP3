package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public abstract class PowerUp extends Entity {

    public PowerUp() {
        assignTexture("/img/item.png");
    }

    public abstract void affectPlayer(Entity player);
    
    public abstract void unaffectPlayer(Entity player);
}
