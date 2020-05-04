/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonitem;

import java.util.Random;

/**
 *
 * @author maimartinsen
 */
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
