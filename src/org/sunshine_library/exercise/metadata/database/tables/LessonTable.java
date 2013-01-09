package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Lessons;
import static org.sunshine_library.exercise.metadata.MetadataContract.Subjects;

public class LessonTable extends MenuWithForeignKeyTable {
    public static final String TABLE_NAME = "lessons";

    private static final String[] ALL_COLUMNS = {
            Lessons._IDENTIFIER,
            Lessons._PARENT_ID,
            Lessons._NAME,
            Lessons._TIME,
            Lessons._USER_PROGRESS,
            Lessons._USER_PERCENTAGE,
            Lessons._USER_RESULT
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Lessons._IDENTIFIER, "TEXT PRIMARY KEY"},
            {Lessons._PARENT_ID, "TEXT NOT NULL"},
            {Lessons._NAME, "TEXT"},
            {Lessons._TIME, "INTEGER"},
            {Lessons._USER_PROGRESS, "TEXT"},
            {Lessons._USER_PERCENTAGE, "INT"},
            {Lessons._USER_RESULT, "INT"},
    };

    public LessonTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

    @Override
    protected String getForeignKey() {
        return Subjects._IDENTIFIER;
    }

    @Override
    protected String getParentTableName() {
        return SubjectTable.TABLE_NAME;
    }

    @Override
    protected String getForeignKeyColumn() {
        return Lessons._PARENT_ID;
    }
}
