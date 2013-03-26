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
        mWebView.loadUrl("javascript:Interfaces.onSyncStart()");
    }

    @Override
    public void onJsonReceived() {
        mWebView.loadUrl("javascript:Interfaces.onJsonReceived()");
    }

    @Override
    public void onJsonParsed() {
        mWebView.loadUrl("javascript:Interfaces.onJsonParsed()");
    }

    @Override
    public void onSyncCompleted(boolean isSuccess) {
        mWebView.loadUrl("javascript:Interfaces.onSyncCompleted()");
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
        mWebView.loadUrl("javascript:Interfaces.backpage()");
    }
}
