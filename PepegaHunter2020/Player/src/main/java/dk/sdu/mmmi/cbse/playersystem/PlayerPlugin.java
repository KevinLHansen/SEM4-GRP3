package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ItemPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

// @author Group 3

@ServiceProviders(value = { @ServiceProvider(service = IGamePluginService.class), })

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // add player entity to world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // remove player from world
        world.removeEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        
        float x = gameData.getDisplayWidth() / 2;
        float y = 370; //gameData.getDisplayHeight() / 2;

        player = new Player();
        
        player.setRadius(16);
        
        player.add(new MovingPart(3));
        
        player.add(new PositionPart(x, y));
        
        UUID uuid = UUID.randomUUID();
        ShootingPart shootingPart = new ShootingPart(uuid.toString());
        shootingPart.setBulletRadius(5);
        shootingPart.setFireRate(200);
        player.add(shootingPart);
        player.add(new LifePart(3));
        player.add(new TimerPart());
        player.add(new ItemPart());
        
        player.setOrigin(new Entity(player));
        
        return player;
    }
}
