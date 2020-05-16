package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.List;

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
        float radius = entity.getRadius();
        float delta = gameData.getDelta();
        
        
        // move entity by altering coordinates depending on input
        float movSpeed;
        // to prevent entity moving double speed when moving diagonally
        if (up && left || up && right || down && left || down && right) {
            movSpeed = speed * 0.8f;
        } else {
            movSpeed = speed;
        }
        
        if (up)    { y +=  movSpeed; }
        if (left)  { x += -movSpeed; }
        if (down)  { y += -movSpeed; }
        if (right) { x +=  movSpeed; }

        float tempY = y;
        
        // prevent entity from leaving world boundaries
        if (x > gameData.getDisplayWidth() || x < 0) { x = positionPart.getX(); }
        if (y > gameData.getDisplayHeight() || y < 0) { y = positionPart.getY(); }
        
        // get list of walls from GameData
        List<Rectangle> walls = gameData.getWalls();
        // check if entity will collide with any wall
        for (Rectangle wall : walls) {
            if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                // try changing only x
                y = positionPart.getY();
                if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                    // try changing only y
                    y = tempY;
                    x = positionPart.getX();
                    if (Intersector.overlaps(new Circle(x, y, radius), wall)) {
                        y = positionPart.getY();
                    }
                }
            }
        }
        
        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
