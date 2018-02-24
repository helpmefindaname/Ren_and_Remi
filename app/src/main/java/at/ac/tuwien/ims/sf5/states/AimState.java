package at.ac.tuwien.ims.sf5.states;

import android.graphics.Color;

import at.ac.tuwien.ims.sf5.GameResult;
import at.ac.tuwien.ims.sf5.GameSurfaceView;
import at.ac.tuwien.ims.sf5.control.IControl;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.RoboAlien;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;

/**
 * @Author Benedikt Fuchs
 * Selects aimDirection and strength and then shoots. for the respective RoboAlien.
 */
public class AimState implements IState {

    private RoboAlien player;
    private boolean firstFrame;
    private IControl control;

    private IState nextState;

    public AimState(RoboAlien player) {
        this.player = player;
    }

    @Override
    public void setup(GameSurfaceView view) {
        control = player.getControl();
        control.setup(player);
        firstFrame = true;
    }

    @Override
    public boolean update(GameData gameData, long frameTime) {

        if (firstFrame) {
            firstFrame = false;
            control.init(gameData);
        }

        control.update(gameData, frameTime);
        gameData.update(frameTime);

        return !player.isShooting();
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
