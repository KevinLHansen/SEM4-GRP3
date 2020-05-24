package test;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.S;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ItemPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// @author Group 3

public class PlayerTest {
    
    GameData gameData;
    World world;
    
    PositionPart playerPos;
    MovingPart playerMov;
    ShootingPart playerShoot;
    LifePart playerLife;
    TimerPart playerTime;
    ItemPart playerItem;
    
    public PlayerTest() {
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
    public void testAdding() {
        // tests whether the player entity is successfully added to the game world and referrable
        System.out.println("\n-- Player Adding test --\n");
        
        Entity player = null;
        for (Entity entity : world.getEntities(Player.class)) {
            player = entity;
        }
        
        boolean expResult = true;
        assertEquals(expResult, player != null);
    }
    
    @Test
    public void testMovement() {
        // tests whether a player entity succesfully moves within world as a result of receiving a movement command
        System.out.println("\n-- Player Movement test --\n");
        
        PlayerControlSystem playerCtrlSys = new PlayerControlSystem();
        
        // simulate DOWN being pressed
        gameData.getKeys().setKey(S, true);
        // y-coordinate of player before movement
        float preY = playerPos.getY();
        System.out.println("preY: " + preY);
        
        // process PlayerControlSystem
        playerCtrlSys.process(gameData, world);
        
        // new y-coordinate
        float postY = playerPos.getY();
        System.out.println("preX: " + postY);
        
        // check if player has successfully moved downwards (y coord should be lower)
        boolean result;
        if (postY < preY) {
            result = true;
        } else {
            result = false;
        }
        
        boolean expResult = true;
        // assert if result is as expected
        assertEquals(expResult, result);
    }
}
