package dk.sdu.mmmi.cbse.common.data.entityparts;

// @author Group 3

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class ItemPart implements EntityPart {

    private boolean isActive; // whether entity is under effect of item
    private float timer; // countdown timer in ms

    @Override
    public void process(GameData gameData, Entity entity) {
        if (isActive) {
            if (timer <= 0) {
                isActive = false;
            } else {
                timer -= gameData.getDelta() * 1000;
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public float getTimer() {
        return timer;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }
}
