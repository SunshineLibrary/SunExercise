package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Problems;

public class ProblemTable extends AbstractTable{
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
            Problems._MEDIA_ID,

    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Problems._STRING_ID, "INTEGER PRIMARY KEY"},
            {Problems._PARENT_ID, "INTEGER NOT NULL"},
            {Problems._SEQUENCE, "INTEGER"},
            {Problems._TYPE, "INTEGER"},
            {Problems._BODY, "TEXT"},
            {Problems._ANALYSIS, "TEXT"},
            {Problems._USER_DURATION, "INTEGER"},
            {Problems._USER_CORRECT, "FLOAT"},
            {Problems._MEDIA_ID, "INTEGER"},

    };

    public ProblemTable(DBHandler db) {
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
