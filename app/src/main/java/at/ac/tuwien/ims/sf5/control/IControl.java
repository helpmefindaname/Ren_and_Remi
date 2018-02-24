package at.ac.tuwien.ims.sf5.control;

import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.RoboAlien;

/**
 * @Author Benedikt Fuchs
 * <p>
 * this Interface should help making the RoboAlien act the same way,
 * when played by player or by computer
 */
public interface IControl {

    /**
     * initialize the control once before the game starts
     * @param player the player this control should control.
     */
    void setup(RoboAlien player);

    /**
     * initialize the control when its state is called
     * @param gameData the data about the game
     */
    void init(GameData gameData);

    /**
     * updates the control while its state is called every update-frame
     * @param gameData the data about the game
     * @param frameTime the time elapsed since the last update frame
     */
    void update(GameData gameData, long frameTime);
}
