package org.sunshine_library.exercise.metadata.database.tables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public interface TableView {

    public String getViewName();

    public void createView(SQLiteDatabase db);

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

}
