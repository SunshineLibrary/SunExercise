package org.sunshinelibrary.exercise.metadata.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.MetadataContract;
import org.sunshinelibrary.exercise.metadata.database.tables.*;
import org.sunshinelibrary.support.utils.database.DBHandler;
import org.sunshinelibrary.support.utils.database.Table;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/9/13
 */
public class MetadataProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = Matcher.Factory.getMatcher();

    private DBHandler dbHandler;

    private Table subjectTable;
    private Table stageTable;
    private Table lessonTable;
    private Table sectionTable;
    private Table activityTable;
    private Table problemTable;
    private Table problemChoiceTable;
    private Table mediaTable;

    @Override
    public boolean onCreate() {
        dbHandler = ((ExerciseApplication) getContext().getApplicationContext()).getMetadataDBHandler();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int uriMatch = sUriMatcher.match(uri);

        Table table = getTableForMatch(uriMatch);

        if (table == null) {
            throw new IllegalArgumentException();
        }

        switch (uriMatch) {
            case Matcher.SUBJECTS:
            case Matcher.STAGES:
            case Matcher.SECTIONS:
            case Matcher.LESSONS:
            case Matcher.ACTIVITIES:
            case Matcher.PROBLEMS:
            case Matcher.PROBLEM_CHOICES:
            case Matcher.MEDIA:
                return table.query(projection, selection, selectionArgs, sortOrder);
            case Matcher.DELETE_UNUSED_FILE:
                String query = "SELECT * FROM "
                        + MediaTable.TABLE_NAME + " WHERE " + MetadataContract.Media._STRING_ID + " NOT IN ( SELECT " + MetadataContract.Activities._MEDIA_ID
                        + " FROM " + ActivityTable.TABLE_NAME + " WHERE " + MetadataContract.Activities._MEDIA_ID + " IS NOT NULL UNION SELECT "
                        + MetadataContract.Problems._MEDIA_ID + " FROM " + ProblemTable.TABLE_NAME + " WHERE " + MetadataContract.Problems._MEDIA_ID
                        + " IS NOT NULL )";
//            Log.i("MediaTable", "query: " + query);
                return table.getDatabase().rawQuery(query, null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getType(Uri uri) {
        return MimeType.METADATA_MIME_TYPE;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Table table = getTableForMatch(sUriMatcher.match(uri));
        if (table == null) {
            throw new IllegalArgumentException();
        }
        table.insert(values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Table table = getTableForMatch(sUriMatcher.match(uri));
        if (table == null) {
            throw new IllegalArgumentException();
        }
        return table.delete(selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriMatch = sUriMatcher.match(uri);

        Table table = getTableForMatch(uriMatch);
        if (table == null) {
            throw new IllegalArgumentException();
        }
        switch (uriMatch) {

            case Matcher.SUBJECTS:
            case Matcher.STAGES:
            case Matcher.SECTIONS:
            case Matcher.LESSONS:
            case Matcher.ACTIVITIES:
            case Matcher.PROBLEMS:
            case Matcher.PROBLEM_CHOICES:
            case Matcher.MEDIA:
                return table.update(values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException();
        }
    }

    private Table getTableForMatch(int match) {
        switch (match) {

            case Matcher.SUBJECTS:
                if (subjectTable== null) {
                    subjectTable= dbHandler.getTable(SubjectTable.TABLE_NAME);
                }
                return subjectTable;
            case Matcher.STAGES:
                if (stageTable== null) {
                    stageTable= dbHandler.getTable(StageTable.TABLE_NAME);
                }
                return stageTable;
            case Matcher.SECTIONS:
                if (sectionTable == null) {
                    sectionTable = dbHandler.getTable(SectionTable.TABLE_NAME);
                }
                return sectionTable;
            case Matcher.LESSONS:
                if (lessonTable == null) {
                    lessonTable = dbHandler.getTable(LessonTable.TABLE_NAME);
                }
                return lessonTable;
            case Matcher.ACTIVITIES:
                if (activityTable == null){
                    activityTable = dbHandler.getTable(ActivityTable.TABLE_NAME);
                }
                return activityTable;

            case Matcher.PROBLEMS:
                if (problemTable == null){
                    problemTable = dbHandler.getTable(ProblemTable.TABLE_NAME);
                }
                return problemTable;
            case Matcher.PROBLEM_CHOICES:
                if (problemChoiceTable == null){
                    problemChoiceTable = dbHandler.getTable(ProblemChoiceTable.TABLE_NAME);
                }
                return problemChoiceTable;
            case Matcher.MEDIA:
            case Matcher.DELETE_UNUSED_FILE:
                if (mediaTable == null){
                    mediaTable = dbHandler.getTable(MediaTable.TABLE_NAME);
                }
                return mediaTable;
            default:
                return null;
        }
    }
}
