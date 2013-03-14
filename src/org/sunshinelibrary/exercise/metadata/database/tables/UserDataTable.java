package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.DBHandler;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.UserData;

public class UserDataTable extends CustomizedAbstractTable {

    public static final String TABLE_NAME = "user_data";

    private static final String[] ALL_COLUMNS = {
            UserData._STRING_ID,
            UserData._USER_DATA,
            UserData._TYPE
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {UserData._STRING_ID, "TEXT NOT NULL"},
            {UserData._USER_DATA, "TEXT DEFAULT \"\""},
            {UserData._TYPE, "TEXT NOT NULL"}
    };


    public UserDataTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
