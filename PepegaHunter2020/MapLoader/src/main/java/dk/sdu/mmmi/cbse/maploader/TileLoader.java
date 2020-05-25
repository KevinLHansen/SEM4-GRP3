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
import dk.sdu.mmmi.cbse.common.data.Heuristic;
import java.util.ArrayList;
import java.util.List;

// @author Group 3

public class TileLoader {
    
    private TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    
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
        tileWidth = properties.get("tilewidth", Integer.class);
        mapPixelHeight = mapHeight * tileHeight;
        mapPixelWidth = mapWidth * tileWidth;
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        // Setting tilesize for the heristic class, used to calculate heristic value between two given points.
        Heuristic.getInstance().setTileSize(mapWidth, mapHeight);
    }
    
    // Returns array with rectangles representing walls extracted from specfied TileMap-layer
    public List<Rectangle> createWalls(){
        
        String layerName = "walls";
        
        MapLayer layer = tiledMap.getLayers().get(layerName);
   
        List<Rectangle> walls = new ArrayList<Rectangle>();
        
        for (MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            walls.add(rect);
        }
        
        return walls;
    }
    
    public OrthogonalTiledMapRenderer getRenderer() {
        return this.mapRenderer;
    }
    
    public TiledMap getTiledMap() {
        return tiledMap;
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
