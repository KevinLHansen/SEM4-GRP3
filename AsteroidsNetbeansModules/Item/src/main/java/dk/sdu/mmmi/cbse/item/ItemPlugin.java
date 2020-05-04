/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonitem.Item;
import dk.sdu.mmmi.cbse.commonitem.ItemType;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author maimartinsen
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class ItemPlugin implements IGamePluginService {
    
    private Entity item;

    @Override
    public void start(GameData gameData, World world) {
        item = createItem(gameData);
        world.addEntity(item);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities(Item.class)) {
            world.removeEntity(e);
        }
    }
    
    private Item createItem(GameData gameData) {
        
        float radians = 3.1415f / 2 + (float) Math.random();
        float x = gameData.getDisplayWidth() / 2 + 100;
        float y = gameData.getDisplayHeight() / 2 + 50;

        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 1.0f;
        colour[2] = 0.07f;
        colour[3] = 1.0f;
        
        Entity itemType = new Item(ItemType.getRandomType());
        itemType.add(new PositionPart(x, y, radians));
        itemType.setColour(colour);
        itemType.setRadius(10);
        
        return (Item) itemType;
    }
    
    
    
}
