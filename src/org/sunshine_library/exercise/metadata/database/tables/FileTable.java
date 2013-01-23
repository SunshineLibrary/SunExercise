package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.MetadataContract.Files;
import org.sunshine_library.exercise.metadata.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-19
 * Time: 下午6:09
 */
public class FileTable extends AbstractTable{

    public static final String TABLE_NAME = "files";

    private static final String[] ALL_COLUMNS = {
            Files._IDENTIFIER,
            Files._OWNER_IDENTIFIER,
            Files._OWNER_TYPE,
            Files._MEDIA_TYPE,
            Files._KEY,
    };

    private static final String[][] COLUMN_DEFINITIONS = {
            {Files._IDENTIFIER, "INTEGER"},
            {Files._OWNER_IDENTIFIER, "INTEGER"},
            {Files._OWNER_TYPE, "INTEGER"},
            {Files._MEDIA_TYPE, "INTEGER"},
            {Files._KEY, "INTEGER"},
    };


    public FileTable(DBHandler handler) {
        super(handler, TABLE_NAME, COLUMN_DEFINITIONS, ALL_COLUMNS);
    }
}
