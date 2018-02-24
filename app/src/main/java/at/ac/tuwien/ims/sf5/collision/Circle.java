package at.ac.tuwien.ims.sf5.collision;

import at.ac.tuwien.ims.sf5.helper.CollisionHelper;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * Immutable Object
 * Implements a 2D circle for collision detection
 * Consists of a radius and a center point.
 */

public class Circle implements ICollisionBox {

    private final float radius;
    private final Vector2D center;

    public Circle(Vector2D center, float radius) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public boolean collidesWith(ICollisionBox collisionBox) {
        return collisionBox.collidesWithCircle(this);
    }

    @Override
    public boolean collidesWithLine(Line line) {
        return CollisionHelper.collidesLineWithCircle(line.getStart(), line.getEnd(), center, radius);
    }

    @Override
    public boolean collidesWithCircle(Circle circle) {
        return CollisionHelper.doCirclesTouch(center, radius, circle.center, circle.radius);
    }

    /**
     * @return the radius of the circle
     */
    public float getRadius() {
        return radius;
    }

    /**
     * @return the center of the circle
     */
    public Vector2D getCenter() {
        return center;
    }
}
