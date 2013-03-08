package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Subjects;

public class SubjectTable extends CustomizedAbstractTable {

    public static final String TABLE_NAME = "subjects";

    private static final String[] ALL_COLUMNS = {
            Subjects._STRING_ID,
            Subjects._NAME,
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Subjects._STRING_ID, "TEXT NOT NULL"},
            {Subjects._NAME, "TEXT DEFAULT \"\""}
    };


    public SubjectTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
