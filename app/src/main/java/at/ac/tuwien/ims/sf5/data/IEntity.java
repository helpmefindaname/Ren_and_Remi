package at.ac.tuwien.ims.sf5.data;

import java.util.List;

import at.ac.tuwien.ims.sf5.collision.ICollisionBox;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;

/**
 * @Author Benedikt Fuchs
 * Interface for any Entity
 */

public interface IEntity {

    /**
     * updates the entity logic
     * @param gameData the data about the game
     * @param frameTime the time since the last update
     */
    void update(GameData gameData, long frameTime);

    /**
     * draws the entity
     * @param canvas the canvas to draw to
     * @param totalTime the total time since the game started
     * @param deltaTime the time since the last draw
     */
    void draw(CanvasManager canvas, long totalTime, long deltaTime);

    /**
     * returns if the entity is dead and can be removed
     * @return true if the entity is dead and can be removed false otherwise
     */
    boolean isDead();

    /**
     * can this entity collide at all with another one
     * @param type the type of the other entity
     * @return true if those entitys may collide and need further checking
     */
    boolean isCollideAbleWith(Class<? extends IEntity> type);

    /**
     * handles the collision that happend
     * @param entity the entity it collided with
     * @param gameData the data about the game.
     */
    void onCollisionWith(IEntity entity, GameData gameData);

    /**
     * returns the collision box of the entity, null if not availaible
     * @return either null, a line or a circle
     */
    ICollisionBox getCollisionBox();

    /**
     * returns the elements it wants gamedata to spawn, most of the times it's an empty list
     * @return the elements it wants gamedata to spawn
     */
    List<IEntity> entitiesToSpawn();
}
