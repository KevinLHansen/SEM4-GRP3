package dk.sdu.mmmi.cbse.common.data;

// @author Kevin Hansen

// Holds information about how to config an entity's sprite
public class SpriteConfig {
    int width;
    int height;
    float scale;
    // room for more sprite attributes
    
    public SpriteConfig(int width, int height, float scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }
    
    public SpriteConfig() {
        width = 20;
        height = 20;
        scale = 1;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}


