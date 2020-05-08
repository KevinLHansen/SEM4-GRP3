package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class EnlargePlayerPowerUp extends Item implements IPowerUp {

    public EnlargePlayerPowerUp() {
        assignTexture("/img/enlargeplayer.png");
        setSpriteConfig(32, 32, 1);
    }

    @Override
    public void affectPlayer(Entity playerEntity) {
        playerEntity.setSpriteConfig(32, 32, 2);
        playerEntity.setRadius(playerEntity.getRadius() * 2);
    }
}
