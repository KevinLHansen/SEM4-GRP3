package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;

// @author Group 3

public class GameInputProcessor extends InputAdapter {

    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int k) {
        
        if (k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.W, true);
        }
        if (k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.A, true);
        }
        if (k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.S, true);
        }
        if (k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.D, true);
        }
        if (k == Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, true);
        }
        if (k == Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if (k == Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if (k == Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if (k == Keys.M) {
            gameData.getKeys().setKey(GameKeys.M, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if (k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.W, false);
        }
        if (k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.A, false);
        }
        if (k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.S, false);
        }
        if (k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.D, false);
        }
        if (k == Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, false);
        }
        if (k == Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if (k == Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if (k == Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if (k == Keys.M) {
            gameData.getKeys().setKey(GameKeys.M, false);
        } 
        return true;
    }
}
