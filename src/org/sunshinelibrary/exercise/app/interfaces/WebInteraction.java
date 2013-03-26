package org.sunshinelibrary.exercise.app.interfaces;

import android.util.Log;
import android.webkit.WebView;
import org.sunshinelibrary.support.utils.sync.SyncObserver;

/**
 * @author linuo
 * @version 1.0
 * @date 3/18/13
 */
public class WebInteraction implements SyncObserver{
    private static final String TAG = "WebInteraction";

    WebView mWebView;

    public WebInteraction(WebView webView) {
        mWebView = webView;
    }

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
    public void onCollectionDownloaded(String collectionId, boolean available) {
        Log.i(TAG, "onCollectionDownloaded: " + collectionId + " " + available);
    }

    @Override
    public void onCollectionDownloadProgress(String collectionId, float percentage) {
        Log.i(TAG, "onCollectionDownloadProgress: " + collectionId + " " + percentage);
    }

    public void backPressed(){
        Log.i(TAG, "backPressed");
        mWebView.loadUrl("javascript:Interfaces.backpage()");
    }
}
