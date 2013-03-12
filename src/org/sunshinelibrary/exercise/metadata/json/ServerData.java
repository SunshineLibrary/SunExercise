package org.sunshinelibrary.exercise.metadata.json;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.*;
import org.sunshinelibrary.exercise.metadata.database.tables.*;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.JSONStringBuilder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 3/12/13
 * Trello:
 */
public class ServerData extends Data {
    private static final String TAG = "ServerData";
    public String type = "";
    public String user_id = "";
    public Parameter param = new Parameter();

    public class Parameter {
        public String id = "";
    };

    public String request() {
        if(type.equals("subjects")) {
            return queryCollection(SubjectTable.TABLE_NAME, "subjects");
        } else if (type.equals("subject")) {
            return querySingeFatherAndItsChildren(SubjectTable.TABLE_NAME, LessonTable.TABLE_NAME, "lessons",
                    Subjects._STRING_ID, Lessons._PARENT_ID, param.id, Lessons._SEQUENCE);
        } else if (type.equals("lesson")) {
            return querySingeFatherAndItsChildren(LessonTable.TABLE_NAME, StageTable.TABLE_NAME, "stages",
                    Lessons._STRING_ID, Stages._PARENT_ID, param.id, Stages._SEQUENCE);
        } else if (type.equals("stage")) {
            return querySingeFatherAndItsChildren(StageTable.TABLE_NAME, SectionTable.TABLE_NAME, "stages",
                    Stages._STRING_ID, Sections._PARENT_ID, param.id, Sections._SEQUENCE);
        } else if (type.equals("section")) {
            return querySingeFatherAndItsChildren(SectionTable.TABLE_NAME, ActivityTable.TABLE_NAME, "activities",
                    Sections._STRING_ID, Activities._PARENT_ID, param.id, Activities._SEQUENCE);
        } else if (type.equals("activity")) {
            return queryProblemAndChoices(param.id);
        } else {
            Log.e(TAG, "wrong type: " + type);
            return null;
        }
    }

    protected String queryCollection(String table, String collectionName){
        JSONStringBuilder jsb = new JSONStringBuilder();
        Cursor cursor = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(table).build(), null, null, null, null);
        jsb.append('{').appendWithQuota(collectionName).append(':').appendTable(cursor, null);
        cursor.close();
        return jsb.toString();
    }

    protected String querySingeFatherAndItsChildren(String fatherTable, String childTable, String childKeyName,
        String fatherColumn, String childColumn, String fatherId, String sortOrder) {
        String[] selectionArgs = new String[]{fatherId};
        Cursor father = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(fatherTable).build(),
                null, fatherColumn + "=?", selectionArgs, null);
        father.moveToFirst();
        Cursor child = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(childTable).build(),
                null, childColumn + "=?", selectionArgs, sortOrder);
        JSONStringBuilder jsb = new JSONStringBuilder();
        jsb.appendJSONObjectBySingleFatherAndItsChildren(father, child, childKeyName, fatherColumn, childColumn);
        father.close();
        child.close();
        return jsb.toString();
    }

    protected String queryProblemAndChoices(String activityId) {
        String[] selectionArgs = new String[]{activityId};
        Cursor activity = mResolver.query(Activities.CONTENT_URI, null, Activities._STRING_ID + "=?",
                selectionArgs, null);
        activity.moveToFirst();
        JSONStringBuilder jsb = new JSONStringBuilder();
        jsb.append('{').appendKeyValuesBySingleLine(activity).append(',').appendWithQuota("problems").append(':');

        Cursor problem = mResolver.query(Problems.CONTENT_URI, null, Problems._PARENT_ID + "=?",
                selectionArgs, Problems._SEQUENCE);
        ArrayList<String> problemIDs = new ArrayList<String>();
        while (problem.moveToNext()) {
            problemIDs.add(CursorUtils.getString(problem, Problems._STRING_ID));
            Log.d(TAG, "queryProblemAndChoices problem id: " + CursorUtils.getString(problem, Problems._STRING_ID));
        }
        problem.moveToFirst();
        Cursor choices = mResolver.query(ProblemChoices.CONTENT_URI, null,
                CursorUtils.generateSelectionStringForStringID(ProblemChoices._PARENT_ID, problemIDs), null,
                ProblemChoices._SEQUENCE);
        jsb.appendJSONArrayByMultipleFathersAndTheirChildren(problem, choices, "choices",
                Problems._STRING_ID, ProblemChoices._PARENT_ID);
        problem.close();
        choices.close();
        return jsb.toString();
    }
}
