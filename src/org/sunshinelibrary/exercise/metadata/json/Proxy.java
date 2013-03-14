package org.sunshinelibrary.exercise.metadata.json;

import android.util.Log;
import org.sunshinelibrary.exercise.app.interfaces.HtmlInterface;

/**
 * @author linuo
 * @version 1.0
 * @date 3/12/13
 */
public class Proxy implements HtmlInterface{
    private static final String TAG = "Proxy";

    @Override
    public String requestJson(String reqJson) {
        return null;
    }

    @Override
    public void loadHtml(String page, String reqJson) {
    }

    @Override
    public String requestUserData(String string) {
        Log.i(TAG, "requestUserData: " + string);
        Request userData = Request.create(string, Request.class);
        String result = userData.request();
        Log.i(TAG, "returnData: " + result);
        return result;
    }

    @Override
    public String requestData(String string) {
        Log.i(TAG, "requestData: " + string);
        Request materialData = Request.create(string, Request.class);
        String result = materialData.request();
        Log.i(TAG, "returnData: " + result);
        return result;
    }
}
