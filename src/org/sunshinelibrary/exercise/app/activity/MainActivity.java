package org.sunshinelibrary.exercise.app.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.*;
import android.widget.*;
import org.sunshinelibrary.exercise.R;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.app.interfaces.AndroidDataInterface;
import org.sunshinelibrary.exercise.app.interfaces.AndroidInterface;
import org.sunshinelibrary.exercise.app.interfaces.AndroidUIInterface;
import org.sunshinelibrary.exercise.app.interfaces.WebInteraction;
import org.sunshinelibrary.exercise.app.service.NotificationService;
import org.sunshinelibrary.exercise.app.ui.HTML5WebView;
import org.sunshinelibrary.exercise.metadata.TestCase;
import org.sunshinelibrary.support.api.UserInfo;


public class MainActivity extends TopActivity implements AndroidUIInterface {

    // TODO
    private static final boolean DEBUG = false;
    private static final int MENU_DEBUG = 33;
    private static final int MENU_CLEAN = 34;
    private static final int MENU_SYNC = 35;
    private static final int MENU_DOWNLOAD = 36;
    private static final int MENU_LOG_DB = 37;
    private static final int MENU_DUMP = 38;
    private static final int MENU_CLEAN_USER_DATA = 39;
    private static final int MENU_RELOAD = 40;
    private static final int MENU_VIDEO = 41;
    private static final int MENU_PDF = 42;

    private static final String TAG = "Main";
    private static final String ASSETS = "file:///android_asset/";
    private static final String HTML = ".html";

    private HTML5WebView mWebView;
    private LinearLayout mLinearLayout;
    private AndroidInterface mInterface;
    private WebInteraction mInteraction;
    private boolean mSignIn = false;
    private boolean mLoadReady = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLinearLayout = (LinearLayout) findViewById(R.id.web);
        mWebView = new HTML5WebView(this);

        mInterface = ExerciseApplication.getInstance().getSyncManager();
        mInteraction = new WebInteraction(mWebView, this);
        mInterface.setUIInterface(this);
        mInterface.register(mInteraction);

//        if (savedInstanceState != null) {
//            mWebView.restoreState(savedInstanceState);
//        } else {
//            mWebView.addJavascriptInterface(mInterface, "android");
//            mWebView.loadUrl(ASSETS + "index" + HTML);
//        }


        mLinearLayout.addView(mWebView.getLayout(), HTML5WebView.COVER_SCREEN_PARAMS);
        mWebView.setInteraction(mInteraction);
        mWebView.addJavascriptInterface(mInterface, "android");
        mWebView.loadUrl(ASSETS + "index" + HTML);

        startService(new Intent(MainActivity.this, NotificationService.class));

        Intent intent = new Intent(UserInfo.ACTION_SIGN_IN_ACTIVITY);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            startActivityForResult(intent, UserInfo.SIGN_IN_REQUEST);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Not install the right version of SunDaemon)", Toast.LENGTH_LONG)
                    .show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UserInfo.SIGN_IN_REQUEST) {
            if (resultCode == RESULT_OK) {
                mSignIn = true;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            String VersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.i(TAG, "onResume VersionName:" + VersionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i(TAG, "onResume");
        }
        if (mSignIn && mLoadReady)
            mInterface.autoSync();

    }


    public void onBackPressed() {
        Log.i(TAG, "in" + mWebView.inCustomView());
        if (mWebView.inCustomView()) {
            mWebView.hideCustomView();
            return;
        } else {
            mInteraction.backPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView.inCustomView()) {
            mWebView.hideCustomView();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mWebView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (DEBUG) {
            menu.add(0, MENU_CLEAN_USER_DATA, 0, "Reset");
            menu.add(0, MENU_RELOAD, 0 , "Reload");
            menu.add(0, MENU_DUMP, 0 , "Dump");
            menu.add(0, MENU_PDF, 0 , "Pdf");
            menu.add(0, MENU_VIDEO, 0, "Video");
            menu.add(1, MENU_SYNC, 0, "Sync");
            menu.add(1, MENU_DOWNLOAD, 0, "Download");
            menu.add(2, MENU_DEBUG, 0, "TestCase");
            menu.add(2, MENU_CLEAN, 0, "Clean");
            menu.add(2, MENU_LOG_DB, 0, "LogDB");

            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DEBUG:
                new TestCase().start(TestCase.RUN_CASE);
                return true;
            case MENU_CLEAN:
                new TestCase().start(TestCase.CLEAN);
                return true;
            case MENU_SYNC:
                mInterface.sync();
                return true;
            case MENU_DOWNLOAD:
                new TestCase().start(TestCase.DOWNLOAD);
                return true;
            case MENU_CLEAN_USER_DATA:
                new TestCase().start(TestCase.CLEAN_USER_DATA);
                return true;
            case MENU_RELOAD:
                mWebView.loadUrl(ASSETS + "index" + HTML);
                return true;
            case MENU_LOG_DB:
                new TestCase().start(TestCase.LOG_DB);
                return true;
            case MENU_DUMP:
                new TestCase().start(TestCase.DUMP);
                return true;
            case MENU_VIDEO:
                mWebView.loadUrl(ASSETS + "test" + HTML);
                return true;
            case MENU_PDF:
                new TestCase().start(TestCase.PDF);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showExitDialog() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.openExitDialog();
            }
        });
    }

    @Override
    public void log(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    @Override
    public void onReady() {
        mLoadReady = true;
        if (mSignIn)
            mInterface.autoSync();
    }

    public void testVideo(View view) {
        mWebView.loadUrl("http://freebsd.csie.nctu.edu.tw/~freedom/html5/");
    }
}
