package org.sunshine_library.exercise.metadata.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import com.google.gson.stream.JsonReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.MetadataContract;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-1-23
 * Time: 下午8:35
 */

public class DataReceiver {

    public static final String CREATED = "created";
    public static final String DELETED = "deleted";
    public static final String UPDATED = "updated";

    public static final String INIT_STRING = "init";

    public static final String SUBJECTS = "subjects";
    public static final String LESSONS = "lessons";
    public static final String STAGES = "stages";
    public static final String SECTIONS = "sections";
    public static final String ACTIVITIES = "activities";
    public static final String PROBLEMS = "problems";
    public static final String PROBLEM_CHOICES = "problem_choices";
    public static final String FILES = "problem_choices";

    public static final String IDENTIFIER = MetadataContract.Columns._IDENTIFIER;

    String[] TABLES = {SUBJECTS, LESSONS, STAGES, SECTIONS, ACTIVITIES, PROBLEMS, PROBLEM_CHOICES, FILES};


    JSONObject data;
    static ContentResolver resolver = ExerciseApplication.getApplication().getContentResolver();

    public void onReceive(String json) {
        try {
            data = new JSONObject(json);
            JSONObject update = data.getJSONObject(UPDATED);
            JSONObject delete = data.getJSONObject(DELETED);
            JSONObject create = data.getJSONObject(CREATED);
            HandleUpdate(update);
            HandleCreate(create);
            HandleDelete(delete);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void HandleDelete(JSONObject json) throws JSONException{
        JSONArray array;
        JSONObject values;
        ContentValues contentValues;

        for (String table : TABLES){
            array = json.getJSONArray(table);
            for (int i = 0; i < array.length(); i++){
                values = array.getJSONObject(i);
                int id = values.getInt(IDENTIFIER);
                resolver.delete(MetadataContract.getUri(table), IDENTIFIER + " = " + id , null);
            }
        }

    }

    private void HandleCreate(JSONObject json) throws JSONException{
        JSONArray array;
        JSONObject values;
        ContentValues contentValues;

        for (String table : TABLES){
            array = json.getJSONArray(table);
            for (int i = 0; i < array.length(); i++){

                values = array.getJSONObject(i);
                contentValues = new ContentValues();
                for (Iterator iter = json.keys(); iter.hasNext();){
                    String key = (String)iter.next();
                    Object value = json.get(key);
                    if (value instanceof Integer){
                        contentValues.put(key, (Integer) value);
                    }else if (value instanceof String){
                        contentValues.put(key, (String) value);
                    }else if (value instanceof Double){
                        contentValues.put(key, (Double) value); //TODO should be Doube or cast to Float?
                    }
                }

                resolver.insert(MetadataContract.getUri(table), contentValues);
            }
        }
    }



    private void HandleUpdate(JSONObject json) throws JSONException{
        JSONArray array;
        JSONObject values;
        ContentValues contentValues;

        for (String table : TABLES){
            array = json.getJSONArray(table);
            for (int i = 0; i < array.length(); i++){

                values = array.getJSONObject(i);
                int id = 0;
                contentValues = new ContentValues();
                for (Iterator iter = json.keys(); iter.hasNext();){
                    String key = (String)iter.next();
                    Object value = json.get(key);
                    if (value instanceof Integer){
                        contentValues.put(key, (Integer) value);
                        if (key.equals(IDENTIFIER)){
                            id = (Integer) value;
                        }
                    }else if (value instanceof String){
                        contentValues.put(key, (String) value);
                    }else if (value instanceof Double){
                        contentValues.put(key, (Double) value); //TODO should be Doube or cast to Float?
                    }
                }

                resolver.update(MetadataContract.getUri(table), contentValues, IDENTIFIER + " = " + id, null);
            }
        }
    }

}
