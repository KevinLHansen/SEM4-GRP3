package dk.sdu.mmmi.cbse.core.managers;

// @author Kevin Hansen

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.IOException;
import org.openide.util.Exceptions;

public class SpriteLoader {

    // create Sprite from image path
    public static Sprite loadSprite(String filePath) {
        byte[] textureBytes = null;
        try {
            textureBytes = Entity.class.getResourceAsStream(filePath).readAllBytes();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        Pixmap pixmap = new Pixmap(textureBytes, 0, textureBytes.length);
        return new Sprite(new Texture(pixmap));
    }
    
    // create Sprite from InputStream byte array
    public static Sprite loadSprite(byte[] textureBytes) {
        Pixmap pixmap = new Pixmap(textureBytes, 0, textureBytes.length);
        return new Sprite(new Texture(pixmap));
    } 
    
    // create Texture from image path
    public static Texture loadTexture(String filePath) {
        byte[] textureBytes = null;
        try {
            textureBytes = Entity.class.getResourceAsStream(filePath).readAllBytes();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        Pixmap pixmap = new Pixmap(textureBytes, 0, textureBytes.length);
        return new Texture(pixmap);
    }
    
}
