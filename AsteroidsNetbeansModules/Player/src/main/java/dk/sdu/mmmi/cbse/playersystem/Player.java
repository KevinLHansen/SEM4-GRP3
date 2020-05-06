
package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Player extends Entity {
    
    public Player() {
        assignTexture("/img/player.png");
        setSpriteConfig(32, 32, 1);
        setType("player");
   }
}
