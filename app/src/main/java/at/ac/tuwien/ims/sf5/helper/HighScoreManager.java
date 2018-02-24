package at.ac.tuwien.ims.sf5.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;

import at.ac.tuwien.ims.sf5.sqlite.ScoreContract;
import at.ac.tuwien.ims.sf5.sqlite.ScoreProvider;

/**
 * @Author Benedikt Fuchs
 * singleton
 * saves the scores
 */

public class HighScoreManager {

    private static HighScoreManager manager;

    private static final int MAX_SCORES = 5;

    public static HighScoreManager getManager() {
        if (manager == null) {
            manager = new HighScoreManager();
        }
        return manager;
    }

    /**
     * loads the data async
     *
     * @param context the context to load from
     * @return the cursorLoader waiting for data
     */
    public CursorLoader loadData(Context context) {
        String[] from = new String[]{ScoreContract.ScoreEntry._ID, ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE};
        return new CursorLoader(context, ScoreProvider.CONTENT_URI, from, null, null, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC");
    }

    /**
     * checks if a score is a new highscore
     * @param context the context to load from
     * @param score the highscore to check
     * @return true iff the score is in the top 5
     */
    public boolean isNewHighScore(Context context, int score) {
        String[] from = new String[]{ScoreContract.ScoreEntry._ID, ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE};
        Cursor cursor = context.getContentResolver().query(ScoreProvider.CONTENT_URI, from, null, null, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC");

        Log.i("checkHighScore", "" + score);

        if (score < 0) {
            return false;
        }

        if (cursor.getCount() < MAX_SCORES) {
            return true;
        }

        cursor.moveToFirst();
        cursor.move(MAX_SCORES - 1);
        int lowestScore = cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_NAME_SCORE));
        cursor.close();
        return score > lowestScore;
    }

    /**
     * adds a new score
     * @param context the context to load from
     * @param name the name of the player
     * @param score the score to put
     */
    public void putScore(Context context, String name, int score) {

        ContentValues values = new ContentValues();
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME, name);
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME_SCORE, score);

        context.getContentResolver().insert(ScoreProvider.CONTENT_URI, values);
    }
}
