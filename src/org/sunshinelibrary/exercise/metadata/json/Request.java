package org.sunshinelibrary.exercise.metadata.json;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.*;

import android.view.View;
import android.widget.Toast;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.sunshinelibrary.exercise.R;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.database.tables.*;
import org.sunshinelibrary.exercise.metadata.sync.Proxy;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.UserInfo;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.JSONStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 3/12/13
 * Trello:
 */
public class Request extends JSONObject {
    private static final String TAG = "Request";
    public static final String GET = "get";
    public static final String POST = "post";
    public static final String MATERIAL = "material";
    public static final String USER_DATA = "user_data";
    public static final String OPEN = "open";
    public static final String PDF = "pdf";
    public static final String AUDIO = "audio";
    public static final String UNKNOWN = "unknown";
    public static final String USER_INFO = "user_info";
    public static final String SUBJECTS = "subjects";
    public static final String EMPTY = "{}";

    public String api = "";
    public String method = "";
    public String user_id = "";
    public Parameter param = new Parameter();

    public class Parameter extends JSONObject {
        public String id = "";
        public String type = "";
        public String user_data = "";
        public String path = "";
    }

    public String request() {
        if (api.equals(USER_DATA)) {
            if (method.equals(POST)) {
                return uploadUserRecord();
            } else if (method.equals(GET)) {
                return queryUserRecord(UserDataTable.TABLE_NAME, UserData._USER_DATA, UserData._STRING_ID);
            } else {
                Log.e(TAG, "wrong param.type: " + param.type);
                return EMPTY;
            }
        } else if (api.equals(OPEN)) {
            return open();
        } else if (param.type.equals(SUBJECTS)) {
            return queryCollection(SubjectTable.TABLE_NAME, "subjects");
        } else if (param.type.equals("subject")) {
            return querySingeFatherAndItsChildren(SubjectTable.TABLE_NAME, LessonTable.TABLE_NAME, "lessons",
                    Subjects._STRING_ID, Lessons._PARENT_ID, param.id, Lessons._SEQUENCE);
        } else if (param.type.equals("lesson")) {
            return querySingeFatherAndItsChildren(LessonTable.TABLE_NAME, StageTable.TABLE_NAME, "stages",
                    Lessons._STRING_ID, Stages._PARENT_ID, param.id, Stages._SEQUENCE);
        } else if (param.type.equals("stage")) {
            return querySingeFatherAndItsChildren(StageTable.TABLE_NAME, SectionTable.TABLE_NAME, "sections",
                    Stages._STRING_ID, Sections._PARENT_ID, param.id, Sections._SEQUENCE);
        } else if (param.type.equals("section")) {
            return querySingeFatherAndItsChildren(SectionTable.TABLE_NAME, ActivityTable.TABLE_NAME, "activities",
                    Sections._STRING_ID, Activities._PARENT_ID, param.id, Activities._SEQUENCE);
        } else if (param.type.equals("activity")) {
            return querySingeFatherAndItsChildren(ActivityTable.TABLE_NAME, ProblemTable.TABLE_NAME, "problems",
                    Activities._STRING_ID, Problems._PARENT_ID, param.id, Problems._SEQUENCE);
        } else if (param.type.equals("problem")) {
            return querySingeFatherAndItsChildren(ProblemTable.TABLE_NAME, ProblemChoiceTable.TABLE_NAME, "choices",
                    Problems._STRING_ID, ProblemChoices._PARENT_ID, param.id, ProblemChoices._SEQUENCE);
        } else if (param.type.equals("media")) {
            return querySingleRecord(MediaTable.TABLE_NAME, param.id);
        } else if (param.type.equals(USER_INFO)) {
            return queryUserInfo();
        } else {
            Log.e(TAG, "wrong param.type: " + param.type);
            return EMPTY;
        }
    }

    protected String queryUserRecord(String table, String recordColumn, String idColumn) {
        Cursor cursor = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(table).build(), new String[]{recordColumn},
                idColumn + "=?", new String[]{param.id}, null);
        String user_data = EMPTY;

        if (cursor.moveToFirst()) {
            user_data = CursorUtils.getString(cursor, recordColumn);
        }
        cursor.close();
        return user_data;
    }

    protected String queryCollection(String table, String collectionName) {
        JSONStringBuilder jsb = new JSONStringBuilder().startObject();
        Cursor cursor = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(table).build(), null, null, null, null);
        jsb.appendTableField(cursor, null, collectionName);
        cursor.close();
        return jsb.endObject().toString();
    }

    protected String querySingleRecord(String table, String id) {
        JSONStringBuilder jsb = new JSONStringBuilder();
        Cursor cursor = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(table).build(), null,
                Columns._STRING_ID + "=?", new String[]{id}, null);
        if (cursor.moveToFirst()) {
            jsb.startObject().appendKeyValuesBySingleLine(cursor).endObject();
            cursor.close();
            return jsb.toString();
        } else {
            cursor.close();
            return EMPTY;
        }
    }

    protected String querySingeFatherAndItsChildren(String fatherTable, String childTable, String childKeyName,
                                                    String fatherColumn, String childColumn, String fatherId, String sortOrder) {
        String[] selectionArgs = new String[]{fatherId};
        Cursor father = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(fatherTable).build(),
                null, fatherColumn + "=?", selectionArgs, null);
        if (father.moveToFirst()) {
            Cursor child = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(childTable).build(),
                    null, childColumn + "=?", selectionArgs, sortOrder);
            JSONStringBuilder jsb = new JSONStringBuilder();
            jsb.appendJSONObjectBySingleFatherAndItsChildren(father, child, childKeyName, fatherColumn, childColumn);
            father.close();
            child.close();
            return jsb.toString();
        } else {
            father.close();
            return EMPTY;
        }
    }

    protected String queryProblemAndChoices(String activityId) {
        String[] selectionArgs = new String[]{activityId};
        Cursor activity = mResolver.query(Activities.CONTENT_URI, null, Activities._STRING_ID + "=?",
                selectionArgs, null);
        activity.moveToFirst();
        JSONStringBuilder jsb = new JSONStringBuilder();
        jsb.startObject().appendKeyValuesBySingleLine(activity);

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
        jsb.appendJSONArrayByMultipleFathersAndTheirChildren(problem, choices, "problems", "choices",
                Problems._STRING_ID, ProblemChoices._PARENT_ID);
        problem.close();
        choices.close();
        return jsb.endObject().toString();
    }

    protected String uploadUserRecord() {
        ContentValues c = new ContentValues(3);
        c.put(UserData._TYPE, param.type);
        c.put(UserData._USER_DATA, param.user_data);

        String selection = UserData._STRING_ID + "=?";
        String[] selectionArgs = new String[]{param.id};
        Cursor cursor = mResolver.query(UserData.CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            int count = mResolver.update(UserData.CONTENT_URI, c, selection, selectionArgs);
        } else {
            c.put(UserData._STRING_ID, param.id);
            Uri uri = mResolver.insert(UserData.CONTENT_URI, c);
        }
        cursor.close();
        ApiManager.getInstance(ExerciseApplication.getInstance().getBaseContext()).getUserRecordUploader()
                .uploadRecord(param.toJsonString());
        return UserRecordResponse.SUCCESS;
    }

    protected String queryUserInfo() {
        Context context;
        try {
            context = ExerciseApplication.getInstance().getBaseContext().createPackageContext(
                    UserInfo.PACKAGE_NAME, Context.MODE_WORLD_WRITEABLE); // where com.example is the owning  app containing the preferences
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, e.getStackTrace().toString());
            return EMPTY;
        }
        SharedPreferences pref = context.getSharedPreferences(UserInfo.SP_NAME, Context.MODE_WORLD_READABLE);
        UserInfoResponse response = new UserInfoResponse(pref);
        return response.toJsonString();
    }

    protected String open() {
        Context context = ExerciseApplication.getInstance().getBaseContext();
        if (param.type.equals(PDF)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param.path));
            intent.setDataAndType(uri, "application/" + param.type);

            try {
                context.startActivity(intent);
                return OpenResponse.SUCCESS;
            } catch (Exception e) {
                Toast.makeText(context, R.string.noAppCanDoThis, Toast.LENGTH_LONG).show();
                return OpenResponse.NOT_PDF_READER_FOUND;
            }
        } else if (param.type.equals(AUDIO)) {
            return ExerciseApplication.getInstance().getSyncManager().playAudio(param.id, param.path);
        } else {
            return OpenResponse.NOT_SUPPORTED;
        }
    }
}
