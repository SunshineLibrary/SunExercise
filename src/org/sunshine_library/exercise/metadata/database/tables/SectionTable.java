package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Lessons;
import static org.sunshine_library.exercise.metadata.MetadataContract.Sections;

public class SectionTable extends MenuWithForeignKeyTable {
    public static final String TABLE_NAME = "sections";

    private static final String[] ALL_COLUMNS = {
            Sections._IDENTIFIER,
            Sections._PARENT_ID,
            Sections._SEQUENCE,
            Sections._NAME,
            Sections._USER_PROGRESS
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Sections._IDENTIFIER, "TEXT PRIMARY KEY"},
            {Sections._PARENT_ID, "TEXT NOT NULL"},
            {Sections._SEQUENCE, "INTEGER"},
            {Sections._NAME, "TEXT"},
            {Sections._USER_PROGRESS, "INTEGER"}

    };

    public SectionTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

    @Override
    protected String getForeignKey() {
        return Lessons._IDENTIFIER;
    }

    @Override
    protected String getParentTableName() {
        return LessonTable.TABLE_NAME;
    }

    @Override
    protected String getForeignKeyColumn() {
        return Sections._PARENT_ID;
    }
}
