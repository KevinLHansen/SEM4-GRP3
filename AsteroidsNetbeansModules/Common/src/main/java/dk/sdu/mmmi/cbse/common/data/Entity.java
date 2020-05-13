package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.openide.util.Exceptions;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private float radius;
    private byte[] textureBytes;
    private Sprite sprite;
    private Map<Class, EntityPart> parts;
    private String type;
    private Entity origin; // stores a copy of the original version of the object. For use when reverting item effects

    public Entity() {

        parts = new ConcurrentHashMap<>();
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
    
    // copy-constructor for making clones of instances (for use with origin)
    public Entity(Entity clonee) {
        
        parts = new ConcurrentHashMap<>();
        type = "entity";
        
        this.radius = clonee.radius;
        
        // make a clone of clonee's shootingpart
        ShootingPart shootingPart = clonee.getPart(ShootingPart.class);
        ShootingPart shootingPartClone = new ShootingPart();
        shootingPartClone.setBulletRadius(shootingPart.getBulletRadius());
        shootingPartClone.setFireRate(shootingPart.getFireRate());
        this.add(shootingPartClone);
        // potentially more relevant attributes
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Entity getOrigin() {
        return origin;
    }
    
    public void setOrigin(Entity origin) {
        this.origin = origin;
    }
    
}
