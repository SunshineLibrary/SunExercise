package org.sunshinelibrary.exercise.metadata;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.app.interfaces.HtmlInteraction;
import org.sunshinelibrary.exercise.metadata.json.Request;
import org.sunshinelibrary.exercise.metadata.sync.Proxy;
import org.sunshinelibrary.exercise.metadata.operation.CheckAvailableOperation;
import org.sunshinelibrary.support.api.ApiManager;
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
public class TestCase extends Thread{
    static private final String TAG = "TestCase";

    static ContentResolver resolver = ExerciseApplication.getInstance().getContentResolver();
    static Random random = new Random();
    static ContentValues values = new ContentValues();
    static Uri uri;
    static Cursor cursor;

    public static final int RUN_CASE = 0;
    public static final int CLEAN = 1;
    public static final int SYNC = 2;
    public static final int DOWNLOAD = 3;
    public static final int LOG_DB = 4;

    static int command = RUN_CASE;


    @Override
    public void run() {
        switch (command) {
            case RUN_CASE:
                testSubject();
                testLesson();
                testStage();
                testSection();
                testActivity();
                testProblemAndChoice();
                testMedia();
                testUserData();
                testProxy();
                break;
            case CLEAN:
                clean();
                logDatabase();
                break;
            case SYNC:
                testSync();
                break;
            case DOWNLOAD:
                testDownload();
            case LOG_DB:
                logDatabase();
                break;
            default:
                throw new RuntimeException("illegal argument");
        }
    }

    public void start(int comm) {
        command = comm;
        start();
    }

    public static void testSync() {
        Proxy proxy = ExerciseApplication.getInstance().getSyncManager();
        HtmlInteraction interaction = new HtmlInteraction();
        proxy.register(interaction);
        proxy.sync();
    }

    public static void testDownload() {
        Cursor cursor = ExerciseApplication.getInstance().getContentResolver().query(Lessons.CONTENT_URI,
                new String[]{Lessons._STRING_ID}, Lessons._DOWNLOAD_FINISH + "=?",
                new String[]{String.valueOf(DOWNLOAD_STATUS.NONE)}, null);
        while (cursor.moveToNext()) {
            String lessonId = CursorUtils.getString(cursor, Lessons._STRING_ID);
            ExerciseApplication.getInstance().getSyncManager().download(lessonId);
        }
        cursor.close();
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

        ArrayList<Pair<String, String>> collection = new ArrayList<Pair<String, String>>();
        collectIDs(Subjects.CONTENT_URI, collection, "subject");
        collectIDs(Lessons.CONTENT_URI, collection, "lesson");
        collectIDs(Stages.CONTENT_URI, collection, "stage");
        collectIDs(Sections.CONTENT_URI, collection, "section");
        collectIDs(Activities.CONTENT_URI, collection, "activity");
        collectIDs(Problems.CONTENT_URI, collection, "problem");
        collectIDs(ProblemChoices.CONTENT_URI, collection, "problem_choice");

        for (Pair<String,String> item : collection) {
            values.clear();
            values.put(UserData._STRING_ID, item.first);
            values.put(UserData._USER_DATA, "{\"key\":\"value\"}");
            values.put(UserData._TYPE, item.second);
            uri = resolver.insert(UserData.CONTENT_URI, values);
        }

        logTable(UserData.CONTENT_URI);

    }

    public static void testProxy() {
        Log.d(TAG, "------------------TEST USER DATA------------------------");
        Log.d(TAG, "-----------------------POST-----------------------------");
        resolver.delete(UserData.CONTENT_URI, null, null);
        Proxy p = new Proxy();
        String FakeUserData = "{\"key\":\"value\"}";

        Request userDataReq = new Request();
        userDataReq.api = "user_data";
        userDataReq.method = "post";
        userDataReq.param.user_data = FakeUserData;
        userDataReq.user_id = "unknown";

        ArrayList<Pair<String, String>> collection = new ArrayList<Pair<String, String>>();
        collectIDs(Subjects.CONTENT_URI, collection, "subject");
        collectIDs(Lessons.CONTENT_URI, collection, "lesson");
        collectIDs(Stages.CONTENT_URI, collection, "stage");
        collectIDs(Sections.CONTENT_URI, collection, "section");
        collectIDs(Activities.CONTENT_URI, collection, "activity");
        collectIDs(Problems.CONTENT_URI, collection, "problem");
        collectIDs(ProblemChoices.CONTENT_URI, collection, "problem_choice");

        for (Pair<String,String> item : collection) {
            userDataReq.param.id = item.first;
            userDataReq.param.type = item.second;
            logJSON(userDataReq.toJsonString());
            p.requestUserData(userDataReq.toJsonString());
        }
        logTable(UserData.CONTENT_URI);

        Log.d(TAG, "------------------TEST USER DATA------------------------");
        Log.d(TAG, "-----------------------GET------------------------------");
        userDataReq.method = "get";
        userDataReq.param.user_data = "";
        for (Pair<String,String> item : collection) {
            userDataReq.param.id = item.first;
            userDataReq.param.type = item.second;
            logJSON(userDataReq.toJsonString());
            p.requestData(userDataReq.toJsonString());
        }

        Log.d(TAG, "-------------------TEST MATERIAL------------------------");
        Request materialReq = new Request();
        materialReq.api = "material";
        materialReq.param.type = "subjects";
        materialReq.param.id = String.valueOf(random.nextInt(1000000));
        materialReq.user_id = "unknown";
        logJSON(materialReq.toJsonString());

        p.requestData(materialReq.toJsonString());
        p.requestData("{\"api\":\"\",\"param\":{\"id\":\"882821\",\"type\":\"subjects\"},\"user_id\":\"unknown\"}");

        materialReq.param.type = "subject";
        cursor = resolver.query(Subjects.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String parentId = CursorUtils.getString(cursor, Subjects._STRING_ID);
        cursor.close();
        materialReq.param.id = parentId;
        logJSON(materialReq.toJsonString());
        p.requestData(materialReq.toJsonString());

        materialReq.param.type = "lesson";
        cursor = resolver.query(Lessons.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Lessons._STRING_ID);
        cursor.close();
        materialReq.param.id = parentId;
        logJSON(materialReq.toJsonString());
        p.requestData(materialReq.toJsonString());

        materialReq.param.type = "stage";
        cursor = resolver.query(Stages.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Stages._STRING_ID);
        cursor.close();
        materialReq.param.id = parentId;
        logJSON(materialReq.toJsonString());
        p.requestData(materialReq.toJsonString());

        materialReq.param.type = "section";
        cursor = resolver.query(Sections.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Sections._STRING_ID);
        cursor.close();
        materialReq.param.id = parentId;
        logJSON(materialReq.toJsonString());
        p.requestData(materialReq.toJsonString());

        materialReq.param.type = "activity";
        cursor = resolver.query(Activities.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Activities._STRING_ID);
        cursor.close();
        materialReq.param.id = parentId;
        logJSON(materialReq.toJsonString());
        p.requestData(materialReq.toJsonString());

        materialReq.param.type = "problem";
        cursor = resolver.query(Problems.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        parentId = CursorUtils.getString(cursor, Problems._STRING_ID);
        cursor.close();
        materialReq.param.id = parentId;
        logJSON(materialReq.toJsonString());
        p.requestData(materialReq.toJsonString());


        Request userInfoReq = new Request();
        userInfoReq.api = "material";
        userInfoReq.param.type = "user_info";
        logJSON(userInfoReq.toJsonString());
        p.requestData(userInfoReq.toJsonString());
    }

    private static void collectIDs(Uri uri, ArrayList<Pair<String,String>> collection, String type) {
        cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            collection.add(new Pair<String, String>(CursorUtils.getString(cursor, Columns._STRING_ID), type));
        }
        cursor.close();
    }

    public static void logJSON(String json) {
//        Log.d(TAG, "************* JSON String Start *****************");
//        Log.d(TAG, json);
//        Log.d(TAG, "************* JSON String END *******************");
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

    public static void clean() {
        resolver.delete(Subjects.CONTENT_URI, null, null);
        resolver.delete(Lessons.CONTENT_URI, null, null);
        resolver.delete(Stages.CONTENT_URI, null, null);
        resolver.delete(Sections.CONTENT_URI, null, null);
        resolver.delete(Activities.CONTENT_URI, null, null);
        resolver.delete(Problems.CONTENT_URI, null, null);
        resolver.delete(ProblemChoices.CONTENT_URI, null, null);
        resolver.delete(Media.CONTENT_URI, null, null);
    }

    public static void logDatabase() {
        logTable(Subjects.CONTENT_URI);
        logTable(Lessons.CONTENT_URI);
        logTable(Stages.CONTENT_URI);
        logTable(Sections.CONTENT_URI);
        logTable(Activities.CONTENT_URI);
        logTable(Problems.CONTENT_URI);
        logTable(ProblemChoices.CONTENT_URI);
        logTable(Media.CONTENT_URI);
    }
}
