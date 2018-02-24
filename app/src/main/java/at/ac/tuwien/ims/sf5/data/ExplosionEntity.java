package at.ac.tuwien.ims.sf5.data;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.ims.sf5.collision.Circle;
import at.ac.tuwien.ims.sf5.collision.ICollisionBox;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * runs an explosion animation and damages entities around
 * @Author Benedikt Fuchs
 */
public class ExplosionEntity implements IEntity {

    private final float SECONDS_ALIVE = 1.2f;
    private final int FRAME_COUNT = 5;

    //double the actual size, I don't know why
    private final int TILE_WIDTH = 256;
    private final int TILE_HEIGHT = 256;

    private RoboAlien owner;
    private Vector2D position;
    private float radius;
    private int damage;
    private float timeAlive;

    private boolean isDmgFrame;

    /**
     * creates an expolsion entity at specific positon, radius and damage.
     * @param owner the roboalien who created this explosion
     * @param position the posiontion to spawn
     * @param radius the size
     * @param damage the damage to imply
     */
    public ExplosionEntity(RoboAlien owner, Vector2D position, float radius, int damage) {
        this.owner = owner;
        this.position = position;
        this.radius = radius;
        this.damage = damage;
    }

    @Override
    public void update(GameData gameData, long frameTime) {
        float seconds = frameTime * 0.001f;
        float threshold = SECONDS_ALIVE * 0.8f;
        isDmgFrame = timeAlive < threshold && threshold <= timeAlive + seconds;
        timeAlive += seconds;

        if (isDmgFrame) {
            gameData.removeTerrain(position, radius * 0.65f);
        }
    }

    @Override
    public void draw(CanvasManager canvas, long totalTime, long deltaTime) {

        int actFrame = (int) ((FRAME_COUNT * timeAlive) / SECONDS_ALIVE);

        if (actFrame >= FRAME_COUNT) {
            actFrame = FRAME_COUNT - 1;
        }

        Paint p = new Paint();
        float x = position.getX();
        float y = position.getY();
        canvas.drawBitmap(
                "explosions",
                new RectF(x - radius, y + radius, x + radius, y - radius),
                new Rect(TILE_WIDTH * actFrame, 0, TILE_WIDTH * (1 + actFrame), TILE_HEIGHT),
                p);
    }

    @Override
    public boolean isDead() {
        return timeAlive >= SECONDS_ALIVE;
    }

    @Override
    public boolean isCollideAbleWith(Class<? extends IEntity> type) {
        return isDmgFrame && RoboAlien.class.isAssignableFrom(type);
    }

    @Override
    public void onCollisionWith(IEntity entity, GameData gameData) {
        RoboAlien alien = (RoboAlien) entity;
        alien.damage(this.damage);
        if (alien.getName().equals(owner.getName())) {
            owner.addPoints(-200);
        } else {
            owner.addPoints(1000);
        }
    }

    @Override
    public ICollisionBox getCollisionBox() {
        return new Circle(position, radius);
    }

    @Override
    public List<IEntity> entitiesToSpawn() {
        return new ArrayList<>(0);
    }
}
