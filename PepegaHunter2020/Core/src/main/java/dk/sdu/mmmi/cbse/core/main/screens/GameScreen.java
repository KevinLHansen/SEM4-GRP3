package dk.sdu.mmmi.cbse.core.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Graph;
import dk.sdu.mmmi.cbse.common.data.Node;
import dk.sdu.mmmi.cbse.common.data.Path;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.main.PepegaHunter2020;
import dk.sdu.mmmi.cbse.core.main.scenes.Hud;
import dk.sdu.mmmi.cbse.core.managers.SpriteLoader;
import dk.sdu.mmmi.cbse.maploader.GraphGenerator;
import dk.sdu.mmmi.cbse.maploader.TileLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

// @author Group 3

public class GameScreen implements Screen {
    
    private PepegaHunter2020 game;
    private GameData gameData;
    private World world;
    private Entity player;
    private int maxLife = -1;
    private Graph tileGraph;

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private SpriteBatch hudBatch;
    
    private Viewport gamePort;
    private Hud hud;
    private Texture background;
    private TileLoader tileLoader;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer b2dr;
    
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private final Lookup lookup = Lookup.getDefault();
    private Lookup.Result<IGamePluginService> result;
   
    
    public GameScreen(PepegaHunter2020 game) {
        this.game = game;
        this.gameData = game.getGameData();
        this.world = game.getWorld();
    }

    @Override
    public void show() {
        gameData.setNewGame(true);
        
        shapeRenderer = new ShapeRenderer();
        hudBatch = new SpriteBatch();
        
        float w = gameData.getDisplayWidth();
        float h = gameData.getDisplayHeight();
        
        // HUD
        gamePort = new FitViewport(w, h, camera);
        hud = new Hud(gameData, hudBatch);
        
        // Background
        background = SpriteLoader.loadTexture("/img/background.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        
        // Tilemap
        tileLoader = new TileLoader();
        tileLoader.load("tilemap.tmx");
        
        tileGraph = GraphGenerator.generateGraph(tileLoader.getTiledMap());
        gameData.setGraph(tileGraph);
        
        // assign walls to GameData
        gameData.setWalls(tileLoader.createWalls());
        
        mapRenderer = tileLoader.getRenderer();
        b2dr = tileLoader.getB2dRenderer();
        
        // Camera
        float aspectRatio = h / w;
        camera = new OrthographicCamera(600, 600 * aspectRatio);
        
        // Modules
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    @Override
    public void render(float delta) {
        
        // get player and check if player is dead (removed from world)
        boolean playerIsAlive = false;
        for (Entity entity : world.getEntities()) {
            if ("player".equalsIgnoreCase(entity.getType())) {
                player = entity;
                if (maxLife == -1) {
                    LifePart life = player.getPart(LifePart.class);
                    maxLife = life.getLife();
                }
                playerIsAlive = true;
                break;
            } 
        } // if dead, restart screen
        if (!playerIsAlive) {
            game.setScreen(new MenuScreen(game));
        }
        
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float w = gameData.getDisplayWidth();
        float h = gameData.getDisplayHeight();

        // Background
        game.batch.begin();
        game.batch.draw(background, 0, 0, 0, 0, (int) w, (int) h);
        game.batch.end();

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        
        updateServices();
        updateCamera();
        drawMap();
        drawSprites();
        drawHud();
        drawDebug(); // debug drawing. Toggleable on M key
    }

    private void drawMap() {
        mapRenderer.setView((OrthographicCamera) camera);
        mapRenderer.render();
        b2dr.render(tileLoader.getWorld(), camera.combined);
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
        game.batch.begin();

        for (Entity entity : world.getEntities()) {
            Sprite sprite;
            // if sprite has not already been created for entity
            if (entity.getSprite() == null) {
                // get byte array from entity and convert to texture
                sprite = SpriteLoader.loadSprite(entity.getTextureBytes());
                entity.setSprite(sprite);
            } else {
                sprite = entity.getSprite();
            }

            // get positionPart to attach sprite to position
            PositionPart pp = entity.getPart(PositionPart.class);
            // configure and draw sprite using positionpart and entity radius
            sprite.setSize(entity.getRadius() * 2, entity.getRadius() * 2);
            sprite.setCenter(pp.getX(), pp.getY());
            sprite.draw(game.batch);
            entity.setSprite(sprite);
        }
        game.batch.end();
    }

    public void updateCamera() {
        // find player and set camera to its location
        PositionPart pp = player.getPart(PositionPart.class);
        float newX;
        float newY;
        float playerX = pp.getX();
        float playerY = pp.getY();

        int mapWidth = TileLoader.getMapWidth();
        int mapHeight = TileLoader.getMapHeight();
        int tileWidth = TileLoader.getTileWidth();
        int tileHeight = TileLoader.getTileHeight();

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
        camera.update();
        // make renderers render according to camera view
        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void drawHud() {
        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.update(gameData);
        hud.stage.draw();
        
        PositionPart playerPos = player.getPart(PositionPart.class);
        LifePart life = player.getPart(LifePart.class);
        float lifeBarX = playerPos.getX() - player.getRadius();
        float lifeBarY = playerPos.getY() - (player.getRadius() + 5);
        float lifeBarW = (player.getRadius() * 2) * life.getLife() / maxLife;
        float lifeBarH = 2;
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 0.7f);
        shapeRenderer.rect(lifeBarX, lifeBarY, lifeBarW, lifeBarH);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    
    public void drawDebug() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if (gameData.isDrawDebug()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            float alpha = 1;
            // draw radius circle for every entity
            for (Entity entity : world.getEntities()) {
                PositionPart pp = entity.getPart(PositionPart.class);
                String entityType = entity.getType();
                switch (entityType) {
                    case "player":
                        shapeRenderer.setColor(0, 1, 1, alpha);
                        break;
                    case "enemy":
                        shapeRenderer.setColor(1, 0, 0, alpha);
                        break;
                    case "enlargeplayerpowerup":
                        shapeRenderer.setColor(0, 1, 0, alpha);
                        break;
                    case "enlargebulletpowerup":
                        shapeRenderer.setColor(0, 1, 0, alpha);
                        break;
                    case "increasefireratepowerup":
                        shapeRenderer.setColor(0, 1, 0, alpha);
                        break;
                    default:
                        shapeRenderer.setColor(1, 1, 1, alpha);
                }
                shapeRenderer.circle(pp.getX(), pp.getY(), entity.getRadius());
                shapeRenderer.circle(pp.getX(), pp.getY(), 1);
            }
            shapeRenderer.setColor(0.8f, 0.8f, 0.8f, alpha);
            // draw outline for all walls
            for (Rectangle wall : gameData.getWalls()) {
                shapeRenderer.rect(wall.x, wall.y, wall.width, wall.height);
            }
            // draw tiles with pathfinding information
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 1, 0.5f);
            for (int i = 0; i < tileGraph.getNodeCount(); i++) {
                for (int j = 0; j < tileGraph.getNodes().get(i).size; j++) {
                    if (tileGraph.getNodes().get(i).get(j).getType() == 1) {
                        shapeRenderer.circle(tileGraph.getNodes().get(i).get(j).getX(), tileGraph.getNodes().get(i).get(j).getY(), TileLoader.getTileHeight() / 2);
                    }
                }
            }
            // draw paths being calculated
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, 0.5f);
            List<Path> pathList = gameData.getPathingDebugList();
            // add paths from one list to other, to avoid ConcurrentModifcationException
            List<Path> drawList = new ArrayList<>();
            drawList.addAll(pathList);
            for (Path path : drawList) {
                for (Node node : path.getNodes()) {
                    shapeRenderer.circle(node.getX(), node.getY(), 5);
                }
            }
            pathList.clear();
            shapeRenderer.end();
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);
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

    @Override
    public void hide() {
        for (Entity entity : world.getEntities()) {
            world.removeEntity(entity);
            gameData.setScore(0);        
        }
    }
}
