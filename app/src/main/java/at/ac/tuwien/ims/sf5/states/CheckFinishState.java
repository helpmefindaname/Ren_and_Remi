package at.ac.tuwien.ims.sf5.states;

import android.graphics.Color;

import at.ac.tuwien.ims.sf5.GameResult;
import at.ac.tuwien.ims.sf5.GameSurfaceView;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;

/**
 * @Author Benedikt Fuchs
 * checks whether the game should end and ends it properly
 */
public class CheckFinishState implements IState {

    private IState nextState;
    private GameResult result;

    @Override
    public void setup(GameSurfaceView view) {

    }

    @Override
    public boolean update(GameData gameData, long frameTime) {

        if (gameData.getPlayer1().isDead() || gameData.getPlayer2().isDead()) {
            nextState = null;

            result = gameData.getPlayer1().isDead() && gameData.getPlayer2().isDead() ? GameResult.draw :
                    gameData.getPlayer1().isDead() ? GameResult.loose :
                            GameResult.win;
        }

        return false;
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
        return result;
    }
}
