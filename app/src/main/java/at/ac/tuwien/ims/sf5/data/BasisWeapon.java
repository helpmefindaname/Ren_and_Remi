package at.ac.tuwien.ims.sf5.data;

import at.ac.tuwien.ims.sf5.helper.GameResourceManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * a basic weapon spawning only one shoot
 */

public class BasisWeapon implements IWeapon {

    private boolean spawning;
    private ShootEntity entityToSpawn;
    private RoboAlien owner;

    /**
     * creates a simple weapon for a specific roboalien
     * @param owner the roboalien that owns this weapon
     */
    public BasisWeapon(RoboAlien owner) {
        this.owner = owner;
    }

    @Override
    public boolean isSpawning() {
        return spawning;
    }

    @Override
    public void spawnObjects(GameData gameData, long frameTime) {
        if (spawning) {
            gameData.addEntity(entityToSpawn);
            spawning = false;
            entityToSpawn = null;
            GameResourceManager.getManager().playSound("shoot");
        }
    }

    @Override
    public void startShooting(Vector2D position, float shootDirection, float shootStrength) {
        spawning = true;
        entityToSpawn = new ShootEntity(owner, position, shootDirection, shootStrength * 1.5f);
    }
}
