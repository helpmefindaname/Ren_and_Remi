package at.ac.tuwien.ims.sf5.data;

import java.util.Arrays;
import java.util.List;

import at.ac.tuwien.ims.sf5.helper.GameResourceManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * a weapon shooting multiple bullets
 */

public class MultiWeapon implements IWeapon {

    boolean spawning;
    List<ShootEntity> entitiesToSpawn;
    private RoboAlien owner;

    /**
     * initializes it with the respective owner
     * @param owner the owner of that weapon
     */
    public MultiWeapon(RoboAlien owner) {
        this.owner = owner;
    }

    @Override
    public boolean isSpawning() {
        return spawning;
    }

    @Override
    public void spawnObjects(GameData gameData, long frameTime) {
        if (spawning) {
            for (IEntity entityToSpawn : entitiesToSpawn) {
                gameData.addEntity(entityToSpawn);
            }
            spawning = false;
            entitiesToSpawn = null;
            GameResourceManager.getManager().playSound("shoot");
        }
    }

    @Override
    public void startShooting(Vector2D position, float shootDirection, float shootStrength) {
        spawning = true;
        entitiesToSpawn = Arrays.asList(
                new ShootEntity(owner, position, shootDirection + 10, shootStrength),
                new ShootEntity(owner, position, shootDirection - 10, shootStrength),
                new ShootEntity(owner, position, shootDirection, shootStrength)
        );
    }
}
