package org.sunshinelibrary.exercise.metadata.sync;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import org.json.JSONObject;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.app.interfaces.AndroidDataInterface;
import org.sunshinelibrary.exercise.app.interfaces.AndroidInterface;
import org.sunshinelibrary.exercise.app.interfaces.AndroidUIInterface;

import static org.sunshinelibrary.exercise.metadata.MetadataContract.Lessons;

import org.sunshinelibrary.exercise.metadata.json.Request;
import org.sunshinelibrary.exercise.metadata.operation.CheckAvailableOperation;
import org.sunshinelibrary.exercise.metadata.operation.ExerciseOperation;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.ApiUriBuilder;
import org.sunshinelibrary.support.api.subscription.Subscription;
import org.sunshinelibrary.support.api.subscription.SubscriptionDataListener;
import org.sunshinelibrary.support.api.subscription.SubscriptionManager;
import org.sunshinelibrary.support.api.subscription.SubscriptionRequest;
import org.sunshinelibrary.support.utils.ApplicationInterface;
import org.sunshinelibrary.support.utils.CursorUtils;
import org.sunshinelibrary.support.utils.LockManager;
import org.sunshinelibrary.support.utils.database.Contract;
import org.sunshinelibrary.support.utils.json.JsonHandler;
import org.sunshinelibrary.support.utils.sync.SyncObserver;

import static org.sunshinelibrary.support.utils.database.Contract.DOWNLOAD_STATUS;

import java.util.ArrayList;

/**
 * @author linuo
 * @version 1.0
 * @date 3/12/13
 */
public class Proxy implements AndroidInterface, SubscriptionDataListener {
    private static final String TAG = "Proxy";
    private static final String PATH = "/api/exercise/updates.json";

    static private final String SP_SYNC = "sync";
    static private final String SP_LAST_TIME = "last_time";
    static private final String FAILED_JSON = "{}";
    static private final int DURATION = 2*60*60*1000;//2*60*60*1000;  // 2 hours

    boolean mIsSynchronizing = false;
    AndroidUIInterface mUIInterface = null;

    Subscription mSubscription;
    SyncObserver mObserver;

    private LockManager mLockManager;
    private LockManager.Token mLockToken;

    public Proxy() {
        mLockManager = LockManager.getInstance(ExerciseApplication.getInstance().getBaseContext());
    }

    private void acquireLock() {
        mLockToken = mLockManager.acquireWakeLock(mLockToken);
    }

    private void releaseLock() {
        mLockManager.releaseLock(mLockToken);
    }

    @Override
    public String requestUserData(String string) {
        Log.i(TAG, "requestUserData: " + string);
        Request userData = Request.create(string, Request.class);
        if (userData == null) {
            Log.e(TAG, "requestUserData: wrong format");
            return FAILED_JSON;
        }
        String result = userData.request();
        Log.i(TAG, "returnData: " + result);
        return result;
    }

    @Override
    public String requestData(String string) {
        Log.i(TAG, "requestData: " + string);
        Request materialData = Request.create(string, Request.class);
        if (materialData == null) {
            Log.e(TAG, "requestData: wrong format");
            return FAILED_JSON;
        }
        String result = materialData.request();
        Log.i(TAG, "returnData: " + result);
        return result;
    }

    @Override
    public void showExitDialog() {
        if (mUIInterface != null)
            mUIInterface.showExitDialog();
    }

    @Override
    public void log(int priority, String tag, String msg) {
    }

    @Override
    public void onReady() {
        if (mUIInterface != null)
            mUIInterface.onReady();
    }

    @Override
    public boolean autoSync() {
        boolean sync = timeToSync();
        if (sync)
            sync();
        return sync;
    }

    @Override
    public void sync() {
        if(mIsSynchronizing) {
            Log.w(TAG, "duplicate sync");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mIsSynchronizing = true;
                mObserver.onSyncStart();
                SubscriptionRequest sq = new SubscriptionRequest(ApiUriBuilder.getUriFromPath(PATH));
                SubscriptionManager sm = ApiManager.getInstance(ExerciseApplication.getInstance().getBaseContext())
                        .getSubscriptionManager();
                mSubscription = sm.getOrCreateNewSubscription(sq);

                mIsSynchronizing = sm.fetchData(mSubscription, Proxy.this);
                if(!mIsSynchronizing) {
                    Log.w(TAG, "fetchData isn't accepted.");
                    if (mObserver != null)
                        mObserver.onSyncCompleted(mIsSynchronizing);
                }
            }
        }).start();
    }

    @Override
    public void download(String collectionId) {
        new ExerciseOperation().updateDownloadStatusById(Lessons.CONTENT_URI, collectionId, DOWNLOAD_STATUS.WAITING);
        if (!isSynchronizing())
            sync();
    }

    @Override
    public ArrayList<String> getCurrentDownloadingCollectionIDs() {
        return null;
    }

    @Override
    public boolean isSynchronizing() {
//        throw new RuntimeException();
        return mIsSynchronizing;
    }

    @Override
    public boolean isJsonParsing() {
        throw new RuntimeException();
//        return false;
    }

    @Override
    public void register(SyncObserver so) {
        mObserver = so;
    }

    @Override
    public void remove(SyncObserver so) {
        if (mObserver.equals(so)) {
            mObserver = null;
        }
    }

    @Override
    public void store(SyncObserver so) {
        throw new RuntimeException();
    }

    @Override
    public boolean dataChanged(SyncObserver so) {
        throw new RuntimeException();
    }

    @Override
    public int deleteUnusedFiles() {
        return new DeleteUnusedFilesRequest().request();
    }

    @Override
    public void onDataReceived(Subscription subscription, JSONObject object) throws Exception {
        acquireLock();
        Log.d(TAG, "onDataReceived");
        if (subscription.getID() == mSubscription.getID()) {
            Log.d(TAG, "JSON" + object.toString());
            mObserver.onJsonReceived();
            boolean succeed = JsonHandler.parse(object);
            if (succeed) {
                CheckAvailableOperation co = new CheckAvailableOperation();
                co.addAll();
                // 接在Json数据处理完成后执行，执行后直接通知前端，必须同步执行。
                co.execute();
            }
            mObserver.onJsonParsed();
            if(!succeed){
                throw new Exception("json parse error");
            }
        } else {
            Log.e(TAG, "mismatch subscription ID");
        }
    }

    @Override
    public void onSyncComplete(Subscription subscription, boolean isSuccess) {
        mObserver.onSyncCompleted(isSuccess);
        mIsSynchronizing = false;
        ExerciseOperation op = new ExerciseOperation();
        ArrayList<String> lessonIDs = op.getIDsByDownloadStatus(Lessons.CONTENT_URI, DOWNLOAD_STATUS.WAITING);
        DownloadLessonRequest dr = new DownloadLessonRequest(lessonIDs);
        dr.start();
        op.updateDownloadStatusByStatus(Lessons.CONTENT_URI, DOWNLOAD_STATUS.WAITING, DOWNLOAD_STATUS.DOWNLOADING);
        releaseLock();
    }

    public void notifyCollectionDownloadProgress(String collectionId, float percentage) {
        mObserver.onCollectionDownloadProgress(collectionId, percentage);
    }

    public void notifyCollectionDownloaded(String collectionId, boolean available) {
//        Cursor cursor = ExerciseApplication.getInstance().getContentResolver().query(Lessons.CONTENT_URI,
//                new String[]{Lessons._DOWNLOAD_FINISH}, Lessons._STRING_ID + "=?", new String[]{Lessons._STRING_ID},
//                null);
//        cursor.moveToFirst();
//        int status = CursorUtils.getInt(cursor, Lessons._DOWNLOAD_FINISH);
//        cursor.close();
        mObserver.onCollectionDownloaded(collectionId, available);
        if (!isSynchronizing()) {
            int count = deleteUnusedFiles();
            Log.i(TAG, "delete " + count + " files");
        }
    }

    static protected boolean timeToSync() {
        return timeToSync(SP_SYNC, SP_LAST_TIME, DURATION);
    }

    static protected boolean timeToSync(String name, String key, int duration) {
        SharedPreferences sharedPreferences = ApplicationInterface.getInstance().getSharedPreferences(name,
                Context.MODE_PRIVATE);
        long now = System.currentTimeMillis();
        long syncTime = sharedPreferences.getLong(key, 0);
        if (syncTime == 0) {
            ApiManager.getInstance(ExerciseApplication.getInstance().getBaseContext()).getSubscriptionManager().reset();
            updateSyncTime(1);
        }
        return ( syncTime > now || now - syncTime >= duration );
    }

    static public void updatedSyncTime() {
        SharedPreferences sharedPreferences = ExerciseApplication.getInstance().getSharedPreferences(SP_SYNC,
                Context.MODE_PRIVATE);
        long now = System.currentTimeMillis();
        long syncTime = sharedPreferences.getLong(SP_LAST_TIME, 0);
        long unique = syncTime % DURATION;
        long expected = now - now % DURATION + unique;

        if (syncTime != expected) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putLong(SP_LAST_TIME, expected);
//            editor.commit();
            updateSyncTime(expected);
        }
    }

    static public void updateSyncTime(long time) {
        SharedPreferences sharedPreferences = ExerciseApplication.getInstance().getSharedPreferences(SP_SYNC,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SP_LAST_TIME, time);
        editor.commit();
    }



    @Override
    public void setUIInterface(AndroidUIInterface a) {
        mUIInterface = a;
    }
}
