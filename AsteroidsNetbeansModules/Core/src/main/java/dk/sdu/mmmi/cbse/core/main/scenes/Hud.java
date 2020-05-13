package dk.sdu.mmmi.cbse.core.main.scenes;

// @author Kevin Hansen

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    
    private float timeCount;
    private float score;
    
    Label scoreLabel;
    Label timeCountLabel;
    
    public Hud(GameData gameData, SpriteBatch batch) {
        timeCount = 0;
        score = 0;
        
        viewport = new FitViewport(gameData.getDisplayWidth(), gameData.getDisplayHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        BitmapFont font = new BitmapFont();
        font.setScale(3);
        
        scoreLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        timeCountLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        
        table.add(scoreLabel).expandX().padLeft(50).padTop(50);
        table.add(timeCountLabel).expandX().padRight(50).padTop(50);
        
        stage.addActor(table);
    }
    
    public void update(GameData gameData) {
        float delta = gameData.getDelta();
        
        timeCount += delta;
        timeCountLabel.setText("TIME: " + (int) timeCount);
        
        float scoreDiff = gameData.getScore() - score;
        score += scoreDiff;
        score += delta;
        gameData.setScore(score);
        scoreLabel.setText("SCORE: " + (int) score);
        
    }
}
