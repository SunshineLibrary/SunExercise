package org.sunshinelibrary.exercise.metadata.json;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.MetadataContract;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 3/12/13
 * Trello:
 */
public class UserData extends Data {
    private static final String TAG = "UserData";

    public String id = "";
    public String user_data = "";

    public static final Uri URI = MetadataContract.UserData.CONTENT_URI;

    public void save() {
        ContentValues values = new ContentValues();
        values.put("user_data", user_data);
        Cursor cursor = mResolver.query(URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            mResolver.update(URI, values, "id=?", new String[]{id});
        } else {
            values.put("id", id);
            mResolver.insert(URI, values);
        }
        cursor.close();
    }
}
