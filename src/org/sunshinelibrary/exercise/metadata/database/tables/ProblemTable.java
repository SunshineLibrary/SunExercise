package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Problems;

public class ProblemTable extends CustomizedAbstractTable{
    public static final String TABLE_NAME = "problems";

    public static final String[] ALL_COLUMNS = {
            Problems._STRING_ID,
            Problems._PARENT_ID,
            Problems._SEQUENCE,
            Problems._TYPE,
            Problems._BODY,
            Problems._ANALYSIS,
            Problems._USER_DURATION,
            Problems._USER_CORRECT,
            Problems._MEDIA_ID
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Problems._STRING_ID, "TEXT NOT NULL"},
            {Problems._PARENT_ID, "TEXT NOT NULL"},
            {Problems._SEQUENCE, "INTEGER NOT NULL"},
            {Problems._TYPE, "INTEGER NOT NULL"},
            {Problems._BODY, "TEXT DEFAULT \"\""},
            {Problems._ANALYSIS, "TEXT DEFAULT \"\""},
            {Problems._USER_DURATION, "INTEGER DEFAULT 0"},
            {Problems._USER_CORRECT, "FLOAT DEFAULT 0"},
            {Problems._MEDIA_ID, "TEXT DEFAULT \"\""}
    };

    public ProblemTable(DBHandler db) {
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
