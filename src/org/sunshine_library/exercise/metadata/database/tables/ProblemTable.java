package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Problems;
import static org.sunshine_library.exercise.metadata.MetadataContract.Activities;

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
            Problems._USER_CORRECT
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Problems._IDENTIFIER, "INTEGER PRIMARY KEY"},
            {Problems._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
            {Problems._SEQUENCE, "INTEGER"},
            {Problems._TYPE, "INTEGER"},
            {Problems._BODY, "TEXT"},
            {Problems._ANALYSIS, "TEXT"},
            {Problems._USER_DURATION, "INTEGER"},
            {Problems._USER_CORRECT, "FLOAT"}
    };

    public ProblemTable(DBHandler db) {
        super(db, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
