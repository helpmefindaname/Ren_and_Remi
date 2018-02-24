package at.ac.tuwien.ims.sf5.helper;

/**
 * @Author Benedikt Fuchs
 * <p>
 * a standard class for 2D points
 * immutable
 */
public class Vector2D {

    private final float x;
    private final float y;

    /**
     * creates a vector2D with specific coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * the x coordinate
     * @return the x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * the y coordinate
     * @return the y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * substract an vector from this vector
     * @param point the wector to substract
     * @return a new vector containing the result of the substraction
     */
    public Vector2D sub(Vector2D point) {
        return new Vector2D(x - point.x, y - point.y);
    }

    /**
     * calculates the squared distance
     * @return the squared distance
     */
    public float distSqr() {
        return x * x + y * y;
    }

    /**
     * adds another vector to this vector
     * @param point the other vector to add
     * @return the result of the addition
     */
    public Vector2D add(Vector2D point) {
        return new Vector2D(x + point.x, y + point.y);
    }

    /**
     * scales this vector by a factor s
     * @param s the factor
     * @return a new vector scaled by the factor
     */
    public Vector2D scale(float s) {
        return new Vector2D(x * s, y * s);
    }

    /**
     * sets a specific y coordinate
     * @param y the y coordinate
     * @return a new vector with the new y coordinate
     */
    public Vector2D withY(float y) {
        return new Vector2D(this.x, y);
    }

    /**
     * sets a specific x coordinate
     * @param x the x coordinate
     * @return a new vector with the new x coordinate
     */
    public Vector2D withX(float x) {
        return new Vector2D(x, this.y);
    }

    /**
     * calculates the dotproduct of this vector and another vector
     * @param v the other vector
     * @return the dot product
     */
    public float dot(Vector2D v) {
        return x * v.x + y * v.y;
    }

    /**
     * returns the dist of this vector
     * @return the dist of this vector
     */
    public float dist() {
        return (float) Math.sqrt(distSqr());
    }

    /**
     * returns a normalisation of this vector
     * @return a normalisation of this vector
     */
    public Vector2D normalize() {
        return this.scale(1 / dist());
    }

    /**
     * reflects the vector by a direction
     * @param direction the direction, expected to be normalized
     * @return a reflected vector
     */
    public Vector2D reflect(Vector2D direction) {
        return this.sub(direction.scale(2 * direction.dot(this)));
    }

    /**
     * performs a 2d cross action, being equivalent to rotiation by 90°
     * @return a rotated vector
     */
    public Vector2D cross() {
        return new Vector2D(-y, x);
    }

    /**
     * calculates the angle of this vector
     * @return the angle
     */
    public float angle() {
        return (float) ((Math.atan2(y, x) - Math.PI / 2) * 180d / Math.PI);
    }
}
