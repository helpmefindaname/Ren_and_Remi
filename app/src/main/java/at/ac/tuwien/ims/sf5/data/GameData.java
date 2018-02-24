package at.ac.tuwien.ims.sf5.data;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * this class contains everything data related.
 * atm. It contains every possible entity on the screen and the bottom field.
 */
public class GameData {

    private static final int FIELD_DENSITY = 1024;
    private static final int GAUSS_FILTER_SIZE = 25;

    private List<IEntity> entities;
    private int[] field;
    private RoboAlien player1, player2;

    /**
     * creates the gamedata including a random field depending on the level
     * @param level the level of the game.
     */
    public GameData(int level) {
        entities = new ArrayList<>();
        field = new int[FIELD_DENSITY];
        for (int i = 0; i < FIELD_DENSITY; i++) {
            field[i] = (int) (Math.random() * 500);
            if (level == 2 && ((i > 300 && i < 400) || (i > 700 && i < 760))) {
                field[i] += 200;
            }
            if (level == 1 && ((i > 300 && i < 320) || (i > 700 && i < 720))) {
                field[i] += 400;
            }
        }
        float[] densedField = new float[FIELD_DENSITY];

        float[] gaussFilter = new float[GAUSS_FILTER_SIZE];
        float filterSum = 0.0f;
        for (int i = 0; i < GAUSS_FILTER_SIZE; i++) {
            float d = (i - GAUSS_FILTER_SIZE * 0.5f) * (i - GAUSS_FILTER_SIZE * 0.5f);
            gaussFilter[i] = (float) Math.exp(-4.0f * d / (GAUSS_FILTER_SIZE * GAUSS_FILTER_SIZE));
            filterSum += gaussFilter[i];
        }

        for (int i = 0; i < GAUSS_FILTER_SIZE; i++) {
            gaussFilter[i] /= filterSum;
        }

        for (int i = 0; i < FIELD_DENSITY; i++) {
            densedField[i] = 0;
            for (int j = 0; j < GAUSS_FILTER_SIZE; j++) {
                int l = i + j - GAUSS_FILTER_SIZE / 2;
                if (l >= 0 && l < FIELD_DENSITY) {
                    densedField[i] += gaussFilter[j] * field[l];
                }
            }
        }

        for (int i = 0; i < FIELD_DENSITY; i++) {
            field[i] = (int) densedField[i];
        }

    }

    /**
     * add an entity to the game
     * @param entity the entity to add
     */
    public void addEntity(IEntity entity) {
        entities.add(entity);
    }

    /**
     * updates all entities and handles their collisions
     * @param frameTime the time since the last update
     */
    public void update(long frameTime) {
        for (IEntity entity : entities) {
            entity.update(this, frameTime);
        }

        for (IEntity entity1 : entities) {
            for (IEntity entity2 : entities) {
                if (!entity1.isDead()
                        && !entity2.isDead()
                        && entity1.isCollideAbleWith(entity2.getClass())
                        && entity1.getCollisionBox().collidesWith(entity2.getCollisionBox()
                )) {
                    entity1.onCollisionWith(entity2, this);
                    entity2.onCollisionWith(entity1, this);
                }
            }
        }

        for (int i = entities.size() - 1; i >= 0; i--) {
            IEntity actualEntity = entities.get(i);
            entities.addAll(actualEntity.entitiesToSpawn());
            if (actualEntity.isDead()) {
                entities.remove(i);
            }
        }
    }

    /**
     * renders all entities and the filed in the background
     * @param canvas the canvas to draw to
     * @param deltaTime the time since the last draw
     * @param totalTime the time since the game started.
     */
    public void render(final CanvasManager canvas, final long deltaTime, final long totalTime) {
        Paint p = new Paint();
        p.setColor(Color.GRAY);

        float d2pos = FIELD_DENSITY / 1024.0f;

        for (int i = 0; i < FIELD_DENSITY; i++) {
            canvas.drawRect(i * d2pos, field[i], (i + 1) * d2pos, 0, p);
        }

        for (IEntity entity : entities) {
            entity.draw(canvas, deltaTime, totalTime);
        }
    }

    /**
     * returns the height of a field depending on it's x position
     * @param xPosition the xPosition
     * @return the height of the field
     */
    public float getHeight(float xPosition) {
        if (xPosition < 0 || xPosition >= 1024) {
            return 1024;
        }
        return field[(int) (FIELD_DENSITY * xPosition / 1024.0f)];
    }

    /**
     * returns all entities that can be assigned to a specific class
     * @param c the class the entities should be assigned to
     * @return all entities that cann be assigned from the class
     */
    public List<IEntity> getEntities(Class<?> c) {

        List<IEntity> result = new ArrayList<>();

        for (IEntity e : entities) {
            if (c.isAssignableFrom(e.getClass())) {
                result.add(e);
            }
        }

        return result;
    }

    /**
     * removes all terrain containing a circle
     * @param position the middle of the circle
     * @param radius the radius of the circle
     */
    public void removeTerrain(Vector2D position, float radius) {
        float d2pos = FIELD_DENSITY / 1024.0f;

        for (int i = 0; i < FIELD_DENSITY; i++) {

            float start = i * d2pos;
            float end = (i + 1) * d2pos;
            float height = field[i];

            if (Math.abs(position.getX() - start) <= radius) {

                float d = (float) Math.sqrt(radius * radius - (position.getX() - start) * (position.getX() - start));
                float top = position.getY() + d;
                float bottom = position.getY() - d;

                if (height > bottom) {
                    if (height > top) {
                        field[i] = (int) (height - d * 2);
                    } else {
                        field[i] = (int) bottom;
                    }
                }
            }
        }
    }

    public void setPlayer1(RoboAlien player1) {
        this.player1 = player1;
        addEntity(player1);
    }

    /**
     * the first player
     * @return the first player
     */
    public RoboAlien getPlayer1() {
        return player1;
    }

    /**
     * the second player
     * @return the second player
     */
    public RoboAlien getPlayer2() {
        return player2;
    }

    public void setPlayer2(RoboAlien player2) {
        this.player2 = player2;
        addEntity(player2);
    }
}
