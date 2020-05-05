package dk.sdu.mmmi.cbse.common.item;

import java.util.Random;

public enum ItemType {

    ENLARGE_PLAYER(0),
    ENLARGE_BULLET(1),
    ENLARGE_ENEMY(2);

    private int type;

    private ItemType(int type) {
        this.type = type;
    }

    int getType() {
        return type;
    }
    
    public static ItemType getRandomType() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
