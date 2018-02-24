package at.ac.tuwien.ims.sf5.sqlite;

import android.provider.BaseColumns;

/**
 * ScoreContract - copied from the example
 * A contract class is a container for constants that define names for URIs, tables, and columns.
 */
public class ScoreContract {

    /**
     * Provides constants for an entry in Score table. BaseColumns interface declares _ID = "id" constant.
     */
    public static abstract class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "highscore";

        public static final String COLUMN_TYPE_ID = "INTEGER PRIMARY KEY";

        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_TYPE_USERNAME = "TEXT";

        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_TYPE_SCORE = "INTEGER";
    }

}
