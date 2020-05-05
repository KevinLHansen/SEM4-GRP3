
package dk.sdu.mmmi.cbse.itemsystem;

// @author Kevin Hansen

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = { @ServiceProvider(service = IGamePluginService.class), })

public class ItemPlugin implements IGamePluginService {
    
    private Entity item;

    public ItemPlugin() {
    }
    
    @Override
    public void start(GameData gameData, World world) {
        
        item = createItem(gameData);
        world.addEntity(item);
    }

    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Entity createItem(GameData gameData) {
        
        // randomly generate position of item
        float x = (float) (gameData.getDisplayWidth() * Math.random());
        float y = (float) (gameData.getDisplayHeight() * Math.random());
        
        item = new Item();
        item.add(new PositionPart(x, y));
        item.setRadius(16);
        
        return item;
    }

}
