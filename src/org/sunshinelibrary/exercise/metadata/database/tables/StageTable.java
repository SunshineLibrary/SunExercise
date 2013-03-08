package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.Stages;

public class StageTable extends CustomizedAbstractTable {
    public static final String TABLE_NAME = "stages";

    public static final String[] ALL_COLUMNS = {
            Stages._STRING_ID,
            Stages._PARENT_ID,
            Stages._SEQUENCE,
            Stages._TYPE,
            Stages._USER_PROGRESS,
            Stages._USER_PERCENTAGE
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Stages._STRING_ID, "TEXT NOT NULL"},
            {Stages._PARENT_ID, "TEXT NOT NULL"},
            {Stages._SEQUENCE, "INTEGER DEFAULT 0"},
            {Stages._TYPE, "INTEGER NOT NULL"},
            {Stages._USER_PROGRESS, "TEXT DEFAULT \"\""},
            {Stages._USER_PERCENTAGE, "FLOAT DEFAULT 0"}
    };

    public StageTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

}
