package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.SpriteConfig;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class Game implements ApplicationListener {

    private static OrthographicCamera camera;
    private SpriteBatch batch;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    private Texture background;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap tiledMap;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        gameData.setDisplayWidth((int) w);
        gameData.setDisplayHeight((int) h);

        // Background
        InputStream streamBg = Entity.class.getResourceAsStream("/img/background.png");
        byte[] bytesBg;
        try {
            bytesBg = streamBg.readAllBytes();
            background = new Texture(new Pixmap(bytesBg, 0, bytesBg.length));
            background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Tilemap
        loadMap("/tilemap/tilemap.tmx");
        
        // Camera
        float aspectRatio = h / w;
        camera = new OrthographicCamera(600, 600 * aspectRatio);
        camera.update(); 

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Background
        batch.begin();
        batch.draw(background, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        // convert delta (seconds) to milliseconds and update deltaTotal
        gameData.setDeltaTotal(gameData.getDeltaTotal() + (int) (gameData.getDelta() * 1000));

        updateServices();
        drawMap();
        drawSprites();
        updateCamera();
    }

    private void loadMap(String mapPath) {
        //TmxMapLoader mapLoader = new TmxMapLoader(new AbsoluteFileHandleResolver());
        TmxMapLoader mapLoader = new TmxMapLoader(new ExternalFileHandleResolver()); // TEMPORARY SOLUTION
        tiledMap = mapLoader.load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
    
    private void drawMap() {
        mapRenderer.setView((OrthographicCamera) camera);
        mapRenderer.render();
    }
    
    private void updateServices() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void drawSprites() {
        batch.begin();

        for (Entity entity : world.getEntities()) {
            // if sprite has not already been created for entity
            if (entity.getSprite() == null) {
                // get byte array from entity and convert to texture
                Pixmap pixmap = new Pixmap(entity.getTextureBytes(), 0, entity.getTextureBytes().length);
                Sprite sprite = new Sprite((new Texture(pixmap)));
                // get SpriteConfig from entity
                SpriteConfig spriteCfg = entity.getSpriteCfg();
                sprite.setSize(spriteCfg.getWidth(), spriteCfg.getHeight());
                sprite.setScale(spriteCfg.getScale());
                entity.setSprite(sprite);
            }
            // get positionPart to attach sprite to position
            PositionPart pp = entity.getPart(PositionPart.class);
            // configure and draw sprite using positionpart
            Sprite sprite = entity.getSprite();
            sprite.setCenter(pp.getX(), pp.getY());
            sprite.draw(batch);   
        }
        batch.end();
    }
    
    public void updateCamera() {
        // find player and set camera to its location
        for (Entity entity : world.getEntities()) {
            if (entity.getType().toLowerCase() == "player") {
                PositionPart pp = entity.getPart(PositionPart.class);
                float newX;
                float newY;
                float playerX = pp.getX();
                float playerY = pp.getY();
                
                int mapWidth = tiledMap.getProperties().get("width", Integer.class);
                int mapHeight = tiledMap.getProperties().get("height", Integer.class);
                int tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
                int tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
                
                // prevent camera from showing out of bounds area when near edge of world
                if (playerX > mapWidth * tileWidth - camera.viewportWidth / 2 || playerX < 0 + camera.viewportWidth / 2) {
                    newX = camera.position.x;
                } else {
                    newX = playerX;
                }
                
                if (playerY > mapHeight * tileHeight - camera.viewportHeight / 2 || playerY < 0 + camera.viewportHeight / 2) {
                    newY = camera.position.y;
                } else {
                    newY = playerY;
                }
                camera.position.set(newX, newY, 0);
            }
        }
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}
