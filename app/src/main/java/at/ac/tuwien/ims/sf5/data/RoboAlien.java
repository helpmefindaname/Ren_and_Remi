package at.ac.tuwien.ims.sf5.data;

import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Collections;
import java.util.List;

import at.ac.tuwien.ims.sf5.collision.Circle;
import at.ac.tuwien.ims.sf5.collision.ICollisionBox;
import at.ac.tuwien.ims.sf5.control.IControl;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * This implements the basic player
 * Dependent on IControl it is either an AI or a real player
 */
public class RoboAlien implements IEntity {

    private IControl control;
    private boolean shooting;

    private float shootStrength = 40;
    private float shootDirection;
    private int weapon;

    private int[] weaponAmount;
    private IWeapon[] weapons;
    private Vector2D position;
    private float radius = 40;

    private int lives = 10;

    private IWeapon shootingWeapon;

    private final String name;
    private int score;

    /**
     * creates the roboalien with a specific name
     * @param name the name of the alien
     */
    public RoboAlien(String name) {
        this.name = name;
        weaponAmount = new int[]{1000, 3};
        weapons = new IWeapon[]{new BasisWeapon(this), new MultiWeapon(this)};
        position = new Vector2D(100, 0);
    }

    /**
     * the control of the roboalien (control.getPlayer() should return this)
     * @return the control of the roboalien
     */
    public IControl getControl() {
        return control;
    }

    public void setControl(IControl control) {
        this.control = control;
    }

    /**
     * is the roboalien in the shoot state
     * @return true if the roboalien is shooting at the moment, false if not
     */
    public boolean isShooting() {
        return shooting;
    }

    /**
     * checks if the weapon can be used to shoot
     * @param weaponId the id of the weapon
     * @return true if there is still amo left for that weapon.
     */
    public boolean hasAmo(int weaponId) {
        return weaponAmount[weaponId] > 0;
    }

    /**
     * starts a shoot with the selected weapon
     * if id is -1, it finishes directly
     */
    public void shoot() {
        this.shooting = true;
        if (weapon == -1) {
            return;
        }

        if (weaponAmount[weapon] > 0) {
            weaponAmount[weapon]--;
        } else {
            weapon = -1;
        }

        if (weapon == -1) {
            shootingWeapon = null;
        } else {
            shootingWeapon = weapons[weapon];
            shootingWeapon.startShooting(position, shootDirection, shootStrength);
        }
    }

    /**
     * the strength to soot with
     * @return the strength to soot with
     */
    public float getShootStrength() {
        return shootStrength;
    }

    public void setShootStrength(float shootStrength) {
        this.shootStrength = shootStrength;
    }

    /**
     * the direction to shoot at
     * @return the direction to shoot at
     */
    public float getShootDirection() {
        return shootDirection;
    }

    public void setShootDirection(float shootDirection) {
        this.shootDirection = shootDirection;
    }


    /**
     * the weaponid to shoot with
     * @param weapon the weaponid to shoot with
     */
    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getWeapon() {
        return weapon;
    }

    @Override
    public void update(GameData gameData, long frameTime) {

        float aimY = gameData.getHeight(position.getX()) + 100;

        if (aimY >= position.getY()) {
            position = position.withY(aimY);
        } else {
            float newY = position.getY() - 49.81f * 0.001f * frameTime;
            if (newY < aimY) {
                newY = aimY;
            }
            position = position.withY(newY);
        }
    }

    @Override
    public void draw(CanvasManager canvas, long totalTime, long deltaTime) {
        float rx = position.getX();
        float ry = position.getY();

        Paint paint = new Paint();

        canvas.save();
        canvas.rotate(this.shootDirection, rx, ry);
        canvas.drawBitmap("weapon_" + name, new RectF(rx - 12, ry + this.shootStrength + 28, rx + 12, ry + 28), paint);
        canvas.restore();

        canvas.drawBitmap("alien_" + name, new RectF(rx - radius, ry + radius, rx + radius, ry - radius), paint);
    }

    /**
     * sets the xPosition of the roboalien, the y position is calculated automatically.
     * @param xPosition
     */
    public void setXPosition(float xPosition) {
        position = position.withX(xPosition);
    }

    /**
     * stops the shooting animation
     */
    public void stopShooting() {
        this.shooting = false;
    }

    @Override
    public boolean isDead() {
        return lives <= 0;
    }

    @Override
    public boolean isCollideAbleWith(Class<? extends IEntity> type) {
        return false;
    }

    @Override
    public ICollisionBox getCollisionBox() {
        return new Circle(position, radius);
    }

    @Override
    public List<IEntity> entitiesToSpawn() {
        return Collections.emptyList();
    }

    /**
     * the name of the alien
     * @return the name of the alien
     */
    public String getName() {
        return name;
    }

    /**
     * the weapon of the alien as object
     * @return the weapon of the alien as object
     */
    public IWeapon getWeaponObject() {
        return shootingWeapon;
    }

    /**
     * damages the alien
     * @param hp the hp it looses
     */
    public void damage(int hp) {
        lives -= hp;
    }

    @Override
    public void onCollisionWith(IEntity entity, GameData gameData) {

    }

    /**
     * the score the player achieved
     * @return the score the player achieved
     */
    public int getScore() {
        return score;
    }

    /**
     * adds some points to the score
     * @param add the amount of points to add
     */
    public void addPoints(int add) {
        score += add;
    }

    /**
     * the hp of the alien
     * @return the hp of the alien
     */
    public int getHp() {
        return lives;
    }

    /**
     * the xPosition of the alien
     * @return the xPosition of the alien
     */
    public float getXPosition() {
        return position.getX();
    }
}
