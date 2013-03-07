package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Stages;
import static org.sunshine_library.exercise.metadata.MetadataContract.Lessons;

public class StageTable extends AbstractTable {
    public static final String TABLE_NAME = "stages";

    public static final String[] ALL_COLUMNS = {
            Stages._STRING_ID,
            Stages._PARENT_ID,
            Stages._SEQUENCE,
            Stages._TYPE,
            Stages._USER_PROGRESS,
            Stages._USER_PERCENTAGE,
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Stages._STRING_ID, "INTEGER PRIMARY KEY"},
            {Stages._PARENT_ID, "INTEGER NOT NULL"},
            {Stages._SEQUENCE, "INTEGER"},
            {Stages._TYPE, "INTEGER"},
            {Stages._USER_PROGRESS, "INTEGER"},
            {Stages._USER_PERCENTAGE, "FLOAT"}

    };

    public StageTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

}
