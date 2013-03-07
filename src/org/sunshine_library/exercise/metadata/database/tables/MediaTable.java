package org.sunshine_library.exercise.metadata.database.tables;

import android.database.Cursor;
import android.net.Uri;
import org.sunshine_library.exercise.metadata.MetadataContract.*;
import org.sunshine_library.exercise.metadata.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午6:09
 */
public class MediaTable extends AbstractTable {
    public static final String TABLE_NAME = "media";

    private static final String[] ALL_COLUMNS = {
            Media._STRING_ID,
            Media._FILE_ID,
            Media._PATH
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Media._STRING_ID, "TEXT NOT NULL"},
            {Media._FILE_ID, "INTEGER"},
            {Media._PATH, "TEXT"}
    };


    public MediaTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uri.getLastPathSegment().equals("delete")) {
// select _id from media where _id not in (select media_id from pieces where media_id is not null union select media_id from images);
            String query = "SELECT * FROM "
                    + MediaTable.TABLE_NAME + " WHERE " + Media._STRING_ID + " NOT IN ( SELECT " + Activities._MEDIA_ID
                    + " FROM " + ActivityTable.TABLE_NAME + " WHERE " + Activities._MEDIA_ID + " IS NOT NULL UNION SELECT "
                    + Problems._MEDIA_ID + " FROM " + ProblemTable.TABLE_NAME + " WHERE " + Problems._MEDIA_ID
                    + " IS NOT NULL )";
//            Log.i("MediaTable", "query: " + query);
            return getDatabase().rawQuery(query, null);
        } else {
            return super.query(uri, projection, selection, selectionArgs, sortOrder);
        }
    }
}
