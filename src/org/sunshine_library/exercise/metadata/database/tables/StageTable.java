package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Stages;
import static org.sunshine_library.exercise.metadata.MetadataContract.Lessons;

public class StageTable extends MenuWithForeignKeyTable {
    public static final String TABLE_NAME = "stages";

    public static final String[] ALL_COLUMNS = {
            Stages._IDENTIFIER,
            Stages._PARENT_ID,
            Stages._SEQUENCE,
            Stages._TYPE,
            Stages._NAME,
            Stages._BODY,
            Stages._USER_PROGRESS
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Stages._IDENTIFIER, "TEXT PRIMARY KEY"},
            {Stages._PARENT_ID, "TEXT NOT NULL"},
            {Stages._SEQUENCE, "INTEGER"},
            {Stages._TYPE, "INTEGER"},
            {Stages._NAME, "TEXT"},
            {Stages._BODY, "TEXT"},
            {Stages._USER_PROGRESS, "TEXT"}
    };

    public StageTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

    @Override
    protected String getForeignKey() {
        return Lessons._IDENTIFIER;
    }

    @Override
    protected String getParentTableName() {
        return LessonTable.TABLE_NAME;
    }

    @Override
    protected String getForeignKeyColumn() {
        return Stages._PARENT_ID;
    }
}
