package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

// @author Group 3

public class Bullet extends Entity {
    
    public Bullet() {
        assignTexture("/img/bullet.png");
        setType("bullet");
    }
}
