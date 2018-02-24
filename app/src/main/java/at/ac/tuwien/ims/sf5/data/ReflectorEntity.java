package at.ac.tuwien.ims.sf5.data;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Collections;
import java.util.List;

import at.ac.tuwien.ims.sf5.collision.ICollisionBox;
import at.ac.tuwien.ims.sf5.collision.Line;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * A reflector redirecting bullets on collision
 */
public class ReflectorEntity implements IEntity {

    private final Vector2D start, end;
    private int timeToShine;

    /**
     * create the entity on a specific line and a specific livespan
     * @param start the start of the line
     * @param end the end of the line
     * @param liveTime the number of rounds it lives.
     */
    public ReflectorEntity(Vector2D start, Vector2D end, int liveTime) {
        this.start = start;
        this.end = end;
        this.timeToShine = liveTime;
    }

    public void reduceTime() {
        timeToShine--;
    }

    @Override
    public void update(GameData gameData, long frameTime) {

    }

    @Override
    public void draw(CanvasManager canvas, long totalTime, long deltaTime) {

        Vector2D mStart = canvas.manipulateVec(start);
        Vector2D mEnd = canvas.manipulateVec(end);

        float length = mStart.sub(mEnd).dist();
        float angle = mStart.sub(mEnd).angle();
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.save();
        canvas.rotate(angle, start.getX(), start.getY());
        canvas.drawBitmap("reflector", new RectF(start.getX() - 3, start.getY() + length, start.getX() + 3, start.getY()), paint);
        canvas.restore();
    }

    @Override
    public boolean isDead() {
        return timeToShine <= 0;
    }

    @Override
    public boolean isCollideAbleWith(Class<? extends IEntity> type) {
        return ShootEntity.class.isAssignableFrom(type);
    }

    @Override
    public void onCollisionWith(IEntity entity, GameData gameData) {

    }

    @Override
    public ICollisionBox getCollisionBox() {
        return new Line(start, end);
    }

    @Override
    public List<IEntity> entitiesToSpawn() {
        return Collections.emptyList();
    }

    /**
     * returns the direction of the entity so that it can be reflected
     * @return a 2D unitvector containing the direction.
     */
    public Vector2D getDirection() {
        return end.sub(start).normalize().cross();
    }
}
