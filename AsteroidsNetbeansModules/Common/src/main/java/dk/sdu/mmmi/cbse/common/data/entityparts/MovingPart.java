package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class MovingPart implements EntityPart {

    private float dx, dy;
    private boolean left, right, up, down;
    private int speed;

    public MovingPart(int speed) {
        this.speed = speed;
    }

    public MovingPart() {
        speed = 3;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float delta = gameData.getDelta();

        // move entity by altering coordinates depending on input
        if (up)    { y +=  speed; }
        if (left)  { x += -speed; }
        if (down)  { y += -speed; }
        if (right) { x +=  speed; }

        // prevent entity from leaving world boundaries
        if (x > gameData.getDisplayWidth() || x < 0) { x = positionPart.getX(); }
        if (y > gameData.getDisplayHeight() || y < 0) { y = positionPart.getY(); }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
