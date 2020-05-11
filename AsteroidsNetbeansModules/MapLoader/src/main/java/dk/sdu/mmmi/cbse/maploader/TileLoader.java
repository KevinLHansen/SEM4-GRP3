/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.maploader;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
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
    
    public void load(String mapPath) {
        tiledMap = mapLoader.load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.createWalls(tiledMap);
    }
    
    private void createWalls(TiledMap tiledMap){
        String layerName = "walls";
        
        MapLayer layer = tiledMap.getLayers().get(layerName);
        if (layer == null){
            System.out.println("\n\n\n\n\n\n\nno walls!!!\n\n\n\n\n");
        }
        
        System.out.println(b2dWorld.toString() + "\n\n\n\n\n\n\n\n\n\n");
        
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        for (MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
            
            body = b2dWorld.createBody(bdef);
            
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
            
        }
    }
    
    public World getWorld(){
        return this.b2dWorld;
    }
    
    public OrthogonalTiledMapRenderer getRenderer() {
        return this.mapRenderer;
    }
    
    public Box2DDebugRenderer getB2dRenderer() {
        return this.b2dr;
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