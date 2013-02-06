package org.sunshine_library.exercise.metadata.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-29
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class DataBack {


    public static final String LESSON_ID = "lesson_id";
    public static final String LESSON_IDENTIFIER = "lesson_identifier";
    public static final String STAGE_IDENTIFIER = "stage_identifier";

    public static final String ACTIVITY_ID = "activity_id";
    public static final String STAGE_ID = "stage_id";
    public static final String ACTIVITY = "activity";
    public static final String ID = "id";
    public static final String USER_PROGRESS = "user_progress";
    public static final String USER_PERCENTAGE = "user_percentage";

    public static final String LESSONS = "lessons";
    public static final String STAGES = "stages";
    public static final String ACTIVITIES = "activities";
    public static final String PROBLEMS = "problems";
    public static final String PROBLEM_CHOICES = "problem_choices";

    public static final String IDENTIFIER = MetadataContract.Columns._IDENTIFIER;
    private static final String PROBLEM = "problem";
    private static final String STAGE = "stage";
    private static final String LESSON = "lesson";
    private static final String ACTIVITY_IDENTIFIER = "activity_identifier";
    private static final String USER_CORRECT = "user_correct";
    private static final String USER_DURATION = "user_duration";
    private static final String SECTION_IDENTIFIER = "section_identifier";
    private static final String USER_SCORE = "user_score";


    private ContentResolver resolver;


    public DataBack() {
        resolver = ExerciseApplication.getApplication().getApplicationContext().getContentResolver();
    }


    /*
    前端完成一个Problem，应该向后端上报JSON。如果这个Problem是所属Activity里面最后一个Problem
    那么应该再次同时上报完成Activity的情况。

    同理，如果完成一个Activity，上报Activity情况，如果是所属Stage里面的最后一个Activity，同时上报Stage情况。

    也就是，完成一个Problem，根据情况上报1-4个Json对象。

          lesson:{
            identifier : int
            user_result : float
          }

          stage:{
            identifier : int
            lesson_identifier: int
            user_progress : string
          }

          activity:{
             identifier : int
             user_duration : float
             user_score : float
             user_correct : float
          }

          problem:{
                identifier: int
                activity_identifier: int
                user_duration: int
                user_correct: float
                problem_choices:[
                    {
                     identifier : 1,
                     user_choice: "hello world"
                    },
                    {
                    identifier : 2 ,
                    user_choice: "hello world"
                    }
                ]
            }

     */

    public void onRecieve(JSONObject json ){
        try {

            if (json.has(LESSON))
                FinisheLesson(json.getJSONObject(LESSON));

            if (json.has(STAGE))
                FinishStage(json.getJSONObject(STAGE));

            if (json.has(PROBLEM))
                FinishProblem(json.getJSONObject(PROBLEM));

            if (json.has(ACTIVITY))
                FinishActivity(json.getJSONObject(ACTIVITY));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void FinisheLesson(JSONObject json) throws JSONException {
        updateRow(LESSONS, json);
    }

    public void FinishStage(JSONObject json) throws JSONException {

        updateValue(LESSONS, json.getInt(LESSON_IDENTIFIER), USER_PROGRESS, json.get(IDENTIFIER));
        updateRow(STAGES, json);
    }

    public void FinishActivity(JSONObject json) throws JSONException {
        int stage_id = json.getInt(STAGE_IDENTIFIER);
        int activity_id = json.getInt(IDENTIFIER);

        float userPrecentage = getStageUserProgress(stage_id, activity_id);

        updateValue(STAGES, stage_id, USER_PROGRESS, activity_id);
        updateValue(STAGES, stage_id, USER_PERCENTAGE, userPrecentage);
        updateValue(ACTIVITIES, activity_id, USER_SCORE, json.get(USER_SCORE));
        updateValue(ACTIVITIES, activity_id, USER_DURATION, json.get(USER_DURATION));
        updateValue(ACTIVITIES, activity_id, USER_CORRECT, json.get(USER_CORRECT));


    }


    public void FinishProblem(JSONObject json) throws JSONException {
        int activity_id = json.getInt(ACTIVITY_IDENTIFIER);
        int problem_id = json.getInt(IDENTIFIER);
        JSONArray choices = json.getJSONArray(PROBLEM_CHOICES);

        updateValue(ACTIVITIES, activity_id, USER_PROGRESS, json.getInt(IDENTIFIER));

        updateValue(PROBLEMS, problem_id, USER_DURATION, json.getInt(USER_DURATION));
        updateValue(PROBLEMS, problem_id, USER_CORRECT, json.getDouble(USER_CORRECT));
        updataArray(choices, PROBLEM_CHOICES);
    }


    private void updateValue(String table, int id, String key, Object value) {

        ContentValues contentValues = new ContentValues();
        if (value instanceof Integer) {
            contentValues.put(key, (Integer) value);
        } else if (value instanceof String) {
            contentValues.put(key, (String) value);
        } else if (value instanceof Double) {
            contentValues.put(key, (Double) value);
        } else if (value instanceof Float) {
            contentValues.put(key, (Float) value);
        }

        resolver.update(MetadataContract.getUri(table), contentValues, IDENTIFIER + " = " + id, null);
    }


    private void updateRow(String table, JSONObject values) throws JSONException {


        ContentValues contentValues = new ContentValues();
        int id = values.getInt(IDENTIFIER);

        for (Iterator iter = values.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Object value = values.get(key);
            if  (value instanceof Double){
               contentValues.put(key, (Double) value);
            } else if  (value instanceof Integer) {
                contentValues.put(key, (Integer) value);
            } else if (value instanceof String) {
                contentValues.put(key, (String) value);
            } else if (value instanceof Float) {
                contentValues.put(key, (Float) value);
            }

        }
        resolver.update(MetadataContract.getUri(table), contentValues, IDENTIFIER + " = " + id, null);

    }

    private void updataArray(JSONArray array, String table) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            updateRow(table, array.getJSONObject(i));
        }
    }


    private float getStageUserProgress(int stage_id, int user_progress) {
        float activityCount = 0;
        int currentActivity = 0;
        Cursor cursorSection = resolver.query(MetadataContract.Sections.CONTENT_URI, null,
                MetadataContract.Sections._IDENTIFIER + " = " + stage_id, null,
                MetadataContract.Sections._SEQUENCE);
        while (cursorSection.moveToNext()) {
            int sectionID = cursorSection.getInt(cursorSection.getColumnIndex(MetadataContract.Sections._IDENTIFIER));
            Cursor cursorActivity = resolver.query(MetadataContract.Activities.CONTENT_URI, null,
                    MetadataContract.Activities._IDENTIFIER + " = " + sectionID, null,
                    MetadataContract.Activities._SEQUENCE);
            while (cursorActivity.moveToNext()) {
                activityCount++;
                int activity_id = cursorActivity.getInt(cursorSection.getColumnIndex(MetadataContract.Activities._IDENTIFIER));
                if (user_progress == activity_id) {
                    currentActivity = activity_id;
                }
            }
        }
        return currentActivity / activityCount;
    }

}
