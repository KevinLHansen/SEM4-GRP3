package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class EnlargePlayerPowerUp extends PowerUp {

    public EnlargePlayerPowerUp() {
        assignTexture("/img/enlargeplayer.png");
        setType("enlargeplayerpowerup");
    }

    @Override
    public void affectPlayer(Entity playerEntity) {
        playerEntity.setRadius(playerEntity.getRadius() * 1.5f);
    }

    @Override
    public void unaffectPlayer(Entity player) {
        Entity playerOrigin = player.getOrigin();
        player.setRadius(playerOrigin.getRadius());
    }
}
