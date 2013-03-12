package org.sunshinelibrary.exercise.metadata;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.json.ServerData;
import org.sunshinelibrary.exercise.metadata.json.UndefinedMayBeProxy;
import org.sunshinelibrary.exercise.metadata.operation.CheckAvailableOperation;
import org.sunshinelibrary.exercise.metadata.operation.ExerciseOperation;
import org.sunshinelibrary.support.utils.CursorUtils;
import static org.sunshinelibrary.exercise.metadata.MetadataContract.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 3/10/13
 * Trello:
 */
public class TestCase {
    static private final String TAG = "TestCase";

    static ContentResolver resolver = ExerciseApplication.getInstance().getContentResolver();
    static Random random = new Random();
    static ContentValues values = new ContentValues();
    static Uri uri;
    static Cursor cursor;

    public static void run() {
        testSubject();
        testLesson();
        testStage();
        testSection();
        testActivity();
        testProblemAndChoice();
        testMedia();
        testUserData();
        testServerJson();
    }

    public static void testSubject() {
        resolver.delete(Subjects.CONTENT_URI, null, null);

        String NAME1 = "Mathematics";
        String NAME2 = "Chinese";

        values = new ContentValues();
        values.put(Subjects._NAME, NAME1);
        values.put(Subjects._STRING_ID, "su" + random.nextInt(1000000));
        uri = resolver.insert(Subjects.CONTENT_URI, values);

        values.clear();
        values.put(Subjects._NAME, NAME2);
        values.put(Subjects._STRING_ID, "su" + random.nextInt(1000000));
        uri = resolver.insert(Subjects.CONTENT_URI, values);

        logTable(Subjects.CONTENT_URI);
    }

    public static void testLesson() {
        resolver.delete(Lessons.CONTENT_URI, null, null);

        String NAME1 = "Lesson1";
        String NAME2 = "Lesson2";

        cursor = resolver.query(Subjects.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Subjects._STRING_ID);
        cursor.close();

        values.clear();
        values.put(Lessons._NAME, NAME1);
        values.put(Lessons._STRING_ID, "l" + random.nextInt(1000000));
        values.put(Lessons._PARENT_ID, parentId);
        values.put(Lessons._SEQUENCE, 0);
        uri = resolver.insert(Lessons.CONTENT_URI, values);

        values.clear();
        values.put(Lessons._NAME, NAME2);
        values.put(Lessons._STRING_ID, "l" + random.nextInt(1000000));
        values.put(Lessons._PARENT_ID, parentId);
        values.put(Lessons._SEQUENCE, 1);
        uri = resolver.insert(Lessons.CONTENT_URI, values);

        logTable(Lessons.CONTENT_URI);
    }

    public static void testStage() {
        resolver.delete(Stages.CONTENT_URI, null, null);

        cursor = resolver.query(Lessons.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Lessons._STRING_ID);
        cursor.close();

        values.clear();
        values.put(Stages._STRING_ID, "st" + random.nextInt(1000000));
        values.put(Stages._PARENT_ID, parentId);
        values.put(Stages._SEQUENCE, 0);
        values.put(Stages._TYPE, 0);
        uri = resolver.insert(Stages.CONTENT_URI, values);

        values.clear();
        values.put(Stages._STRING_ID, "st" + random.nextInt(1000000));
        values.put(Stages._PARENT_ID, parentId);
        values.put(Stages._SEQUENCE, 1);
        values.put(Stages._TYPE, 1);
        uri = resolver.insert(Stages.CONTENT_URI, values);

        logTable(Stages.CONTENT_URI);
    }

    public static void testSection() {
        resolver.delete(Sections.CONTENT_URI, null, null);

        String NAME1 = "Section1";
        String NAME2 = "Section2";

        cursor = resolver.query(Stages.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Stages._STRING_ID);
        cursor.close();

        values.clear();
        values.put(Sections._NAME, NAME1);
        values.put(Sections._STRING_ID, "se" + random.nextInt(1000000));
        values.put(Sections._PARENT_ID, parentId);
        values.put(Sections._SEQUENCE, 0);
        uri = resolver.insert(Sections.CONTENT_URI, values);

        values.clear();
        values.put(Sections._NAME, NAME2);
        values.put(Sections._STRING_ID, "se" + random.nextInt(1000000));
        values.put(Sections._PARENT_ID, parentId);
        values.put(Sections._SEQUENCE, 1);
        uri = resolver.insert(Sections.CONTENT_URI, values);

        logTable(Sections.CONTENT_URI);
    }

    public static void testActivity() {
        resolver.delete(Activities.CONTENT_URI, null, null);

        String NAME1 = "Activity1";
        String NAME2 = "Activity2";

        cursor = resolver.query(Sections.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Sections._STRING_ID);
        cursor.close();

        values.clear();
        values.put(Activities._NAME, NAME1);
        values.put(Activities._STRING_ID, "a" + random.nextInt(1000000));
        values.put(Activities._PARENT_ID, parentId);
        values.put(Activities._SEQUENCE, 0);
        values.put(Activities._TYPE, Activities.TYPE_PRACTICE);
        uri = resolver.insert(Activities.CONTENT_URI, values);

        values.clear();
        values.put(Activities._NAME, NAME2);
        values.put(Activities._STRING_ID, "a" + random.nextInt(1000000));
        values.put(Activities._PARENT_ID, parentId);
        values.put(Activities._SEQUENCE, 1);
        values.put(Activities._TYPE, Activities.TYPE_VIDEO);
        values.put(Activities._MEDIA_ID, "ma" + random.nextInt(1000000));
        uri = resolver.insert(Activities.CONTENT_URI, values);

        logTable(Activities.CONTENT_URI);
    }

    public static void testProblemAndChoice() {
        resolver.delete(Problems.CONTENT_URI, null, null);

        int CS = random.nextInt(1000000);
        int FBS = random.nextInt(1000000);

        cursor = resolver.query(Activities.CONTENT_URI, null, null, null, Activities._SEQUENCE);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Activities._STRING_ID);
        cursor.close();

        values.clear();
        values.put(Problems._STRING_ID, "p" + CS);
        values.put(Problems._PARENT_ID, parentId);
        values.put(Problems._SEQUENCE, 0);
        values.put(Problems._TYPE, Problems.TYPE_CS);
        values.put(Problems._MEDIA_ID, "mp" + random.nextInt(1000000));
        values.put(Problems._BODY, "Single choice problem, please choice:");
        uri = resolver.insert(Problems.CONTENT_URI, values);

        values.clear();
        values.put(Problems._STRING_ID, "p" + FBS);
        values.put(Problems._PARENT_ID, parentId);
        values.put(Problems._SEQUENCE, 1);
        values.put(Problems._TYPE, Problems.TYPE_FBS);
        values.put(Problems._MEDIA_ID, "mp" + random.nextInt(1000000));
        values.put(Problems._BODY, "Singe filling blank, please fill:");
        uri = resolver.insert(Problems.CONTENT_URI, values);

        logTable(Problems.CONTENT_URI);

        resolver.delete(ProblemChoices.CONTENT_URI, null, null);

        values.clear();
        values.put(ProblemChoices._STRING_ID, "pc" + random.nextInt(1000000));
        values.put(ProblemChoices._PARENT_ID, "p" + CS);
        values.put(ProblemChoices._SEQUENCE, 0);
        values.put(ProblemChoices._DISPLAY_TEXT, "wrong choice1");
        values.put(ProblemChoices._ANSWER, "no");
        uri = resolver.insert(ProblemChoices.CONTENT_URI, values);

        values.clear();
        values.put(ProblemChoices._STRING_ID, "pc" + random.nextInt(1000000));
        values.put(ProblemChoices._PARENT_ID, "p" + CS);
        values.put(ProblemChoices._SEQUENCE, 1);
        values.put(ProblemChoices._DISPLAY_TEXT, "wrong choice2");
        values.put(ProblemChoices._ANSWER, "no");
        uri = resolver.insert(ProblemChoices.CONTENT_URI, values);

        values.clear();
        values.put(ProblemChoices._STRING_ID, "pc" + random.nextInt(1000000));
        values.put(ProblemChoices._PARENT_ID, "p" + CS);
        values.put(ProblemChoices._SEQUENCE, 2);
        values.put(ProblemChoices._DISPLAY_TEXT, "correct choice");
        values.put(ProblemChoices._ANSWER, "yes");
        uri = resolver.insert(ProblemChoices.CONTENT_URI, values);

        values.clear();
        values.put(ProblemChoices._STRING_ID, "pc" + random.nextInt(1000000));
        values.put(ProblemChoices._PARENT_ID, "p" + CS);
        values.put(ProblemChoices._SEQUENCE, 3);
        values.put(ProblemChoices._DISPLAY_TEXT, "wrong choice4");
        values.put(ProblemChoices._ANSWER, "no");
        uri = resolver.insert(ProblemChoices.CONTENT_URI, values);

        values.clear();
        values.put(ProblemChoices._STRING_ID, "pc" + random.nextInt(1000000));
        values.put(ProblemChoices._PARENT_ID, "p" + FBS);
        values.put(ProblemChoices._SEQUENCE, 0);
        values.put(ProblemChoices._ANSWER, "fb answer");
        uri = resolver.insert(ProblemChoices.CONTENT_URI, values);

        logTable(ProblemChoices.CONTENT_URI);
    }

    public static void testMedia() {
        resolver.delete(Media.CONTENT_URI, null, null);


        CheckAvailableOperation cao = new CheckAvailableOperation();
        ArrayList<String> lessonIDs = cao.getCollection(Lessons.CONTENT_URI,Lessons._STRING_ID, Lessons._SEQUENCE,
                null);
        ArrayList<String> mediaIDs = new ArrayList<String>();
        for(String lessonId : lessonIDs) {
            ArrayList<String> collection = cao.CollectMediaIDs(lessonId);
            cao.combine(mediaIDs,collection);
        }

        for(String mediaId : mediaIDs) {
            values.clear();
            values.put(Media._STRING_ID, mediaId);
            values.put(Media._FILE_ID, random.nextInt(1000000));
            values.put(Media._PATH, "file:");
            uri = resolver.insert(Media.CONTENT_URI, values);
        }
        cao.addAll();
        cao.execute();

        logTable(Media.CONTENT_URI);
        logTable(Lessons.CONTENT_URI);
    }

    public static void testUserData() {
        resolver.delete(UserData.CONTENT_URI, null, null);

        ArrayList<String> collection = new ArrayList<String>();
        collectIDs(Subjects.CONTENT_URI, collection);
        collectIDs(Lessons.CONTENT_URI, collection);
        collectIDs(Stages.CONTENT_URI, collection);
        collectIDs(Sections.CONTENT_URI, collection);
        collectIDs(Activities.CONTENT_URI, collection);
        collectIDs(Problems.CONTENT_URI, collection);
        collectIDs(ProblemChoices.CONTENT_URI, collection);

        for (String id : collection) {
            values.clear();
            values.put(UserData._STRING_ID, id);
            values.put(UserData._USER_DATA, "{\"key\":\"value\"}");
            uri = resolver.insert(UserData.CONTENT_URI, values);
        }

        logTable(UserData.CONTENT_URI);

    }

    public static void testServerJson() {
        UndefinedMayBeProxy p = new UndefinedMayBeProxy();

        ServerData sd = new ServerData();
        sd.type = "subjects";
        sd.id = String.valueOf(random.nextInt(1000000));
        sd.user_id = "unknown";
        logJSON(sd.toJsonString());
        p.requestData(sd.toJsonString());


        sd.type = "subject";
        cursor = resolver.query(Subjects.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Subjects._STRING_ID);
        cursor.close();
        sd.id = parentId;
        logJSON(sd.toJsonString());
        p.requestData(sd.toJsonString());

        sd.type = "lesson";
        cursor = resolver.query(Lessons.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Lessons._STRING_ID);
        cursor.close();
        sd.id = parentId;
        logJSON(sd.toJsonString());
        p.requestData(sd.toJsonString());

        sd.type = "stage";
        cursor = resolver.query(Stages.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Stages._STRING_ID);
        cursor.close();
        sd.id = parentId;
        logJSON(sd.toJsonString());
        p.requestData(sd.toJsonString());

        sd.type = "section";
        cursor = resolver.query(Sections.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Sections._STRING_ID);
        cursor.close();
        sd.id = parentId;
        logJSON(sd.toJsonString());
        p.requestData(sd.toJsonString());

        sd.type = "activity";
        cursor = resolver.query(Activities.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Activities._STRING_ID);
        cursor.close();
        sd.id = parentId;
        logJSON(sd.toJsonString());
        p.requestData(sd.toJsonString());

    }

    private static void collectIDs(Uri uri, ArrayList<String> collection) {
        cursor = resolver.query(Subjects.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            collection.add(CursorUtils.getString(cursor, Columns._STRING_ID));
        }
        cursor.close();
    }

    public static void logJSON(String json) {
        Log.d(TAG, "************* JSON String Start *****************");
        Log.d(TAG, json);
        Log.d(TAG, "************* JSON String END *******************");
    }


    public static void logTable(Uri uri) {

        cursor = resolver.query(uri, null, null, null, null);

        Log.d(TAG, "-------------------------------------------------");
        Log.d(TAG, "Table: " + uri.getLastPathSegment());
        while (cursor.moveToNext()) {
            StringBuilder log = new StringBuilder();
            for (int i = 0; i < cursor.getColumnCount(); ++i) {
                String columnName = cursor.getColumnName(i);
                log.append(" ");
                log.append(columnName);
                log.append(" : ");
                switch (cursor.getType(i)) {
                    case Cursor.FIELD_TYPE_STRING:
                        log.append(cursor.getString(i));
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        log.append(cursor.getInt(i));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        log.append(cursor.getFloat(i));
                        break;
                    default:
                        Log.d(TAG, "wrong type: " + cursor.getType(i) + " name: " + columnName);
                }
            }
            Log.d(TAG, log.toString());
        }
        cursor.close();
    }
}
