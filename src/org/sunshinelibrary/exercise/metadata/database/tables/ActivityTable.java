package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Activities;

public class ActivityTable extends CustomizedAbstractTable {
    public static final String TABLE_NAME = "activities";

    public static final String[] ALL_COLUMNS = {
            Activities._STRING_ID,
            Activities._PARENT_ID,
            Activities._SEQUENCE,
            Activities._TYPE,
            Activities._NAME,
            Activities._BODY,
            Activities._JUMP_CONDITION,
            Activities._USER_PROGRESS,
            Activities._USER_DURATION,
            Activities._USER_CORRECT,
            Activities._USER_SCORE,
            Activities._MEDIA_ID
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Activities._STRING_ID, "TEXT NOT NULL"},
            {Activities._PARENT_ID, "TEXT NOT NULL"},
            {Activities._SEQUENCE, "INTEGER NOT NULL"},
            {Activities._TYPE, "INTEGER NOT NULL"},
            {Activities._NAME, "TEXT DEFAULT \"\""},
            {Activities._BODY, "TEXT DEFAULT \"\""},
            {Activities._JUMP_CONDITION, "TEXT DEFAULT \"\""},
            {Activities._USER_PROGRESS, "TEXT DEFAULT \"\""},
            {Activities._USER_DURATION, "INTEGER DEFAULT 0"},
            {Activities._USER_CORRECT, "FLOAT DEFAULT 0"},
            {Activities._USER_SCORE, "FLOAT DEFAULT 0"},
            {Activities._MEDIA_ID, "TEXT DEFAULT \"\""}
    };

    public ActivityTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
