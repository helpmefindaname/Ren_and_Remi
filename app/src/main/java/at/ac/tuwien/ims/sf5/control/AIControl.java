package at.ac.tuwien.ims.sf5.control;

import android.util.Log;

import at.ac.tuwien.ims.sf5.Gameactivity;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.RoboAlien;

/**
 * @Author Benedikt Fuchs
 * the computer controls the roboalien.
 * This AI shoots at a random direction with an random strength and moves to random places.
 * It always selects the best weapon possible.
 */

public class AIControl implements IControl {

    public static final float ROT_PER_SECOND = 24.0f;
    public static final float STRENGTH_PER_SECOND = 30.0f;
    public static final float MOVEMENT_PER_SECOND = 30.0f;
    public static final float FUEL_PER_ROUND = 60.0f;

    private static final float EPSILON = 0.0001f;

    private RoboAlien player;

    private float targetAim;
    private float targetStrength;
    private int targetWeaponId;
    private float targetX;

    private final Gameactivity gameactivity;

    /**
     * creates an AIControl for a specific gameactivity, the player won't be set here.
     * @param gameactivity the gameactivity owning this AIControl
     */
    public AIControl(Gameactivity gameactivity) {
        this.gameactivity = gameactivity;
    }

    @Override
    public void setup(RoboAlien player) {
        this.player = player;
    }

    @Override
    public void init(GameData gameData) {
        targetAim = 90.0F * ((float) Math.random() - 1.0f);
        targetStrength = 100.0F * (float) (Math.random() * 0.25 + 0.75);
        targetWeaponId = player.hasAmo(1) ? 1 : 0;
        targetX = player.getXPosition() + ((float) Math.random() - 0.5f) * 2.0f * FUEL_PER_ROUND;

        gameactivity.setUpAiUi(player);

        Log.i("AIControl", "init AI");
    }

    @Override
    public void update(GameData gameData, long frameTime) {
        float actualAim = player.getShootDirection();
        float actualStrength = player.getShootStrength();
        float actualX = player.getXPosition();
        player.setWeapon(targetWeaponId);

        if (Math.abs(actualAim - targetAim) < EPSILON &&
                Math.abs(actualStrength - targetStrength) < EPSILON &&
                Math.abs(actualX - targetX) < EPSILON) {
            player.shoot();
        }

        float seconds = frameTime * 0.001f;
        player.setShootDirection(closeUp(targetAim, actualAim, ROT_PER_SECOND * seconds));
        player.setShootStrength(closeUp(targetStrength, actualStrength, STRENGTH_PER_SECOND * seconds));
        player.setXPosition(closeUp(targetX, actualX, MOVEMENT_PER_SECOND * seconds));
    }

    private float closeUp(float target, float actual, float delta) {
        float sign = Math.signum(target - actual);
        float magnitude = Math.abs(target - actual);

        if (magnitude < delta) {
            return target;
        } else {
            return actual + sign * delta;
        }
    }
}
