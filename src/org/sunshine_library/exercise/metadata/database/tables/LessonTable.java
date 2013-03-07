package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Lessons;

public class    LessonTable extends AbstractTable {
    public static final String TABLE_NAME = "lessons";

    private static final String[] ALL_COLUMNS = {
            Lessons._STRING_ID,
            Lessons._PARENT_ID,
            Lessons._NAME,
            Lessons._TIME,
            Lessons._IS_AVALIBLE,
            Lessons._USER_PROGRESS,
            Lessons._USER_RESULT,

    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Lessons._STRING_ID, "INTEGER PRIMARY KEY"},
            {Lessons._PARENT_ID, "INTEGER NOT NULL"},
            {Lessons._NAME, "TEXT"},
            {Lessons._TIME, "DATETIME"},
            {Lessons._IS_AVALIBLE,"INTEGER"},
            {Lessons._USER_PROGRESS, "INTEGER"},
            {Lessons._USER_RESULT, "FLOAT"},
    };

    public LessonTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
