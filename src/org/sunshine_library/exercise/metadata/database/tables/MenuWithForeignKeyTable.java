package org.sunshine_library.exercise.metadata.database.tables;

import org.sunshine_library.exercise.metadata.database.DBHandler;

public abstract class MenuWithForeignKeyTable extends AbstractTable {
    public MenuWithForeignKeyTable(DBHandler handler,
                                   String tableName,
                                   String[][] columnDefinitions,
                                   String[] columns) {
        super(handler, tableName, columnDefinitions, columns);
    }

    @Override
    protected String getCreateQuery() {
        String query = "CREATE TABLE " + getTableName() + "(";
        for (String[] pair : getColumnDefinitions()) {
            query += pair[0] + " " + pair[1] + " , ";
        }
        query += "FOREIGN KEY (" + getForeignKeyColumn() + ") REFERENCES " + getParentTableName() + "(" + getForeignKey() + "));";
        return query;
    }

    protected abstract String getForeignKey();

    protected abstract String getParentTableName();

    protected abstract String getForeignKeyColumn();
}
