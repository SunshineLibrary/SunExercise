package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Sections;
import static org.sunshine_library.exercise.metadata.MetadataContract.Stages;

public class SectionTable extends MenuWithForeignKeyTable {
    public static final String TABLE_NAME = "sections";

    private static final String[] ALL_COLUMNS = {
            Sections._IDENTIFIER,
            Sections._PARENT_IDENTIFIER,
            Sections._SEQUENCE,
            Sections._NAME,
            Sections._USER_PROGRESS
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Sections._IDENTIFIER, "TEXT PRIMARY KEY"},
            {Sections._PARENT_IDENTIFIER, "TEXT NOT NULL"},
            {Sections._SEQUENCE, "INTEGER"},
            {Sections._NAME, "TEXT"},
            {Sections._USER_PROGRESS, "INTEGER"}

    };

    public SectionTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

    @Override
    protected String getForeignKey() {
        return Stages._IDENTIFIER;
    }

    @Override
    protected String getParentTableName() {
        return StageTable.TABLE_NAME;
    }

    @Override
    protected String getForeignKeyColumn() {
        return Sections._PARENT_IDENTIFIER;
    }
}
