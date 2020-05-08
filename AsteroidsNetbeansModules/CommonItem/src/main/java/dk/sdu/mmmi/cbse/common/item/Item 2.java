package dk.sdu.mmmi.cbse.common.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Item extends Entity {
    
    private ItemType type;
    
    public Item(ItemType type) {
        this.type = type;
    }

    public int getType() {
        return type.getType();
    }
    
}
