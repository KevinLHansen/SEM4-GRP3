package dk.sdu.mmmi.cbse.core.main.screens;

// @author Kevin Hansen

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.core.main.PepegaHunter2020;
import dk.sdu.mmmi.cbse.core.managers.SpriteLoader;

public class MenuScreen implements Screen {
    
    private PepegaHunter2020 game;
    private GameData gameData;
    
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    
    private static final int BANNER_W = 1000;
    private static final int MENU_ITEM_H = 80;
    
    private Texture background;
    private Texture banner;
    private Texture playButton;
    private Texture playButtonSelect;
    private Texture quitButton;
    private Texture quitButtonSelect;
    
    private int displayWidth;
    private int displayHeight;
    
    public MenuScreen(PepegaHunter2020 game) {
        this.game = game;
        this.gameData = game.getGameData();
    }

    @Override
    public void show() {
        displayWidth = gameData.getDisplayWidth();
        displayHeight = gameData.getDisplayHeight();
        
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        
        background =        SpriteLoader.loadTexture("/img/background.png");
        banner =            SpriteLoader.loadTexture("/img/menu/banner.png");
        playButton =        SpriteLoader.loadTexture("/img/menu/play.png");
        playButtonSelect =  SpriteLoader.loadTexture("/img/menu/play_select.png");
        quitButton =        SpriteLoader.loadTexture("/img/menu/quit.png");
        quitButtonSelect =  SpriteLoader.loadTexture("/img/menu/quit_select.png");
        
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void render(float f) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Gdx.input.getY is flipped, so displayHeight is substracted to match how everything else is drawn
        Vector2 mousePos = new Vector2(Gdx.input.getX(), displayHeight - Gdx.input.getY());
        
        batch.begin();
        batch.draw(background, 0, 0, 0, 0, (int) displayWidth, (int) displayHeight);
        batch.end();
        
        // to keep aspect ratio of image, scale height by same factor as width
        int bannerW = BANNER_W;
        int bannerH = banner.getHeight() * (bannerW / banner.getWidth());
        float bannerY = (float) (displayHeight * 0.8);
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
        shapeRenderer.rect(0, bannerY - 25, displayWidth, bannerH + 45);
        shapeRenderer.end();
        
        batch.begin();
        batch.draw(banner, displayWidth / 2 - bannerW / 2, bannerY, bannerW, bannerH);
        
        
        int playBtnH = MENU_ITEM_H;
        int quitBtnH = MENU_ITEM_H;
        int playBtnW = playButton.getWidth() * (playBtnH / playButton.getHeight());
        int quitBtnW = quitButton.getWidth() * (quitBtnH / quitButton.getHeight());
        
        float quitBtnY = (float) (displayHeight * 0.2);
        float quitBtnX = displayWidth / 2 - quitBtnW / 2;
        float playBtnY = quitBtnY + quitBtnH + 50;
        float playBtnX = displayWidth / 2 - playBtnW / 2;
        
        // when hovering playButton
        if (mousePos.x > playBtnX && mousePos.x < playBtnX + playBtnW && mousePos.y > playBtnY && mousePos.y < playBtnY + playBtnH) {
            batch.draw(playButtonSelect, playBtnX, playBtnY, playBtnW, playBtnH);
            // if click, go to game screen
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(game));
            }
        } else {
           batch.draw(playButton, displayWidth / 2 - playBtnW / 2, playBtnY, playBtnW, playBtnH); 
        }
        // when hovering quitButton
        if (mousePos.x > quitBtnX && mousePos.x < quitBtnX + quitBtnW && mousePos.y > quitBtnY && mousePos.y < quitBtnY + quitBtnH) {
            batch.draw(quitButtonSelect, quitBtnX, quitBtnY, quitBtnW, quitBtnH);
            // if click, close application
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
           batch.draw(quitButton, quitBtnX, quitBtnY, quitBtnW, quitBtnH);
        }
        batch.end();
        
        // draw mouse position
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 0.2f);
        shapeRenderer.circle(mousePos.x, mousePos.y, 5);
        shapeRenderer.end();
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
