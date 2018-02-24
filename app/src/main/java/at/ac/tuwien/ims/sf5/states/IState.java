package at.ac.tuwien.ims.sf5.states;

import at.ac.tuwien.ims.sf5.GameResult;
import at.ac.tuwien.ims.sf5.GameSurfaceView;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;

/**
 * @Author Benedikt Fuchs
 * Basic Interface for States of the StateMachine
 */
public interface IState {

    /**
     * initializes the state when switched to it
     * @param view the view of the game.
     */
    void setup(GameSurfaceView view);

    /**
     * updates the state
     * @param gameData the data about the game
     * @param frameTime the time since the last update
     * @return true if this state is finished, getNextState will define the next one
     */
    boolean update(GameData gameData, long frameTime);

    /**
     * draws the game
     * @param gameData the game data
     * @param canvas the canvas to draw to
     * @param deltaTime the time since the last draw
     * @param totalTime the time since the game started
     */
    void render(GameData gameData, CanvasManager canvas, long deltaTime, long totalTime);

    /**
     * the following state
     * null if the game should end.
     * @return the following state
     */
    IState getNextState();

    void setNextState(IState nextState);

    /**
     * the result of the game
     * only relevant if the state ends with getNextState()==null
     * default should be GameState.Cancel
     * @return the result of the game
     */
    GameResult getGameResult();
}
