package at.ac.tuwien.ims.sf5.data;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.ims.sf5.collision.Circle;
import at.ac.tuwien.ims.sf5.collision.ICollisionBox;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.helper.GameResourceManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * The shoot entity
 */

public class ShootEntity implements IEntity {

    private Vector2D position;
    private Vector2D velocity;
    private boolean isDead;
    private int reflectCount = 0;

    private float radius;

    private List<IEntity> entitiesToSpawn;
    private RoboAlien owner;

    /**
     * creates a shoot entity depending on position direction strength and owner.
     *
     * @param owner
     * @param position
     * @param shootDirection
     * @param shootStrength
     */
    public ShootEntity(RoboAlien owner, Vector2D position, float shootDirection, float shootStrength) {
        this.owner = owner;

        velocity = new Vector2D((float) Math.sin(shootDirection / 180.0f * (float) Math.PI) * (shootStrength + 28),
                (float) Math.cos(shootDirection / 180.0f * (float) Math.PI) * (shootStrength + 28));

        this.position = position.add(velocity);
        float length = velocity.dist();
        velocity = velocity.scale((length * 0.75f + 100.0f) / length);

        radius = 10;

        isDead = false;
        entitiesToSpawn = new ArrayList<>(1);
    }

    @Override
    public boolean isDead() {
        return position.getX() < 0 || position.getX() > 1024 || isDead;
    }

    @Override
    public boolean isCollideAbleWith(Class<? extends IEntity> type) {
        return RoboAlien.class.isAssignableFrom(type);
    }

    @Override
    public void onCollisionWith(IEntity entity, GameData gameData) {
        if (entity instanceof RoboAlien) {
            explode();
        } else if (entity instanceof ReflectorEntity) {
            ReflectorEntity reflector = (ReflectorEntity) entity;
            if (reflectCount++ < 5) {
                velocity = velocity.reflect(reflector.getDirection());
                GameResourceManager.getManager().playSound("reflect");
            }
        }
    }

    @Override
    public ICollisionBox getCollisionBox() {
        return new Circle(position, radius);
    }

    @Override
    public void update(GameData gameData, long frameTime) {
        float friction = 0.99f;
        float seconds = frameTime * 0.001f;

        position = position.add(velocity.scale(seconds));

        if (position.getX() < 0) {
            position = position.withX(-position.getX());
            velocity = velocity.withX(-velocity.getX());
        }

        if (position.getX() > 1024) {
            position = position.withX(1024 + 1024 - position.getX());
            velocity = velocity.withX(-velocity.getX());
        }

        velocity = velocity.scale((float) Math.pow(friction, seconds));

        velocity = velocity.sub(new Vector2D(0, 49.81f * seconds));

        if (position.getY() > 900 && velocity.getY() > 0) {
            velocity = velocity.withY(0);
        }

        if (position.getY() < gameData.getHeight(position.getX())) {
            explode();
        }
    }

    private void explode() {
        if (!isDead) {
            entitiesToSpawn.add(new ExplosionEntity(owner, position, radius * 5, 2));
            GameResourceManager.getManager().playSound("explosion");
        }
        isDead = true;
    }

    @Override
    public void draw(CanvasManager canvas, long totalTime, long deltaTime) {

        float rx = position.getX();
        float ry = position.getY();

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        canvas.drawOval(rx - radius, ry + radius, rx + radius, ry - radius, paint);
    }

    @Override
    public List<IEntity> entitiesToSpawn() {
        return entitiesToSpawn;
    }
}
