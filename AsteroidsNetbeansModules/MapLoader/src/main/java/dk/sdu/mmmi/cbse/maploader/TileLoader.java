/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.maploader;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 *
 * @author rasmusstamm
 */
public class TileLoader {
    //private final World b2dWorld = new World(new Vector2(0, 0), true);
    
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    
    public TileLoader(TmxMapLoader mapLoader){
        this.mapLoader = mapLoader;
    }
    
    public void load(String mapPath) {
        tiledMap = mapLoader.load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.createWalls(tiledMap);
    }
    
    private void createWalls(TiledMap tiledMap){
        
    }
    
    /*public World getWorld(){
        return this.b2dWorld;
    }*/
    
    public OrthogonalTiledMapRenderer getRenderer() {
        return this.mapRenderer;
    }
    
    public int getMapHeight(){
        return tiledMap.getProperties().get("height", Integer.class);
    }
    
    public int getMapWidth(){
        return tiledMap.getProperties().get("width", Integer.class);
    }
    
    public int getTileWidth(){
        return tiledMap.getProperties().get("tilewidth", Integer.class);
    }
    
    public int getTileHeight(){
        return tiledMap.getProperties().get("tileheight", Integer.class);
    }
}
