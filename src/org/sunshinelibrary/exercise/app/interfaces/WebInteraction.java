package org.sunshinelibrary.exercise.app.interfaces;

import android.app.Activity;
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

    WebView mWebView = null;
    Activity mActivity = null;

    public WebInteraction(WebView webView, Activity a) {
        mWebView = webView;
        mActivity = a;
    }

    @Override
    public void onSyncStart() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:Interfaces.onSyncStart()");
            }
        });
    }

    @Override
    public void onJsonReceived() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:Interfaces.onJsonReceived()");
            }
        });
    }

    @Override
    public void onJsonParsed() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:Interfaces.onJsonParsed()");
            }
        });
    }

    @Override
    public void onSyncCompleted(boolean isSuccess) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:Interfaces.onSyncCompleted()");
            }
        });
    }

    @Override
    public void onCollectionDownloaded(final String collectionId, final boolean available) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:onCollectionDownloaded('" + collectionId + "', '" + available + "')");
            }
        });
    }

    @Override
    public void onCollectionDownloadProgress(final String collectionId, final float percentage) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:onCollectionDownloaded('" + collectionId + "', '" + percentage + "')");
            }
        });
    }

    public void backPressed(){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:Interfaces.backpage()");
            }
        });
    }
}
