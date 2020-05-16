package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

// @author Group 3

public class Player extends Entity {
    
    public Player() {
        assignTexture("/img/player.png");
        setType("player");
   }
}
