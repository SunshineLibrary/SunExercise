package org.sunshinelibrary.exercise.app.interfaces;

import android.util.Log;
import org.sunshinelibrary.support.utils.sync.SyncObserver;

/**
 * @author linuo
 * @version 1.0
 * @date 3/18/13
 */
public class HtmlInteraction implements SyncObserver{
    private static final String TAG = "HtmlInteraction";

    @Override
    public void onSyncStart() {
        Log.i(TAG, "onSyncStart");
    }

    @Override
    public void onJsonReceived() {
        Log.i(TAG, "onJsonReceived");
    }

    @Override
    public void onJsonParsed() {
        Log.i(TAG, "onJsonParsed");
    }

    @Override
    public void onSyncCompleted(boolean isSuccess) {
        Log.i(TAG, "onSyncCompleted");
    }

    @Override
    public void onCollectionDownloaded(String collectionId) {
        Log.i(TAG, "onCollectionDownloaded");
    }
}
