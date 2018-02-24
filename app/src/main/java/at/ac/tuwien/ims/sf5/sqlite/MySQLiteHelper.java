package at.ac.tuwien.ims.sf5.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import at.ac.tuwien.ims.sf5.sqlite.ScoreContract.ScoreEntry;

/**
 * MySQLiteHelper - copied from the example
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ScoreGame.db";

    private final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + ScoreEntry.TABLE_NAME +
                                " (" + ScoreEntry._ID + " " + ScoreEntry.COLUMN_TYPE_ID + " autoincrement," +
                                ScoreEntry.COLUMN_NAME_USERNAME + " " + ScoreEntry.COLUMN_TYPE_USERNAME + "," +
                                ScoreEntry.COLUMN_NAME_SCORE + " " + ScoreEntry.COLUMN_TYPE_SCORE + ");";

    private final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(MySQLiteHelper.class.getName(), "Creating tables in DB");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " +
                oldVersion + " to " + newVersion);
        dropTables(db);
        onCreate(db);
    }

    private void dropTables(SQLiteDatabase db) {
        Log.d(MySQLiteHelper.class.getName(), "Dropping all tables");
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
