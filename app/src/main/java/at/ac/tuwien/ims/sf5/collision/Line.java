package at.ac.tuwien.ims.sf5.collision;

import at.ac.tuwien.ims.sf5.helper.CollisionHelper;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 */

public class Line implements ICollisionBox {

    private final Vector2D start, end;

    public Line(Vector2D start, Vector2D end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean collidesWith(ICollisionBox collisionBox) {
        return collisionBox.collidesWithLine(this);
    }

    @Override
    public boolean collidesWithLine(Line line) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean collidesWithCircle(Circle circle) {
        return CollisionHelper.collidesLineWithCircle(start, end, circle.getCenter(), circle.getRadius());
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
