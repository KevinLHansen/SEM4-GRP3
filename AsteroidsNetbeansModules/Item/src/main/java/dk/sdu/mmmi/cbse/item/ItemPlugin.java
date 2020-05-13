package dk.sdu.mmmi.cbse.item;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.item.powerups.*;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.item.powerups.IncreaseFireRatePowerUp;
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
        // add items of random type
        int itemCount = 10;
        for (int i = 0; i < itemCount; i++) {
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

        float x = 69; // default x
        float y = 420; // default y
        float radius = 12;
        
        boolean isClear = false;
        
        while (!isClear) {  
            // place item randomly
            x = (float) (gameData.getDisplayWidth() * Math.random());
            y = (float) (gameData.getDisplayHeight() * Math.random());
            
            boolean inWall = false;
            // prevent item from spawning inside wall
            for (Rectangle wall : gameData.getWalls()) {
                if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                    inWall = true;
                }
            }
            if (!inWall) { isClear = true; }
        }

        // get random number in range 1-3
        Random rng = new Random();
        int itemSelect = rng.nextInt(3) + 1;

        Entity newItem;

        switch (itemSelect) {
            case 1:
                newItem = new EnlargePlayerPowerUp();
                break;
            case 2:
                newItem = new EnlargeBulletPowerUp();
                break;
            case 3:
                newItem = new IncreaseFireRatePowerUp();
                break;
            default:
                newItem = new Entity();
                break;
        }
        
        newItem.add(new PositionPart(x, y));
        newItem.setRadius(radius);
        
        return newItem;
    }

}
