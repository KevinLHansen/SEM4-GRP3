package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class EnlargePlayer extends Item implements IPowerUp {

    public EnlargePlayer() {
        assignTexture("/img/enlargeplayer.png");
        setSpriteConfig(32, 32, 1);
    }

    @Override
    public void playerEffect(Entity playerEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
