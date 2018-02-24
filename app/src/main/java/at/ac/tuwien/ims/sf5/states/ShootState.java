package at.ac.tuwien.ims.sf5.states;

import android.graphics.Color;

import at.ac.tuwien.ims.sf5.GameResult;
import at.ac.tuwien.ims.sf5.GameSurfaceView;
import at.ac.tuwien.ims.sf5.data.ExplosionEntity;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.ShootEntity;
import at.ac.tuwien.ims.sf5.data.RoboAlien;
import at.ac.tuwien.ims.sf5.data.IWeapon;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;

/**
 * @Author Benedikt Fuchs
 * handles the time between shooting and explosions
 * this state ends when no shootentity
 * and no explosion entity exists
 * and the shootanimation of the weapon ended
 */
public class ShootState implements IState {

    private IState nextState;
    private final RoboAlien player;

    public ShootState(RoboAlien player) {
        this.player = player;
    }


    @Override
    public void setup(GameSurfaceView view) {

    }

    @Override
    public boolean update(GameData gameData, long frameTime) {
        IWeapon activeWeapon = player.getWeaponObject();
        if (activeWeapon != null) {
            if (activeWeapon.isSpawning()) {
                activeWeapon.spawnObjects(gameData, frameTime);
            } else {
                if (gameData.getEntities(ShootEntity.class).isEmpty() && gameData.getEntities(ExplosionEntity.class).isEmpty()) {
                    player.stopShooting();
                }
            }
        } else {
            player.stopShooting();
        }

        gameData.update(frameTime);

        return player.isShooting();
    }

    @Override
    public void render(GameData gameData, CanvasManager canvas, long deltaTime, long totalTime) {
        canvas.drawColor(Color.WHITE);
        gameData.render(canvas, deltaTime, totalTime);
    }

    @Override
    public IState getNextState() {
        return nextState;
    }

    @Override
    public void setNextState(IState nextState) {
        this.nextState = nextState;
    }

    @Override
    public GameResult getGameResult() {
        return null;
    }
}
