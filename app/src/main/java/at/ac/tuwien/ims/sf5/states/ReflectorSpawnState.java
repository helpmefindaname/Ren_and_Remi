package at.ac.tuwien.ims.sf5.states;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import at.ac.tuwien.ims.sf5.GameResult;
import at.ac.tuwien.ims.sf5.GameSurfaceView;
import at.ac.tuwien.ims.sf5.data.GameData;
import at.ac.tuwien.ims.sf5.data.IEntity;
import at.ac.tuwien.ims.sf5.data.ReflectorEntity;
import at.ac.tuwien.ims.sf5.helper.CanvasManager;
import at.ac.tuwien.ims.sf5.helper.Vector2D;

/**
 * @Author Benedikt Fuchs
 * handles the spawning of reflectors between the shoot rounds
 */
public class ReflectorSpawnState implements IState {

    private IState nextState;
    private final Random rand;

    Map<ReflectorEntity, Integer> entitiesToSpawn;

    public ReflectorSpawnState() {
        this.rand = new Random();
        entitiesToSpawn = new HashMap<>();
    }

    private void addRandomEntity() {
        float length = rand.nextFloat() * 300 + 150;
        float direction = (float) (rand.nextDouble() * Math.PI * 2);

        float startX = rand.nextFloat() * 1024;
        float startY = rand.nextFloat() * 500 + 300;

        Vector2D dir = new Vector2D((float) Math.sin(direction), (float) Math.cos(direction));
        Vector2D start = new Vector2D(startX, startY);
        Vector2D end = start.add(dir.scale(length));

        ReflectorEntity entity = new ReflectorEntity(start, end, 2 * (rand.nextInt(3) + 1));

        int timeToSpawn = 2 + rand.nextInt(3);
        entitiesToSpawn.put(entity, timeToSpawn);
    }

    @Override
    public void setup(GameSurfaceView view) {
        while (entitiesToSpawn.size() < 2) {
            addRandomEntity();
        }
    }

    @Override
    public boolean update(GameData gameData, long frameTime) {

        for (IEntity entity : gameData.getEntities(ReflectorEntity.class)) {
            ReflectorEntity reflectorEntity = (ReflectorEntity) entity;

            reflectorEntity.reduceTime();
        }

        List<ReflectorEntity> delete = new ArrayList<>();
        for (ReflectorEntity entity : entitiesToSpawn.keySet()) {
            int timeToSpawn = entitiesToSpawn.get(entity) - 1;
            if (timeToSpawn == 0) {
                gameData.addEntity(entity);
                delete.add(entity);
                Log.i("reflectorSpawn", "spawning Reflector");
            } else {
                Log.i("reflectorSpawn", "" + timeToSpawn);
                entitiesToSpawn.put(entity, timeToSpawn);
            }
        }

        for (ReflectorEntity entity : delete) {
            entitiesToSpawn.remove(entity);
        }

        gameData.update(frameTime);

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
        return null;
    }
}
