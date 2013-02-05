package org.sunshine_library.exercise.app.json;

import android.content.ContentValues;
import android.util.Log;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 2/4/13
 * Trello:
 */
public class JsonHandler {
    private static final String TAG = "JsonHandler";
    private int mReqId;

    public boolean parseReqId(String json) {
        boolean succeed = true;
        try {
            JsonFactory jFactory = new JsonFactory();
            JsonParser jp = jFactory.createParser(json);
            jp.nextToken(); // for first "{"
            jp.nextToken(); // for :req_id:"
            mReqId = jp.getIntValue();
            jp.close();

        } catch (IOException e){
            succeed = false;
            Log.e(TAG, "file IO exception");
        }
        return succeed;
    }

    public int getReqId() {
        return mReqId;
    }
}
