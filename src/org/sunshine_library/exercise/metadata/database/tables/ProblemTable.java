package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Problems;

public class ProblemTable extends AbstractTable{
    public static final String TABLE_NAME = "problems";

    public static final String[] ALL_COLUMNS = {
            Problems._IDENTIFIER,
            Problems._PARENT_IDENTIFIER,
            Problems._SEQUENCE,
            Problems._TYPE,
            Problems._BODY,
            Problems._ANALYSIS,
            Problems._USER_DURATION,
            Problems._USER_CORRECT,
            Problems._MEDIA_ID,
            Problems._MEDIA_PATH,

    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Problems._IDENTIFIER, "INTEGER PRIMARY KEY"},
            {Problems._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
            {Problems._SEQUENCE, "INTEGER"},
            {Problems._TYPE, "INTEGER"},
            {Problems._BODY, "TEXT"},
            {Problems._ANALYSIS, "TEXT"},
            {Problems._USER_DURATION, "INTEGER"},
            {Problems._USER_CORRECT, "FLOAT"},
            {Problems._MEDIA_ID, "INTEGER"},
            {Problems._MEDIA_PATH, "TEXT"}

    };

    public ProblemTable(DBHandler db) {
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
