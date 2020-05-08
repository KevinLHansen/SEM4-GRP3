package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static Game game;

    @Override
    public void restored() {

        game = new Game();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Pepega Hunter 2020";
        cfg.width = 1280;
        cfg.height = 800;
        
        cfg.backgroundFPS = 60;
        cfg.foregroundFPS = 60;
        
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(game, cfg);
    }
}
