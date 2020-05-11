package dk.sdu.mmmi.cbse.item;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = { @ServiceProvider(service = IGamePluginService.class), })

public class ItemPlugin implements IGamePluginService {

    private Entity item;

    public ItemPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // add 5 items of random type
        for (int i = 0; i < 5; i++) {
            item = createItem(gameData);
            world.addEntity(item);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities(PowerUp.class)) {
            world.removeEntity(e);
        }
    }

    private Entity createItem(GameData gameData) {

        float x = (float) (gameData.getDisplayWidth() * Math.random());
        float y = (float) (gameData.getDisplayHeight() * Math.random());

        // get random number in range 1-2
        Random rng = new Random();
        int itemSelect = rng.nextInt(2) + 1;

        Entity newItem;

        switch (itemSelect) {
            case 1:
                newItem = new EnlargePlayerPowerUp();
                break;
            case 2:
                newItem = new EnlargeBulletPowerUp();
                break;
            default:
                newItem = new Entity();
                break;
        }
        
        newItem.add(new PositionPart(x, y));
        newItem.setRadius(12);
        
        return newItem;
    }

}
