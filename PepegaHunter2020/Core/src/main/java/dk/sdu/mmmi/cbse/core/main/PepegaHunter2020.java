 package dk.sdu.mmmi.cbse.core.main;

import dk.sdu.mmmi.cbse.core.main.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.core.main.screens.MenuScreen;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;

public class PepegaHunter2020 extends Game {
    
    private final GameData gameData = new GameData();
    private World world = new World();
    
    public SpriteBatch batch;

    @Override
    public void create() {
        
        batch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        gameData.setDisplayWidth((int) w);
        gameData.setDisplayHeight((int) h);

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
        
        //this.setScreen(new GameScreen(this));
        this.setScreen(new MenuScreen(this));
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
