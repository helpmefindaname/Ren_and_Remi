package at.ac.tuwien.ims.sf5.helper;

import android.util.Log;

/**
 * @Author Benedikt Fuchs
 * <p>
 * Standard util functions to detect collision
 */
public final class CollisionHelper {

    /**
     * checks if two circles do touch
     * @param center1 the center of the first circle
     * @param radius1 the radius of the first circle
     * @param center2 the center of the first circle
     * @param radius2 the radius of the first circle
     * @return true of those circles touch, false if not
     */
    public static boolean doCirclesTouch(Vector2D center1, float radius1, Vector2D center2, float radius2) {
        return center1.sub(center2).distSqr() <= radius1 * radius1 + radius2 * radius2 + 2 * radius1 * radius2;
    }

    /**
     * checks if a line collides with a circle
     * @param start the start of the line
     * @param end the end of the line
     * @param center the center of the circle
     * @param radius the radius of the circle
     * @return true of the line collides with the circle
     */
    public static boolean collidesLineWithCircle(Vector2D start, Vector2D end, Vector2D center, float radius) {
        Vector2D d = end.sub(start);
        Vector2D f = start.sub(center);

        float a = d.distSqr();
        float b = 2 * f.dot(d);
        float c = f.distSqr() - radius * radius;

        float discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return false;
        }

        float discriminantSqr = (float) Math.sqrt(discriminant);

        float t1 = (-b - discriminantSqr) / (2 * a);
        float t2 = (-b + discriminantSqr) / (2 * a);
        Log.d("collision", "circle to line t1: " + t1 + "; t2: " + t2);

        return (t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1);
    }
}
