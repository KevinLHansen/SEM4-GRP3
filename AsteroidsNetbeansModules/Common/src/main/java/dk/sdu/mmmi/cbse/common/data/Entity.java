package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.openide.util.Exceptions;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private float[] colour;
    private byte[] textureBytes;
    private Sprite sprite;
    private Map<Class, EntityPart> parts;
    private String type;
    private SpriteConfig spriteCfg;

    public Entity() {

        parts = new ConcurrentHashMap<>();
        spriteCfg = new SpriteConfig();
        type = "entity";
        try {
            // assign default texture
            InputStream stream = Entity.class.getResourceAsStream("/img/default.png");
            byte[] bytes = stream.readAllBytes();
            textureBytes = bytes;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void assignTexture(String filePath) {
        try {
            this.textureBytes = Entity.class.getResourceAsStream(filePath).readAllBytes();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public float[] getColour() {
        return this.colour;
    }

    public void setColour(float[] c) {
        this.colour = c;
    }

    public byte[] getTextureBytes() {
        return textureBytes;
    }

    public void setTextureBytes(byte[] textureBytes) {
        this.textureBytes = textureBytes;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteConfig getSpriteCfg() {
        return spriteCfg;
    }

    public void setSpriteConfig(int width, int height, float scale) {
        spriteCfg = new SpriteConfig(width, height, scale);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
