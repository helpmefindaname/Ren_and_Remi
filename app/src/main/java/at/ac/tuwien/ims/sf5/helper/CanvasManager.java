package at.ac.tuwien.ims.sf5.helper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;

/**
 * @Author Benedikt Fuchs
 * this class is an canvas wrapper.
 * this should make it easier to implement dpi /screen percentages
 */
public class CanvasManager {

    private Canvas canvas;
    private DisplayMetrics metrics;

    private float manipulateX(float x) {
        return x / 1024.0f * metrics.widthPixels;
    }

    private float manipulateY(float y) {
        return metrics.heightPixels - y;
    }

    private RectF manipulateRectF(RectF r) {
        return new RectF(
                manipulateX(r.left),
                manipulateY(r.top),
                manipulateX(r.right),
                manipulateY(r.bottom)
        );
    }

    /**
     * sets the canvas to draw on
     * @param canvas
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        metrics = Resources.getSystem().getDisplayMetrics();
    }


    /**
     * draws a bitmap which is loaded with a specific tag
     * @param tag the tag of the bitmap
     * @param rect the position of the rectangle
     * @param paint the paint to draw with
     */
    public void drawBitmap(String tag, RectF rect, Paint paint) {
        canvas.drawBitmap(
                GameResourceManager.getManager().getBitmap(tag),
                null,
                manipulateRectF(rect),
                paint);
    }

    /**
     * draws a part a bitmap which is loaded with a specific tag
     * @param tag the tag of the bitmap
     * @param rect the position of the rectangle
     * @param src the position of the part in the image
     * @param paint the paint to draw with
     */
    public void drawBitmap(String tag, RectF rect, Rect src, Paint paint) {
        canvas.drawBitmap(
                GameResourceManager.getManager().getBitmap(tag),
                src,
                manipulateRectF(rect),
                paint);
    }

    /**
     * clears the canvas with a specific color
     * @param backGroundColor the color to clear with.
     */
    public void drawColor(int backGroundColor) {
        canvas.drawColor(backGroundColor);
    }

    /**
     * draws a rectangle with a specific paint
     * we require left<right and bottom<top
     * @param left the left xCoordinate
     * @param top the top yCoordinate
     * @param right the right xCoordinate
     * @param bottom the bottom yCoordinate
     * @param paint the paint to draw with
     */
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(
                manipulateX(left),
                manipulateY(top),
                manipulateX(right),
                manipulateY(bottom),
                paint);
    }

    /**
     * saves the canvas
     */
    public int save() {
        return canvas.save();
    }

    /**
     * restores the canves on it's last saving point
     */
    public void restore() {
        canvas.restore();
    }

    /**
     * rotates everything drawn after
     * @param degree the degrees to rotate
     * @param rx the xPosition to rotate
     * @param ry the yPosition to rotate
     */
    public void rotate(float degree, float rx, float ry) {
        canvas.rotate(degree, manipulateX(rx), manipulateY(ry));
    }

    /**
     * draws an oval
     * we require left<right and bottom<top
     * @param left the left xCoordinate
     * @param top the top yCoordinate
     * @param right the right xCoordinate
     * @param bottom the bottom yCoordinate
     * @param paint the paint to draw with
     */
    public void drawOval(float left, float top, float right, float bottom, Paint paint) {
        canvas.drawOval(
                manipulateX(left),
                manipulateY(top),
                manipulateX(right),
                manipulateY(bottom),
                paint);
    }

    /**
     * manipulates a vector2D to get into a display point
     * @param vector2D the vector to manipulate
     * @return a new manipulated vector which represents a display point.
     */
    public Vector2D manipulateVec(Vector2D vector2D) {
        return new Vector2D(manipulateX(vector2D.getX()), manipulateY(vector2D.getY()));
    }
}
