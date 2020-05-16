package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;

// @author Group 3

public class Enemy extends Entity {
    
    private float aggroRange;
    
    public Enemy(float aggroRange) {
        this.aggroRange = aggroRange;
        assignTexture("/img/enemy.png");
        setType("enemy");
        setScoreVal(75);
    }
    
    public void setAggroRange(float aggroRange) {
        this.aggroRange = aggroRange;
    }

    public float getAggroRange() {
        return aggroRange;
    }
}
