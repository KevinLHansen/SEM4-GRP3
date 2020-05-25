package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Heuristic;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AIPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PathFinderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.enemysystem.EnemySpawnSystem;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rasmusstamm
 */
public class EnemyJUnitTest {

    GameData gameData;
    World world;

    int tileWidth;
    int tileHeight;

    int mapWidth;
    int mapHeight;

    public EnemyJUnitTest() {
    }

    @BeforeEach
    public void setUp() {
        System.out.println("\n--- Creating GameData and World for test ---");

        tileWidth = 16;
        tileHeight = 16;
        mapWidth = 30;
        mapHeight = 30;

        gameData = new GameData();
        gameData.setDisplayWidth(mapWidth * tileWidth);
        gameData.setDisplayHeight(mapHeight * tileHeight);

        world = new World();

        Array<Array<Node>> nodes = new Array<Array<Node>>();

        for (int y = 0; y < mapHeight; y++) {
            nodes.add(new Array<>());
            for (int x = 0; x < mapWidth; x++) {
                nodes.get(y).add(new Node(x, y));
            }
        }

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {

                Node targetNode = nodes.get(y).get(x);
                if (y != 0) {
                    Node downNode = nodes.get(y - 1).get(x);
                    targetNode.createConnection(downNode, 1);
                }
                if (y != mapHeight - 1) {
                    Node upNode = nodes.get(y + 1).get(x);
                    targetNode.createConnection(upNode, 1);
                }
                if (x != 0) {
                    Node leftNode = nodes.get(y).get(x - 1);
                    targetNode.createConnection(leftNode, 1);
                }
                if (x != mapWidth - 1) {
                    Node rightNode = nodes.get(y).get(x + 1);
                    targetNode.createConnection(rightNode, 1);
                }

                if (y != mapHeight - 1 && x != 0) {
                    Node upLeftNode = nodes.get(y + 1).get(x - 1);
                    targetNode.createConnection(upLeftNode, 1);
                }
                if (y != mapHeight - 1 && x != mapWidth - 1) {
                    Node upRightNode = nodes.get(y + 1).get(x + 1);
                    targetNode.createConnection(upRightNode, 1);
                }
                if (y != 0 && x != 0) {
                    Node downLeftNode = nodes.get(y - 1).get(x - 1);
                    targetNode.createConnection(downLeftNode, 1);
                }
                if (y != 0 && x != mapWidth - 1) {
                    Node downRightNode = nodes.get(y - 1).get(x + 1);
                    targetNode.createConnection(downRightNode, 1);
                }
            }
        }

        Graph graph = new Graph(tileWidth, tileHeight, nodes);

        gameData.setGraph(graph);

        Heuristic.getInstance().setTileSize(tileWidth, tileHeight);

        System.out.println("Nodes in graph: " + gameData.getGraph().getNodeCount() * gameData.getGraph().getNodeCount());

        PlayerPlugin pp = new PlayerPlugin();
        pp.start(gameData, world);
    }

    @Test
    public void testSpawner() {
        System.out.println("\n--- Spawning enemies ---");

        EnemyPlugin plugin = new EnemyPlugin();
        EnemySpawnSystem ess = new EnemySpawnSystem();

        plugin.start(gameData, world);
        ess.process(gameData, world);

        int counter = 0;
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == "enemy") {
                System.out.println("Enemy was created: " + entity);
                counter++;
            }
        }

        boolean expResult = true;

        assertEquals(expResult, counter > 0);

    }

    @Test
    public void testEnemyMovement() {

        System.out.println("\n--- Enemy AI test ---");

        EnemyPlugin plugin = new EnemyPlugin();
        EnemySpawnSystem ess = new EnemySpawnSystem();
        EnemyControlSystem ecs = new EnemyControlSystem();
        plugin.start(gameData, world);
        ess.process(gameData, world);

        Enemy testEnemy = null;
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == "enemy") {
                testEnemy = (Enemy) entity;
            }
        }

        PositionPart pp = testEnemy.getPart(PositionPart.class);
        System.out.println(pp);

        float preX = pp.getX();
        float preY = pp.getY();

        System.out.println("Old position: " + preX + ", " + preY);

        ecs.process(gameData, world);

        System.out.println("New position: " + pp.getX() + ", " + pp.getY());

        boolean expectedResult = true;

        boolean result = (preX != pp.getX()) || (preY != pp.getY());

        assertEquals(expectedResult, result);

    }
}
