/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.maploader;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rasmusstamm
 */
public class TileLoader {
    private final World b2dWorld = new World(new Vector2(0, 0), true);
    
    private TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer b2dr = new Box2DDebugRenderer();
    
    private static int mapHeight;
    private static int mapWidth;
    private static int tileHeight;
    private static int tileWidth;
    private static int mapPixelHeight;
    private static int mapPixelWidth;
    
    public void load(String mapPath) {
        tiledMap = mapLoader.load(mapPath);
        MapProperties properties = tiledMap.getProperties();
        mapHeight = properties.get("height", Integer.class);
        mapWidth = properties.get("width", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        tileWidth = properties.get("tileWidth", Integer.class);
        mapPixelHeight = mapHeight * tileHeight;
        mapPixelWidth = mapWidth * tileWidth;
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
    
    public List<Rectangle> createWalls(){
        String layerName = "walls";
        
        MapLayer layer = tiledMap.getLayers().get(layerName);
        if (layer == null){
            System.out.println("\n\n\n\n\n\n\nno walls!!!\n\n\n\n\n");
        }
        
        System.out.println(b2dWorld.toString() + "\n\n\n\n\n\n\n\n\n\n");
        
        List<Rectangle> walls = new ArrayList<Rectangle>();
        
        for (MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            walls.add(rect);
        }
        
        return walls;
    }
    
    public World getWorld(){
        return this.b2dWorld;
    }
    
    public TiledMap getTiledMap() {
        return tiledMap;
    }
    
    public OrthogonalTiledMapRenderer getRenderer() {
        return this.mapRenderer;
    }
    
    public Box2DDebugRenderer getB2dRenderer() {
        return this.b2dr;
    }

    public static int getMapHeight() {
        return mapHeight;
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static int getTileWidth() {
        return tileWidth;
    }
    
   
}
