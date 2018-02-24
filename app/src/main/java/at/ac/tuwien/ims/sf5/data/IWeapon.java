package at.ac.tuwien.ims.sf5.data;

import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * Interface for any Weapon
 */

public interface IWeapon {

    /**
     * checks if the weapon needs more time to spawn objects
     * @return true if this weapon requires more frames to spawn, false if it is finished spawning.
     */
    boolean isSpawning();

    /**
     * called in a spawn sequence every update frame,
     * possibly waits till it finished spawning multiple elements
     * @param gameData the data about the game
     * @param frameTime the time elapsed since the last update
     */
    void spawnObjects(GameData gameData, long frameTime);

    /**
     * initializes a shoot sequence
     * @param position the position where to shoot from
     * @param shootDirection the direction to shoot in
     * @param shootStrength the strength to shoot
     */
    void startShooting(Vector2D position, float shootDirection, float shootStrength);
}
