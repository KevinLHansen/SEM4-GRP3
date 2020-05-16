package dk.sdu.mmmi.cbse.item.powerups;

import dk.sdu.mmmi.cbse.common.data.Entity;

// @author Group 3

public abstract class PowerUp extends Entity {

    public PowerUp() {
        assignTexture("/img/item.png");
    }

    public abstract void affectPlayer(Entity player);
    
    public abstract void unaffectPlayer(Entity player);
}
