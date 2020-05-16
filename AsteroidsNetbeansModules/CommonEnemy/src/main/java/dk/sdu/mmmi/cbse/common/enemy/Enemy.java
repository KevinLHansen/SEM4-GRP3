package dk.sdu.mmmi.cbse.common.enemy;

import com.badlogic.gdx.math.Circle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;

public class Enemy extends Entity {
    
    private int aggroRange;
    
    public Enemy(int aggroRange) {
        this.aggroRange = aggroRange;
        assignTexture("/img/enemy.png");
        setType("enemy");
    }
    
    public void setAggroRange(int aggroRange) {
        this.aggroRange = aggroRange;
    }

    public int getAggroRange() {
        return aggroRange;
    }
    
}
