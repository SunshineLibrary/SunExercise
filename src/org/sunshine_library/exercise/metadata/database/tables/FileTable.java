package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class FileTable extends AbstractTable{

    public static final String TABLE_NAME = "files";

    private static final String[] ALL_COLUMNS = {
            MetadataContract.Files._IDENTIFIER,
            MetadataContract.Files._OWNER_IDENTIFIER,
            MetadataContract.Files._OWNER_TYPE,
            MetadataContract.Files._MEDIA_TYPE,
            MetadataContract.Files._KEY,
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {MetadataContract.Files._IDENTIFIER, "INTEGER"},
            {MetadataContract.Files._OWNER_IDENTIFIER, "INTEGER"},
            {MetadataContract.Files._OWNER_TYPE, "INTEGER"},
            {MetadataContract.Files._MEDIA_TYPE, "INTEGER"},
            {MetadataContract.Files._KEY, "INTEGER"},
    };


    public FileTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
