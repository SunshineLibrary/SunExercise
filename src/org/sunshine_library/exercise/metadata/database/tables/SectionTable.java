package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Sections;
import static org.sunshine_library.exercise.metadata.MetadataContract.Stages;

public class SectionTable extends AbstractTable {
    public static final String TABLE_NAME = "sections";

    private static final String[] ALL_COLUMNS = {
            Sections._IDENTIFIER,
            Sections._PARENT_IDENTIFIER,
            Sections._SEQUENCE,
            Sections._NAME,
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Sections._IDENTIFIER, "INTEGER PRIMARY KEY"},
            {Sections._PARENT_IDENTIFIER, "INTEGER NOT NULL"},
            {Sections._SEQUENCE, "INTEGER"},
            {Sections._NAME, "TEXT"},

    };

    public SectionTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }


}
