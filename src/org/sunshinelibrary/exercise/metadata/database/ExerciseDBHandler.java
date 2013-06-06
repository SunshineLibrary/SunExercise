package org.sunshinelibrary.exercise.metadata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.sync.Proxy;
import org.sunshinelibrary.support.utils.database.DBHandler;

/**
 * @author linuo
 * @version 1.0
 * @date 6/6/13
 */
public class ExerciseDBHandler extends DBHandler {
    public ExerciseDBHandler(Context context, String dbName, int dbVersion) {
        super(context, dbName, dbVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.i(getClass().getName(), "oldVersion: " + oldVersion + " newVersion: " + newVersion);
        if (oldVersion < 102) {
            Proxy proxy = ExerciseApplication.getInstance().getSyncManager();
            proxy.reset();
            proxy.sync();
        }
    }
}
