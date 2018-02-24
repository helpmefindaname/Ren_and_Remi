package at.ac.tuwien.ims.sf5;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import at.ac.tuwien.ims.sf5.helper.CanvasManager;

/**
 * @Author Iris Fiedler
 * the basic GameLoop
 */
public class GameLoop extends Thread {

    private final int FPS = 60;
    private final long TIME_PER_FRAME = (long) (1000.0f / FPS);

    private GameSurfaceView gameSurfaceView;
    private Gameactivity gameactivity;
    private StateMachine stateMachine;
    private boolean running;
    private CanvasManager canvasManager;
    private boolean isPaused;

    /**
     * creates a new gameloop for a activity
     * @param gameactivity the respective activity
     */
    public GameLoop(Gameactivity gameactivity) {

        this.gameSurfaceView = null;
        this.gameactivity = gameactivity;
        this.stateMachine = new StateMachine();
        canvasManager = new CanvasManager();
        isPaused = false;
    }

    @Override
    public void run() {
        long totalTime = 0;
        long lastTime = System.currentTimeMillis();
        long thisTime;
        long deltaTime;
        long timeBuffer = 0;
        this.running = true;
        stateMachine.setup(gameSurfaceView, gameactivity);

        while (running) {

            //calculate time per frame
            thisTime = System.currentTimeMillis();
            deltaTime = thisTime - lastTime;        //time elapsed in milliseconds
            lastTime = thisTime;
            totalTime += deltaTime;
            timeBuffer += deltaTime;
            if (isPaused) {
                timeBuffer = 0;
                continue;
            }
            while (timeBuffer >= TIME_PER_FRAME) {
                update(TIME_PER_FRAME);
                timeBuffer -= TIME_PER_FRAME;
            }
            draw(deltaTime, totalTime);
        }
    }

    private void update(long deltaTime) {
        if (stateMachine.update(deltaTime)) {
            this.running = false;
            gameactivity.nextActivity(stateMachine.getGameResult(), stateMachine.getScore());
        }
    }

    private void draw(long deltaTime, long totalTime) {
        Canvas canvas = null;
        if (gameSurfaceView != null) {
            final SurfaceHolder holder = gameSurfaceView.getHolder();
            try {
                if (holder.getSurface().isValid()) {
                    canvas = holder.lockCanvas(null);
                    synchronized (holder) {
                        if (canvas != null) {
                            canvasManager.setCanvas(canvas);
                            stateMachine.draw(canvasManager, totalTime, deltaTime);
                        }
                    }
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    /**
     * pauses the loop
     */
    public void pause() {
        isPaused = true;
    }

    /**
     * resumes the loop
     */
    public void resumeLoop() {
        isPaused = false;
        if (!running) {
            start();
        }
    }

    /**
     * returns if the loop is paused
     * @return true if the loop is paused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * sets the gameView
     * @param gameSurfaceView the gameView
     */
    public void setGameSurfaceView(GameSurfaceView gameSurfaceView) {
        this.gameSurfaceView = gameSurfaceView;
    }

    /**
     * stops the loop
     */
    public void stopLoop() {
        running = false;
    }
}
