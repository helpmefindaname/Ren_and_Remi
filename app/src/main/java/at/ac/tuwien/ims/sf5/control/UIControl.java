package at.ac.tuwien.ims.sf5.control;

import at.ac.tuwien.ims.sf5.Gameactivity;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.RoboAlien;

import static at.ac.tuwien.ims.sf5.control.AIControl.FUEL_PER_ROUND;
import static at.ac.tuwien.ims.sf5.control.AIControl.MOVEMENT_PER_SECOND;
import static at.ac.tuwien.ims.sf5.control.AIControl.ROT_PER_SECOND;
import static at.ac.tuwien.ims.sf5.control.AIControl.STRENGTH_PER_SECOND;

/**
 * @Author Benedikt Fuchs
 * the user controls the roboalien per userInterface
 * forwards the values of the gameactivity to the respective player.
 */

public class UIControl implements IControl {

    private RoboAlien player;
    private final Gameactivity gameactivity;
    private float fuel;

    /**
     * creates an UIControl for a specific gameactivity, the player won't be set here.
     *
     * @param gameactivity the gameactivity owning this AIControl
     */
    public UIControl(Gameactivity gameactivity) {
        this.gameactivity = gameactivity;
    }

    @Override
    public void setup(RoboAlien player) {
        this.player = player;
    }

    @Override
    public void init(GameData gameData) {
        gameactivity.setUpPlayerUi(player);
        fuel = FUEL_PER_ROUND;
    }

    @Override
    public void update(GameData gameData, long frameTime) {
        float seconds = frameTime * 0.001f;

        player.setShootDirection(handleDirection(ROT_PER_SECOND * seconds));
        player.setShootStrength(handleStrength(STRENGTH_PER_SECOND * seconds));
        player.setXPosition(handleMovement(MOVEMENT_PER_SECOND * seconds));
        player.setWeapon(gameactivity.getSelectedWeaponId());
        if (gameactivity.isShoot()) {
            player.shoot();
            gameactivity.setUpAiUi(player);
        }
    }

    private float handleMovement(float v) {
        float f = player.getXPosition();

        if (v > fuel) {
            v = fuel;
        }

        if (gameactivity.isMoveLeft()) {
            f -= v;
            fuel -= v;
        }

        if (gameactivity.isMoveRight()) {
            f += v;
            fuel -= v;
        }

        if (f < 10.0f) {
            f = 10.0f;
        } else if (f > 1000.0f) {
            f = 1000.0f;
        }

        return f;
    }

    private float handleStrength(float s) {
        float f = player.getShootStrength();

        if (gameactivity.isDmgDown()) {
            f -= s;
        }

        if (gameactivity.isDmgUp()) {
            f += s;
        }

        if (f < 10.0f) {
            f = 10.0f;
        } else if (f > 100.0f) {
            f = 100.0f;
        }

        return f;
    }

    private float handleDirection(float d) {
        float f = player.getShootDirection();

        if (gameactivity.isRotateLeft()) {
            f -= d;
        }

        if (gameactivity.isRotateRight()) {
            f += d;
        }

        if (f < -90.0f) {
            f = -90.0f;
        } else if (f > 90.0f) {
            f = 90.0f;
        }

        return f;
    }
}
