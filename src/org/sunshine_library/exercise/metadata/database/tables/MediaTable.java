package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午6:09
 */
public class MediaTable extends AbstractTable{

    public static final String TABLE_NAME = "media";

    private static final String[] ALL_COLUMNS = {
            MetadataContract.Medias._STRING_ID,
            MetadataContract.Medias._FILE_ID,
            MetadataContract.Medias._PATH,


    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {MetadataContract.Medias._STRING_ID, "TEXT NOT NULL"},
            {MetadataContract.Medias._FILE_ID, "INTEGER"},
            {MetadataContract.Medias._PATH, "TEXT"},

    };


    public MediaTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
