package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Subjects;

public class SubjectTable extends AbstractTable {

    public static final String TABLE_NAME = "subjects";

    private static final String[] ALL_COLUMNS = {
            Subjects._IDENTIFIER,
            Subjects._NAME,
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Subjects._IDENTIFIER, "TEXT PRIMARY KEY"},
            {Subjects._NAME, "TEXT"}
    };


    public SubjectTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
