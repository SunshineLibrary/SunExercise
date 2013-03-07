package org.sunshine_library.exercise.metadata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.sunshine_library.exercise.metadata.database.tables.Table;

import java.util.HashMap;

/**
 * @author Bowen Sun
 *         <p/>
 *         Manages the meta-data database,
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 100;
    private static final String DB_NAME = "metadb";
    private HashMap<String, Table> tableManagers;

    /**
     * @param context
     */
    public DBHandler(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        tableManagers = new HashMap<String, Table>();
    }

    public void addTableManager(String tableName, Table table) {
        tableManagers.put(tableName, table);
    }

    public Table getTableManager(String tableName) {
        return tableManagers.get(tableName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Table table : tableManagers.values()) {
            table.createTable(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table table : tableManagers.values()) {
            table.upgradeTable(db, oldVersion, newVersion);
        }
    }
}
