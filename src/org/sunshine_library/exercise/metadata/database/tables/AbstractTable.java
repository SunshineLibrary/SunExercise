package org.sunshine_library.exercise.metadata.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import org.sunshine_library.exercise.metadata.database.DBHandler;

/**
 * @author Bowen Sun
 *         <p/>
 *         TableManager provides an abstraction over the meta-data database.
 *         Each table should have a corresponding TableManager to create and
 *         upgrade the tables accordingly.
 */

public abstract class AbstractTable implements Table {

    protected DBHandler dbHandler;
    private String tableName;
    private String[][] columnDefinitions;
    private String[] columns;

    /**
     * TableManager requires a handle on the DatabaseHandler in order to access
     * the database.
     *
     * @param handler
     */
    public AbstractTable(DBHandler handler, String tableName, String[][] columnDefinitions, String[] columns) {
        this.dbHandler = handler;
        this.tableName = tableName;
        this.columnDefinitions = columnDefinitions;
        this.columns = columns;
    }

    /**
     * @return name of the table to be managed
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return an array of column name and column definition pairs
     */
    public String[][] getColumnDefinitions() {
        return columnDefinitions;
    }

    /**
     * @return an array of all column names
     */
    public String[] getColumns() {
        return columns;
    }

    /**
     * Create the database to be managed. Calls getColumnDefinitions() to
     * construct the table with a default query.
     *
     * @param db
     */
    public void createTable(SQLiteDatabase db) {
        dropTable(db);
        db.execSQL(this.getCreateQuery());
    }

    protected String getCreateQuery() {
        String query = "CREATE TABLE " + this.getTableName() + "(";
        for (String[] pair : getColumnDefinitions()) {
            query += pair[0] + " " + pair[1] + " , ";
        }
        query = query.substring(0, query.lastIndexOf(" , ")) + ");";
        return query;
    }

    /**
     * Upgrade the database accordingly, by default destroy the old table and
     * create a new one.
     */
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
         * upgradeTableInSteps method is introduced since database version 114,
         * it can be used to upgrade from version 113. For previous versions, we
         * must drop and recreate the table instead.
         */
        if (oldVersion < 113) {
            createTable(db);
        } else {
            upgradeTableInSteps(db, oldVersion, newVersion);
        }
    }

    public void upgradeTableInSteps(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    private void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + this.getTableName() + ";");
    }

    public void addColumn(SQLiteDatabase db, int oldVersion, int upgradeVersion, String columnName, String columnDef) {
        if (oldVersion < upgradeVersion) {
            db.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s %s", getTableName(), columnName, columnDef));
        }
    }

    public SQLiteDatabase getDatabase() {
        return dbHandler.getWritableDatabase();
    }

    /*
     * Proxy methods for the content provider to individual tables in the
     * database.
     */
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = dbHandler.getReadableDatabase().query(getTableName(),
                projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;

    }

    public Uri insert(Uri uri, ContentValues values) {
        long id = dbHandler.getWritableDatabase().insert(getTableName(), null, values);
        if (id > 0 && uri != null) {
            return uri.buildUpon().appendPath("" + id).build();
        } else {
            return null;
        }
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbHandler.getWritableDatabase().delete(getTableName(), selection, selectionArgs);
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return dbHandler.getWritableDatabase().update(getTableName(), values, selection, selectionArgs);
    }
}
