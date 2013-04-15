package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Lessons;

public class LessonTable extends CustomizedAbstractTable {
    public static final String TABLE_NAME = "lessons";

    private static final String[] ALL_COLUMNS = {
            Lessons._STRING_ID,
            Lessons._PARENT_ID,
            Lessons._NAME,
            Lessons._SEQUENCE,
            Lessons._TIME,
            Lessons._USER_PROGRESS,
            Lessons._USER_RESULT,
            Lessons._DOWNLOAD_FINISH
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Lessons._STRING_ID, "TEXT NOT NULL"},
            {Lessons._PARENT_ID, "TEXT NOT NULL"},
            {Lessons._NAME, "TEXT DEFAULT \"\""},
            {Lessons._SEQUENCE, "INTEGER NOT NULL"},
            {Lessons._TIME, "DATETIME DEFAULT '2013-1-1'"},
            {Lessons._USER_PROGRESS, "TEXT DEFAULT \"\""},
            {Lessons._USER_RESULT, "FLOAT DEFAULT 0"},
            {Lessons._DOWNLOAD_FINISH, "TEXT DEFAULT \"none\""}
    };

    public LessonTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
