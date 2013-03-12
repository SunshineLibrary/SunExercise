package org.sunshinelibrary.exercise.metadata.json;

import android.util.Log;
import org.sunshinelibrary.exercise.app.interfaces.HtmlInterface;

/**
 * @author linuo
 * @version 1.0
 * @date 3/12/13
 */
public class UndefinedMayBeProxy implements HtmlInterface{
    private static final String TAG = "UndefinedMayBeProxy";

    @Override
    public String requestJson(String reqJson) {
        return null;
    }

    @Override
    public void loadHtml(String page, String reqJson) {
    }

    @Override
    public String uploadUserData(String string) {
        Log.i(TAG, "uploadUserData: " + string);
        UserData userData = UserData.create(string, UserData.class);
        userData.save();
        return userData.toJsonString();
    }

    @Override
    public String requestData(String string) {
        Log.i(TAG, "requestData: " + string);
        ServerData serverData = ServerData.create(string, ServerData.class);
        String result = serverData.request();
        Log.i(TAG, "returnData: " + result);
        return result;
    }
}
