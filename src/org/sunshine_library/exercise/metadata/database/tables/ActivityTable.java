package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

import static org.sunshine_library.exercise.metadata.MetadataContract.Activities;
import static org.sunshine_library.exercise.metadata.MetadataContract.Sections;

public class ActivityTable extends MenuWithForeignKeyTable {
    public static final String TABLE_NAME = "activities";

    public static final String[] ALL_COLUMNS = {
            Activities._IDENTIFIER,
            Activities._PARENT_IDENTIFIER,
            Activities._SEQUENCE,
            Activities._TYPE,
            Activities._NAME,
            Activities._BODY,
            Activities._WEIGHT,
            Activities._JUMP_CONDITION,
            Activities._USER_PROGRESS,
            Activities._USER_DURATION,
            Activities._USER_RESULT
    };

    public static final String[][] COLUMN_DEFINITIONS = {
            {Activities._IDENTIFIER, "INTEGER PRIMARY KEY"},
            {Activities._PARENT_IDENTIFIER, "TEXT NOT NULL"},
            {Activities._SEQUENCE, "INTEGER"},
            {Activities._TYPE, "INTEGER"},
            {Activities._NAME, "TEXT"},
            {Activities._BODY, "TEXT"},
            {Activities._WEIGHT, "INTEGER"},
            {Activities._JUMP_CONDITION, "TEXT"},
            {Activities._USER_PROGRESS, "TEXT"},
            {Activities._USER_DURATION, "INTEGER"},
            {Activities._USER_RESULT, "INTEGER"}
    };

    public ActivityTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

    @Override
    protected String getForeignKey() {
        return Sections._IDENTIFIER;
    }

    @Override
    protected String getParentTableName() {
        return SectionTable.TABLE_NAME;
    }

    @Override
    protected String getForeignKeyColumn() {
        return Activities._PARENT_IDENTIFIER;
    }
}
