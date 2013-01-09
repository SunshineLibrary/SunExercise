package org.sunshine_library.exercise.metadata.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public interface Table {

    public String getTableName();

    public String[] getColumns();

    public void createTable(SQLiteDatabase db);

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion);

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public Uri insert(Uri uri, ContentValues values);

    public int delete(Uri uri, String selection, String[] selectionArgs);

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs);

    public SQLiteDatabase getDatabase();
}