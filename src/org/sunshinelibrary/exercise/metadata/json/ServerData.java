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
    public String id= "";
    public String param = "";

    public void fill() {
        if(type.equals("subjects")) {
            param = query(SubjectTable.TABLE_NAME, null, null, null);
        } else if (type.equals("subject")) {
            param = query(LessonTable.TABLE_NAME, Lessons._PARENT_ID, id, Lessons._SEQUENCE);
        } else if (type.equals("lesson")) {
            param = query(StageTable.TABLE_NAME, Stages._PARENT_ID, id, Stages._SEQUENCE);
        } else if (type.equals("stage")) {
            param = query(SectionTable.TABLE_NAME, Sections._PARENT_ID, id, Sections._SEQUENCE);
        } else if (type.equals("section")) {
            param = query(ActivityTable.TABLE_NAME, Activities._PARENT_ID, id, Activities._SEQUENCE);
        } else if (type.equals("activity")) {
            param = queryProblemAndChoices(id);
        } else {
            Log.e(TAG, "wrong type: " + type);
        }
    }

    protected String query(String table, String parentColumn, String parentId, String sortOrder) {
        String selection = ((parentColumn!=null)?(parentColumn + "=?"):null);
        String[] selectionArgs = ((parentId!=null)?new String[]{parentId}:null);
        Cursor cursor = mResolver.query(AUTHORITY_URI.buildUpon().appendPath(table).build(),
                null, selection, selectionArgs, sortOrder);
        String string = CursorUtils.convertToJSONString(cursor);
        cursor.close();
        return string;
    }

    protected String queryProblemAndChoices(String activityId) {
        Cursor problem = mResolver.query(Problems.CONTENT_URI, null, Problems._PARENT_ID + "=?",
                new String[]{activityId}, Problems._SEQUENCE);
        ArrayList<String> problemIDs = new ArrayList<String>();
        while (problem.moveToNext()) {
            problemIDs.add(CursorUtils.getString(problem, Problems._STRING_ID));
            Log.d(TAG, "queryProblemAndChoices problem id: " + CursorUtils.getString(problem, Problems._STRING_ID));
        }
        problem.moveToFirst();
        Cursor choice = mResolver.query(ProblemChoices.CONTENT_URI, null,
                CursorUtils.generateSelectionStringForStringID(ProblemChoices._PARENT_ID, problemIDs), null,
                ProblemChoices._SEQUENCE);
        String string = convertToJSONStringForProblemsAndChoices(problem, choice);
        problem.close();
        choice.close();
        return string;
    }

    public static String convertToJSONStringForProblemsAndChoices(Cursor problem, Cursor choice) {
        Log.d(TAG, "Problem Count: " + problem.getCount() + " Choice Count: " + choice.getCount());
        JSONStringBuilder jsb = new JSONStringBuilder();
        jsb.append("[");
        for(int i=0; i<problem.getCount(); ++i) {
            problem.moveToPosition(i);
            if(i>0) jsb.append(',');
            jsb.append('{');
            for (int j=0; j<problem.getColumnCount(); ++j) {
                if(j>0) jsb.append(',');
                jsb.appendNameAndValue(problem.getColumnName(j), problem.isNull(j)?"":problem.getString(j));
            }
            jsb.append(',');

            jsb.appendWithQuota("choice").append(':');
            jsb.append('[');
            int k = 0;
            String problemId = CursorUtils.getString(problem, Problems._STRING_ID);
            Log.d(TAG, "problemId: " + problemId);
            choice.moveToPosition(-1);
            while(choice.moveToNext()) {
                Log.d(TAG, "problemId: " + CursorUtils.getString(choice, ProblemChoices._PARENT_ID));
                if (CursorUtils.getString(choice, ProblemChoices._PARENT_ID).equals(problemId)) {
                    if (k > 0) jsb.append(',');
                    jsb.append('{');

                    for (int l = 0; l < choice.getColumnCount(); ++l) {
                        if (l > 0) jsb.append(',');
                        jsb.appendNameAndValue(choice.getColumnName(l), choice.isNull(l) ? "" : choice.getString(l));
                    }

                    jsb.append('}');
                    ++k;
                }
            }
            jsb.append(']').toString();

            jsb.append('}');
        }
        return jsb.append(']').toString();
    }
}
