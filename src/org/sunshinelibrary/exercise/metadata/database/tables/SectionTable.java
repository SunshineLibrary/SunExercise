package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Sections;

public class SectionTable extends CustomizedAbstractTable {
    public static final String TABLE_NAME = "sections";

    private static final String[] ALL_COLUMNS = {
            Sections._STRING_ID,
            Sections._PARENT_ID,
            Sections._SEQUENCE,
            Sections._NAME
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Sections._STRING_ID, "TEXT NOT NULL"},
            {Sections._PARENT_ID, "TEXT NOT NULL"},
            {Sections._SEQUENCE, "INTEGER NOT NULL"},
            {Sections._NAME, "TEXT DEFAULT \"\""}
    };

    public SectionTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
