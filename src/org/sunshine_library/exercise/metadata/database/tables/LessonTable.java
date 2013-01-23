package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Lessons;
import static org.sunshine_library.exercise.metadata.MetadataContract.Subjects;

public class    LessonTable extends AbstractTable {
    public static final String TABLE_NAME = "lessons";

    private static final String[] ALL_COLUMNS = {
            Lessons._IDENTIFIER,
            Lessons._PARENT_IDENTIFIER,
            Lessons._NAME,
            Lessons._TIME,
            Lessons._USER_PROGRESS,
            Lessons._USER_RESULT
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Lessons._IDENTIFIER, "INTEGER PRIMARY KEY"},
            {Lessons._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
            {Lessons._NAME, "TEXT"},
            {Lessons._TIME, "DATETIME"},
            {Lessons._USER_PROGRESS, "INTEGER"},
            {Lessons._USER_RESULT, "FLOAT"},
    };

    public LessonTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
