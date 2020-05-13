package dk.sdu.mmmi.cbse.core.main;

// @author Kevin Hansen

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class MenuScreen implements Screen {
    
    PepegaHunter2020 game;
    
    public MenuScreen(PepegaHunter2020 game) {
        this.game = game;
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int i, int i1) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }

}
