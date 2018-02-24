package at.ac.tuwien.ims.sf5;

import at.ac.tuwien.ims.sf5.control.AIControl;
import at.ac.tuwien.ims.sf5.control.UIControl;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.RoboAlien;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.states.AimState;
import at.ac.tuwien.ims.sf5.states.CheckFinishState;
import at.ac.tuwien.ims.sf5.states.IState;
import at.ac.tuwien.ims.sf5.states.ReflectorSpawnState;
import at.ac.tuwien.ims.sf5.states.ShootState;

/**
 * @Author Benedikt Fuchs
 * the stateMachine runs different states depending on what should happen at the moment.
 * for example there is an "Aimstate" where the 'Control' aims and then shoots.
 */
public class StateMachine {

    private IState actualState;
    private GameData gameData;
    private GameSurfaceView view;
    private GameResult gameResult;

    /**
     * setups the Statemachine with activity and view
     * @param gameSurfaceView the gameView
     * @param gameactivity the gameActivity
     */
    public void setup(GameSurfaceView gameSurfaceView, Gameactivity gameactivity) {
        this.view = gameSurfaceView;
        this.gameData = new GameData(gameactivity.getLevel());
        RoboAlien player1 = new RoboAlien("ren");
        RoboAlien player2 = new RoboAlien("remi");

        player1.setXPosition((int) (Math.random() * 323));
        player2.setXPosition(760 + (int) (Math.random() * 123));

        if (gameactivity.isPlayer1Ai()) {
            player1.setControl(new AIControl(gameactivity));
        } else {
            player1.setControl(new UIControl(gameactivity));
        }

        if (gameactivity.isPlayer2Ai()) {
            player2.setControl(new AIControl(gameactivity));
        } else {
            player2.setControl(new UIControl(gameactivity));
        }

        gameData.setPlayer1(player1);
        gameData.setPlayer2(player2);

        actualState = new AimState(player1);
        IState actualShootState = new ShootState(player1);
        IState checkFinishState = new CheckFinishState();

        IState nextAimState = new AimState(player2);
        IState nextShootState = new ShootState(player2);
        IState nextCheckFinishState = new CheckFinishState();

        actualState.setNextState(actualShootState);
        actualShootState.setNextState(checkFinishState);
        checkFinishState.setNextState(nextAimState);

        nextAimState.setNextState(nextShootState);
        nextShootState.setNextState(nextCheckFinishState);
        nextCheckFinishState.setNextState(actualState);

        if (gameactivity.isSpawnReflector()) {
            ReflectorSpawnState reflectorSpawnState = new ReflectorSpawnState();
            reflectorSpawnState.setNextState(actualShootState.getNextState());
            actualShootState.setNextState(reflectorSpawnState);

            ReflectorSpawnState nextReflectorSpawnState = new ReflectorSpawnState();
            nextReflectorSpawnState.setNextState(nextShootState.getNextState());
            nextShootState.setNextState(nextReflectorSpawnState);
        }

        if (Math.random() < .05f) {
            actualState = nextAimState;
        }

        actualState.setup(view);
    }

    /**
     * updates the current state
     * @param deltaTime the time since the last update
     * @return true if the loop should finish
     */
    public boolean update(long deltaTime) {
        if (actualState == null) {
            return true;
        }

        if (!actualState.update(gameData, deltaTime)) {
            GameResult result = actualState.getGameResult();
            actualState = actualState.getNextState();
            if (actualState == null) {
                this.gameResult = result;
                return true;
            }
            actualState.setup(view);
        }

        return false;
    }

    /**
     * draws the current state to a canvas
     * @param canvas the canvas to draw to
     * @param totalTime the time since the game started
     * @param deltaTime the time since the last draw
     */
    public void draw(CanvasManager canvas, long totalTime, long deltaTime) {
        if (actualState == null) {
            return;
        }
        try {
            actualState.render(gameData, canvas, totalTime, deltaTime);
        } catch (Exception ignored) {
        }
    }

    /**
     * returns the score of the first player
     * @return the score of the first player
     */
    public int getScore() {
        return gameData.getPlayer1().getScore();
    }

    /**
     * returns the result of the game
     * @return the result of the game
     */
    public GameResult getGameResult() {
        return gameResult;
    }
}
