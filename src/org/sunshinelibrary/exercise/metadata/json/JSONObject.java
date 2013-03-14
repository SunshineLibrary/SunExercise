package org.sunshinelibrary.exercise.metadata.json;

import android.content.ContentResolver;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;

/**
 * @author linuo
 * @version 1.0
 * @date 3/12/13
 */
public class JSONObject {
    private static final String TAG = "JSONObject";

    ContentResolver mResolver = ExerciseApplication.getInstance().getBaseContext().getContentResolver();

    public String toJsonString(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, e.getStackTrace().toString());
            return "";
        }
    }

    public static <T> T create(String json, java.lang.Class<T> valueType) {
        try {
            return new ObjectMapper().readValue(json, valueType);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, e.getStackTrace().toString());
            return null;
        }
    }
}
