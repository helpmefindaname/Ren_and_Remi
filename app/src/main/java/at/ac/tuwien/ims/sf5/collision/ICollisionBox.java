package at.ac.tuwien.ims.sf5.collision;

/**
 * @Author Benedikt Fuchs
 * Interface for detecting collisions
 * call iCollisionBox1.collidesWith(iCollisionBox2) to check whether two boxes collide.
 */

public interface ICollisionBox {

    /**
     * checks whether two boxes collide
     * calls collidesWithLine(this) or collidesWithCircle(this) depending on type
     * @param collisionBox an other box
     * @return returns true if those boxes collide, false if not
     */
    boolean collidesWith(ICollisionBox collisionBox);

    /**
     * checks whether this box collides with a line
     * @param line a line to check collision with
     * @return returns true if this box and the line collide, false if not
     */
    boolean collidesWithLine(Line line);

    /**
     * checks whether this box collides with a circle
     * @param circle a circle to check collision with
     * @return returns true if this box and the circle collide, false if not
     */
    boolean collidesWithCircle(Circle circle);
}
