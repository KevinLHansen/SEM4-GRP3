package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Item extends Entity {
    
        public Item() {
        assignTexture("/img/item.png");
        setSpriteConfig(32, 32, 1);
    }
    
    private ItemType type;
    
    public Item(ItemType type) {
        this.type = type;
    }

    public int getType() {
        return type.getType();
    }
    
}
