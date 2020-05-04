/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonitem;

/**
 *
 * @author maimartinsen
 */
public enum ItemType {
    
    ENLARGE_PLAYER(1),
    ENLARGE_BULLET(2),
    ENLARGE_ENEMY(3);
    
    
    private int type;
    
    private ItemType(int type) {
        this.type = type;
    }

    int getType() {
        return type;
    }
}
