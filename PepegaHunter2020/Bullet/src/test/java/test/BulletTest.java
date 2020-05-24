package test;

import dk.sdu.mmmi.cbse.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ItemPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.playersystem.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BulletTest {
    
    GameData gameData;
    World world;

    Entity player;

    PositionPart playerPos;
    MovingPart playerMov;
    ShootingPart playerShoot;
    LifePart playerLife;
    TimerPart playerTime;
    ItemPart playerItem;
    
    public BulletTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // setup example game world with a Player Entity
        
        world = new World();
        gameData = new GameData();
        gameData.setDisplayHeight(500);
        gameData.setDisplayWidth(500);
        
        Entity player = new Player();
        player.setRadius(16);
        
        playerPos = new PositionPart(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        playerMov = new MovingPart(3);
        playerShoot = new ShootingPart();
        playerLife = new LifePart(3);
        playerTime = new TimerPart();
        playerTime.setTimer(10000000f); // necessary due to firerate limitations
        playerItem = new ItemPart();
        
        player.add(playerPos);
        player.add(playerMov);
        player.add(playerShoot);
        player.add(playerLife);
        player.add(playerTime);
        player.add(playerItem);
        
        world.addEntity(player);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testFiring() {
        // tests whether bullets are succesfully created when the player is shooting
        System.out.println("\n-- Bullet Firing test --\n");
        
        BulletControlSystem bulletCtrlSys = new BulletControlSystem();
        
        // simulate the player shooting
        playerShoot.setIsShooting(true);
        playerShoot.setDirection("up");
        
        // count number of bullets in world before shooting
        int preBulletCount = 0;
        for (Entity entity : world.getEntities()) {
            if (entity.getType().equalsIgnoreCase("bullet")) {
                preBulletCount++;
            }
        }
        System.out.println("preBulletCount: " + preBulletCount);
        
        // process BulletControlSystem
        bulletCtrlSys.process(gameData, world);
        
        // count number of bullets in world after shooting
        int postBulletCount = 0;
        for (Entity entity : world.getEntities()) {
            if (entity.getType().equalsIgnoreCase("bullet")) {
                postBulletCount++;
            }    
        }
        System.out.println("postBulletCount: " + postBulletCount);
        
        boolean expResult = true;
        // assert if result is as expected
        assertEquals(expResult, postBulletCount > preBulletCount);
    }
}
