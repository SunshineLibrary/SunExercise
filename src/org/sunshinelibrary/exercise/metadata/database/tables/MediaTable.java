package org.sunshinelibrary.exercise.metadata.database.tables;

import android.database.Cursor;
import android.net.Uri;
import org.sunshinelibrary.exercise.metadata.MetadataContract.*;
import org.sunshinelibrary.support.utils.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午6:09
 */
public class MediaTable extends CustomizedAbstractTable {
    public static final String TABLE_NAME = "media";

    private static final String[] ALL_COLUMNS = {
            Media._STRING_ID,
            Media._FILE_ID,
            Media._PATH
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Media._STRING_ID, "TEXT NOT NULL"},
            {Media._FILE_ID, "INTEGER"},
            {Media._PATH, "TEXT DEFAULT \"\""}
    };

    public MediaTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
