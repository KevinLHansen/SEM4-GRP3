package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList<>();
    private Graph graph;
    private List<Path> pathingDebugList = new ArrayList<>();
    private List<Rectangle> wallList = new ArrayList<>();
    private boolean drawDebug = false;
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
    
    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
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

    
    
    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }
}