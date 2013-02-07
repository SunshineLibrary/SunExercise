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

    public static final String TABLE_NAME = "files";

    private static final String[] ALL_COLUMNS = {
            MetadataContract.Medias._IDENTIFIER,
            MetadataContract.Medias._OWNER_IDENTIFIER,
            MetadataContract.Medias._OWNER_TYPE,
            MetadataContract.Medias._MEDIA_TYPE,
            MetadataContract.Medias._KEY,
            MetadataContract.Medias._FILE_ID,
            MetadataContract.Medias._PATH,


    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {MetadataContract.Medias._IDENTIFIER, "INTEGER"},
            {MetadataContract.Medias._OWNER_IDENTIFIER, "INTEGER"},
            {MetadataContract.Medias._OWNER_TYPE, "INTEGER"},
            {MetadataContract.Medias._MEDIA_TYPE, "INTEGER"},
            {MetadataContract.Medias._KEY, "INTEGER"},
            {MetadataContract.Medias._FILE_ID, "INTEGER"},
            {MetadataContract.Medias._PATH, "TEXT"},

    };


    public MediaTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
