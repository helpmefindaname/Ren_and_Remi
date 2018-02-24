package at.ac.tuwien.ims.sf5;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @Author Iris Fiedler
 * The Basic view, providing a canvas for the gameLoop
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop loop;

    /**
     * creates the gameView
     * @param context the context of the view
     * @param attrs attributes
     */
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    /**
     * acesses the GameActivity owning this view,
     * null if the owner is no GameActivity
     * @return the Gameactivity owning this view
     */
    public Gameactivity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Gameactivity) {
                return (Gameactivity) context;
            }
            if (context instanceof Activity) {
                return null;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        loop = this.getActivity().getLoop();

        if (loop == null) {
            loop = this.getActivity().createLoop();
            loop.setGameSurfaceView(this);
            loop.start();
        } else {
            loop.setGameSurfaceView(this);
            loop.resumeLoop();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (loop != null) {
            loop.pause();
        }
    }
}
