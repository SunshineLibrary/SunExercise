package org.sunshine_library.exercise.metadata.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.database.DBHandler;
import org.sunshine_library.exercise.metadata.database.tables.*;

import java.io.FileNotFoundException;

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
    private Table filesTable;
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
            // Collections
            case Matcher.SUBJECTS:
            case Matcher.STAGE:
            // TODO:
            case Matcher.SECTIONS:
            case Matcher.LESSONS:
            case Matcher.ACTIVITIES:
            case Matcher.PROBLEMS:
            case Matcher.PROBLEM_CHOICES:
            case Matcher.FILES:
            case Matcher.DIALOGS:
                return table.query(uri, projection, selection, selectionArgs, sortOrder);

            // Elements
            case Matcher.ACTIVITIES_IDENTIFIER:
            case Matcher.PROBLEMS_IDENTIFIER:
                return table.query(uri, projection, MetadataContract.Columns._IDENTIFIER + "=" + uri.getLastPathSegment(), null, null);

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
        return table.insert(uri, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Table table = getTableForMatch(sUriMatcher.match(uri));
        if (table == null) {
            throw new IllegalArgumentException();
        }
        return table.delete(uri, selection, selectionArgs);
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
            case Matcher.ACTIVITIES_IDENTIFIER:
            case Matcher.PROBLEMS_IDENTIFIER:
                selection =  MetadataContract.Columns._IDENTIFIER+ " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
            case Matcher.SUBJECTS:
            case Matcher.STAGE:
                // TODO:
            case Matcher.SECTIONS:
            case Matcher.LESSONS:
            case Matcher.ACTIVITIES:
            case Matcher.PROBLEMS:
            case Matcher.PROBLEM_CHOICES:
            case Matcher.FILES:
            case Matcher.DIALOGS:
                return table.update(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException();
        }
    }

    private Table getTableForMatch(int match) {
        switch (match) {
            case Matcher.SUBJECTS:
                if (subjectTable== null) {
                    subjectTable= dbHandler.getTableManager(SubjectTable.TABLE_NAME);
                }
                return subjectTable;
            case Matcher.STAGE:
                if (stageTable== null) {
                    stageTable= dbHandler.getTableManager(SubjectTable.TABLE_NAME);
                }
                return stageTable;
            case Matcher.SECTIONS:
                if (sectionTable == null) {
                    sectionTable = dbHandler.getTableManager(SectionTable.TABLE_NAME);
                }
                return sectionTable;
            case Matcher.LESSONS:
                if (lessonTable == null) {
                    lessonTable = dbHandler.getTableManager(LessonTable.TABLE_NAME);
                }
                return lessonTable;
            case Matcher.ACTIVITIES:
            case Matcher.ACTIVITIES_IDENTIFIER:
                if (activityTable == null) {
                    activityTable = dbHandler.getTableManager(ActivityTable.TABLE_NAME);
                }
                return activityTable;
            case Matcher.PROBLEMS:
            case Matcher.PROBLEMS_IDENTIFIER:
                if (problemTable == null) {
                    problemTable = dbHandler.getTableManager(ProblemTable.TABLE_NAME);
                }
                return problemTable;
            // TODO:
//            case Matcher.PROBLEM_CHOICES:
//            case Matcher.FILES:
//            case Matcher.DIALOGS:

            default:
                return null;
        }
    }
}
