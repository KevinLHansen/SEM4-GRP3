package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {
    
    public Enemy() {
        assignTexture("/img/enemy.png");
        setType("enemy");
        setScoreVal(75);
    }
}
