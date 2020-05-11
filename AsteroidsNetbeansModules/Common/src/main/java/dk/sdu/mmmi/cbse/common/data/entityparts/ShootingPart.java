package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class ShootingPart implements EntityPart {

    private boolean isShooting;
    private String direction;
    private String ID;
    private float bulletRadius;
    private int fireRate; // ms between shots

    public ShootingPart() {
        
    }
    
    public ShootingPart(String id) {
        this.ID = id;
    }

    public boolean isShooting() {
        return this.isShooting;
    }

    public void setIsShooting(boolean b) {
        this.isShooting = b;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public String getDirection() {
        return direction;
    }

    public float getBulletRadius() {
        return bulletRadius;
    }

    public void setBulletRadius(float bulletRadius) {
        this.bulletRadius = bulletRadius;
    }
    
    public int getFireRate() {
        return fireRate;
    }
    
    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }
    
}
