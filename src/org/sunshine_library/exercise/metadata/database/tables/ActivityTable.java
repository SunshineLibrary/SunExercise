package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Activities;

public class ActivityTable extends AbstractTable {
    public static final String TABLE_NAME = "activities";


    public static final String[] ALL_COLUMNS = {
            Activities._IDENTIFIER,
            Activities._PARENT_IDENTIFIER,
            Activities._SEQUENCE,
            Activities._TYPE,
            Activities._NAME,
            Activities._BODY,
            Activities._JUMP_CONDITION,
            Activities._USER_PROGRESS,
            Activities._USER_DURATION,
            Activities._USER_CORRECT,
            Activities._USER_SCORE,
            Activities._MEDIA_ID,
            Activities._MEDIA_PATH
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Activities._IDENTIFIER, "INTEGER PRIMARY KEY"},
            {Activities._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
            {Activities._SEQUENCE, "INTEGER"},
            {Activities._TYPE, "INTEGER"},
            {Activities._NAME, "TEXT"},
            {Activities._BODY, "TEXT"},
            {Activities._JUMP_CONDITION, "TEXT"},
            {Activities._USER_PROGRESS, "INTEGER"},
            {Activities._USER_DURATION, "INTEGER"},
            {Activities._USER_CORRECT, "FLOAT"},
            {Activities._USER_SCORE, "FLOAT"},
            {Activities._MEDIA_ID, "INTEGER"},
            {Activities._MEDIA_PATH, "INTEGER"}

    };

    public ActivityTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

}
