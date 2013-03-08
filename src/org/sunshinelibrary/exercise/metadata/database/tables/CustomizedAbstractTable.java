package org.sunshinelibrary.exercise.metadata.database.tables;

import org.sunshinelibrary.support.utils.database.AbstractTable;
import org.sunshinelibrary.support.utils.database.DBHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 3/7/13
 * Trello:
 */
public class CustomizedAbstractTable extends AbstractTable {

    public CustomizedAbstractTable(DBHandler handler, String tableName, String[][] columnDefinitions,
                                   String[] columns) {
        super(handler, tableName, columnDefinitions, columns);
        versionIntroduced = Integer.MIN_VALUE;
    }
}
