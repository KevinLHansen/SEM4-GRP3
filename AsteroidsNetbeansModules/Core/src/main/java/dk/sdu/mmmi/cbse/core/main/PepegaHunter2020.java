 package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;

public class PepegaHunter2020 extends Game implements ApplicationListener {
    
    private final GameData gameData = new GameData();
    private World world = new World();

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        gameData.setDisplayWidth((int) w);
        gameData.setDisplayHeight((int) h);

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
        
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
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

    public GameData getGameData() {
        return gameData;
    }

    public World getWorld() {
        return world;
    }
}
