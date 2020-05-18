package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;

// @author Group 3

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private Graph graph;
    private List<Path> pathingDebugList = new ArrayList<>();
    private List<Rectangle> wallList = new ArrayList<>();
    private boolean drawDebug = false;
    private boolean spawnEnemies = false;
    private boolean newGame = true;
    private float score = 0;

    public void setGraph(Graph graph) {
    	this.graph = graph;
    }
    
    public Graph getGraph() {
        return graph;
    } 
    
    public void setWalls(List<Rectangle> walls) {
        this.wallList = walls;
    }
    
    public List<Rectangle> getWalls() {
        return wallList;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public boolean isDrawDebug() {
        return drawDebug;
    }

    public void setDrawDebug(boolean drawDebug) {
        this.drawDebug = drawDebug;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<Path> getPathingDebugList() {
        return pathingDebugList;
    }

    public void setPathingDebugList(List<Path> pathingDebugList) {
        this.pathingDebugList = pathingDebugList;
    }

    public boolean isSpawnEnemies() {
        return spawnEnemies;
    }

    public void setSpawnEnemies(boolean spawnEnemies) {
        this.spawnEnemies = spawnEnemies;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }
}
