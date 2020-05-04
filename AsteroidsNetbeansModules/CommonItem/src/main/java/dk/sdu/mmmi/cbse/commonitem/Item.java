/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonitem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author maimartinsen
 */
public class Item extends Entity {
    
    private ItemType type;
    
    public Item(ItemType type) {
        this.type = type;
    }

    public int getType() {
        return type.getType();
    }
    
}
