package test;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ItemPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.item.ItemSystem;
import dk.sdu.mmmi.cbse.item.powerups.EnlargeBulletPowerUp;
import dk.sdu.mmmi.cbse.item.powerups.EnlargePlayerPowerUp;
import dk.sdu.mmmi.cbse.item.powerups.IncreaseFireRatePowerUp;
import dk.sdu.mmmi.cbse.item.powerups.PowerUp;
import dk.sdu.mmmi.cbse.playersystem.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// @author Group 3

public class ItemTest {

    GameData gameData;
    World world;

    Entity player;

    PositionPart playerPos;
    MovingPart playerMov;
    ShootingPart playerShoot;
    LifePart playerLife;
    TimerPart playerTime;
    ItemPart playerItem;

    public ItemTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // setup example game world with a Player Entity and PowerUp Entity

        world = new World();
        gameData = new GameData();
        gameData.setDisplayHeight(500);
        gameData.setDisplayWidth(500);

        // create and add player to world
        player = new Player();
        player.setRadius(16);

        playerPos = new PositionPart(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        playerMov = new MovingPart(3);
        playerShoot = new ShootingPart();
        playerShoot.setBulletRadius(5);
        playerShoot.setFireRate(200);
        playerLife = new LifePart(3);
        playerTime = new TimerPart();
        playerItem = new ItemPart();

        player.add(playerPos);
        player.add(playerMov);
        player.add(playerShoot);
        player.add(playerLife);
        player.add(playerTime);
        player.add(playerItem);

        player.setOrigin(new Entity(player));

        world.addEntity(player);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAdding() {
        // tests whether the item entity is successfully added to the game world and referrable
        System.out.println("\n-- Item Adding test --\n");
        
        Entity powerUp = new IncreaseFireRatePowerUp();
        world.addEntity(powerUp);
        
        Entity item = null;
        for (Entity entity : world.getEntities()) {
            if (entity instanceof PowerUp) {
                item = entity;
            }
        }
        boolean expResult = true;
        assertEquals(expResult, item != null);
    }
    
    @Test
    public void testIncreaseFirerate() {
        // tests whether PowerUp EnlargePlayerPowerUp properly affects player
        System.out.println("\n-- Increase FireRate test --\n");

        ItemSystem itemSys = new ItemSystem();

        Entity powerUp = new IncreaseFireRatePowerUp();
        powerUp.setRadius(12);
        // place powerup on top of player to trigger collision
        PositionPart itemPos = new PositionPart(playerPos.getX(), playerPos.getY());
        powerUp.add(itemPos);

        world.addEntity(powerUp);
        
        // firerate of player before colliding with powerup
        int preFireRate = playerShoot.getFireRate();
        System.out.println("preFireRate: " + preFireRate);
        
        // process ItemSystem
        itemSys.process(gameData, world);
        
        // firerate of player after colliding with powerup
        int postFireRate = playerShoot.getFireRate();
        System.out.println("postFireRate: " + postFireRate);

        boolean expResult = true;

        // assert if result is as expected
        Assertions.assertEquals(expResult, postFireRate < preFireRate);
    }
    
    @Test
    public void testEnlargePlayer() {
        // tests whether PowerUp EnlargePlayerPowerUp properly affects player
        System.out.println("\n-- Enlarge Player test --\n");

        ItemSystem itemSys = new ItemSystem();

        Entity powerUp = new EnlargePlayerPowerUp();
        powerUp.setRadius(12);
        // place powerup on top of player to trigger collision
        PositionPart itemPos = new PositionPart(playerPos.getX(), playerPos.getY());
        powerUp.add(itemPos);

        world.addEntity(powerUp);
        
        // radius of player before colliding with powerup
        float preRadius = player.getRadius();
        System.out.println("preRadius:" + preRadius);
        
        // process ItemSystem
        itemSys.process(gameData, world);
        
        // radius of player after colliding with powerup
        float postRadius = player.getRadius();
        System.out.println("postRadius: " + postRadius);

        boolean expResult = true;

        // assert if result is as expected
        Assertions.assertEquals(expResult, postRadius > preRadius);
    }
    
    @Test
    public void testEnlargeBullet() {
        // tests whether PowerUp EnlargeBulletPowerUp properly affects bullets
        System.out.println("\n-- Enlarge Bullet test --\n");

        ItemSystem itemSys = new ItemSystem();

        Entity powerUp = new EnlargeBulletPowerUp();
        powerUp.setRadius(12);
        // place powerup on top of player to trigger collision
        PositionPart itemPos = new PositionPart(playerPos.getX(), playerPos.getY());
        powerUp.add(itemPos);

        world.addEntity(powerUp);
        
        // radius of bullets before colliding with powerup
        float preRadius = playerShoot.getBulletRadius();
        System.out.println("preRadius: " + preRadius);
        
        // process ItemSystem
        itemSys.process(gameData, world);
        
        // radius of bullets after colliding with powerup
        float postRadius = playerShoot.getBulletRadius();
        System.out.println("postRadius: " + postRadius);
        
        boolean expResult = true;

        // assert if result is as expected
        Assertions.assertEquals(expResult, postRadius > preRadius);
    }
}
