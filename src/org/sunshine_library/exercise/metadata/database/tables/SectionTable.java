package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Sections;

public class SectionTable extends AbstractTable {
    public static final String TABLE_NAME = "sections";

    private static final String[] ALL_COLUMNS = {
            Sections._STRING_ID,
            Sections._PARENT_ID,
            Sections._SEQUENCE,
            Sections._NAME,
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Sections._STRING_ID, "INTEGER PRIMARY KEY"},
            {Sections._PARENT_ID, "INTEGER NOT NULL"},
            {Sections._SEQUENCE, "INTEGER"},
            {Sections._NAME, "TEXT"},

    };

    public SectionTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
