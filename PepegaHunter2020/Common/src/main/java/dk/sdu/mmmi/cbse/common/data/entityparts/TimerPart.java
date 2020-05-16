package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

// @author Group 3

public class TimerPart implements EntityPart {

    private float expiration;
    private boolean shouldExpire;
    private float timer; // lifetime of part in ms

    public TimerPart(float expiration, boolean shouldExpire) {
        this.expiration = expiration;
        this.shouldExpire = shouldExpire;
        timer = 0;
    }

    public TimerPart() { // default constructor for when expiration is redundant
        shouldExpire = false;
        timer = 0;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    public float getTimer() {
        return timer;
    }

    public void resetTimer() {
        timer = 0;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        // process expiration for parts that need to expire
        if (shouldExpire) {
            if (expiration > 0) {
                reduceExpiration(gameData.getDelta());
            }

            if (expiration <= 0) {
                LifePart lifePart = entity.getPart(LifePart.class);
                lifePart.setLife(0);
            }
        }
        // convert delta (seconds) to milliseconds and add to part timer
        timer += gameData.getDelta() * 1000;
    }
}
