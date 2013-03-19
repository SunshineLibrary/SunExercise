package org.sunshinelibrary.exercise.metadata.sync;

import android.content.Context;
import android.content.SharedPreferences;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.utils.ApplicationInterface;
import org.sunshinelibrary.support.utils.sync.StateMachine;
import org.sunshinelibrary.support.utils.sync.SyncObserver;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 3/10/13
 * Trello:
 */
public class ExerciseDeprecatedSyncManager {}
//public class ExerciseDeprecatedSyncManager implements StateMachine {
//    public static final String UPDATE = "/api/exercise/updates.json";
//
//    static private final String SP_SYNC = "sync";
//    static private final String SP_LAST_TIME = "last_time";
//    static private final int DURATION = 2*60*60*1000;//2*60*60*1000;  // 2 hours
//
//    ArrayList<SyncObserver> mObservers;
//    ArrayList<SyncObserver> mClients;
//
//    ArrayList<String> mWaitingSyncArray = new ArrayList<String>();
//    ArrayList<String> mWaitingDownloadArray = new ArrayList<String>();
//
//    volatile boolean mJSON = false;
//    volatile boolean mSync = false;
//
//    @Override
//    public boolean autoSync() {
//        boolean sync = timeToSync();
//        if (sync)
//            sync();
//        return sync;
//    }
//
//    @Override
//    public void sync() {
//        SyncRequest syncRequest = new SyncRequest(ApiManager.getInstance(ExerciseApplication.getInstance()
//                .getBaseContext()), UPDATE);
//        syncRequest.subscribe();
//    }
//
//    @Override
//    public void download(String collectionId) {
//        mWaitingSyncArray.add(collectionId);
//        if(!isSynchronizing())
//            sync();
//    }
//
//    @Override
//    public ArrayList<String> getCurrentDownloadingCollectionIDs() {
//        ArrayList<String> a = new ArrayList<String>();
//        a.addAll(mWaitingDownloadArray);
//        a.addAll(mWaitingSyncArray);
//        return a;
//    }
//
//    @Override
//    public boolean isSynchronizing() {
//        return mJSON;
//    }
//
//    @Override
//    public boolean isJsonParsing() {
//        return mSync;
//    }
//
//    @Override
//    public void register(SyncObserver so) {
//        mObservers.add(so);
//    }
//
//    @Override
//    public void remove(SyncObserver so) {
//        mObservers.remove(so);
//    }
//
//    public void notifyJsonReceived() {
//        for (SyncObserver so : mObservers) {
//            so.onJsonReceived();
//        }
//    }
//
//    public void notifyJsonParsed() {
//        for (SyncObserver so : mObservers) {
//            so.onJsonParsed();
//        }
//    }
//
//    public void notifySyncCompleted(boolean isSuccess) {
//        mJSON = false;
//        mSync = false;
//        if (isSuccess) {
//            downloadImpl(mWaitingSyncArray);
//            mWaitingDownloadArray.addAll(mWaitingSyncArray);
//            mWaitingSyncArray.clear();
//        }
//        for (SyncObserver so : mObservers) {
//            so.onSyncCompleted(isSuccess);
//        }
//    }
//
//    public void notifySyncStart() {
//        for (SyncObserver so : mObservers) {
//            so.onSyncStart();
//        }
//    }
//
//    public void notifyCollectionDownloaded(String collectionId) {
//        mWaitingDownloadArray.remove(collectionId);
//        updateTimeStamp();
//        for (SyncObserver so : mObservers) {
//            so.onCollectionDownloaded(collectionId);
//        }
//    }
//
//    @Override
//    public void store(SyncObserver so) {
//        mClients.add(so);
//    }
//
//    @Override
//    public boolean dataChanged(SyncObserver so) {
//        if(mClients.contains(so)) {
//            mClients.remove(so);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public int deleteUnusedFiles() {
//        return new DeleteUnusedFilesRequest().request();
//    }
//
//    protected void updateTimeStamp() {
//        mClients.clear();
//    }
//
//    public void downloadImpl(ArrayList<String> collectionIDs) {
//        DownloadLessonRequest dr = new DownloadLessonRequest(collectionIDs);
//        dr.start();
//    }
//
//    static protected void updatedSyncTime() {
//        updatedSyncTime(SP_SYNC, SP_LAST_TIME, DURATION);
//    }
//
//    static protected boolean timeToSync() {
//        return timeToSync(SP_SYNC, SP_LAST_TIME, DURATION);
//    }
//
//    static protected void updatedSyncTime(String name, String key, int duration) {
//        SharedPreferences sharedPreferences = ApplicationInterface.getInstance().getSharedPreferences(name,
//                Context.MODE_PRIVATE);
//        long now = System.currentTimeMillis();
//        long syncTime = sharedPreferences.getLong(key, 0);
//        long unique;
//        if(syncTime == 0) {
//            unique = new Random().nextInt(duration);
//            ApiManager.getInstance(ApplicationInterface.getInstance().getBaseContext())
//                    .getSubscriptionManager().reset();
//        } else {
//            unique = syncTime % (long)duration;
//        }
//        long expected = now - now % (long)duration + unique;
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putLong(key, expected);
//        editor.commit();
//    }
//
//    static protected boolean timeToSync(String name, String key, int duration) {
//        SharedPreferences sharedPreferences = ApplicationInterface.getInstance().getSharedPreferences(name,
//                Context.MODE_PRIVATE);
//        long now = System.currentTimeMillis();
//        long syncTime = sharedPreferences.getLong(key, 0);
//        return ( syncTime > now || now - syncTime >= duration );
//    }
//}
