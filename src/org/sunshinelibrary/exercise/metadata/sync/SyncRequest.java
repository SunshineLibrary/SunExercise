package org.sunshinelibrary.exercise.metadata.sync;

import android.util.Log;
import org.json.JSONObject;
import org.sunshinelibrary.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.exercise.metadata.operation.CheckAvailableOperation;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.ApiUriBuilder;
import org.sunshinelibrary.support.api.subscription.Subscription;
import org.sunshinelibrary.support.api.subscription.SubscriptionDataListener;
import org.sunshinelibrary.support.api.subscription.SubscriptionManager;
import org.sunshinelibrary.support.api.subscription.SubscriptionRequest;
import org.sunshinelibrary.support.utils.json.JsonHandler;

/**
 * Created with IntelliJ IDEA.
 * User: linuo
 * Date: 1/24/13
 * Trello:
 */
public class SyncRequest {}
//public class SyncRequest extends Thread implements SubscriptionDataListener {
//    private static final String TAG = "SyncRequest";
//
//    ApiManager mApiManager;
//    Subscription mSubscription;
//    String mPath;
//
//    public SyncRequest(ApiManager apiManager, String path) {
//        mApiManager = apiManager;
//        mPath = path;
//    }
//
//    public void subscribe(){
//        start();
//        }
//
//    @Override
//    public void run() {
//        super.run();
//        Log.i(TAG, "run so far");
//        ExerciseApplication.getInstance().getSyncManager().notifySyncStart();
//
//        SubscriptionRequest sq = new SubscriptionRequest(ApiUriBuilder.getUriFromPath(mPath));
//        SubscriptionManager sm = mApiManager.getSubscriptionManager();
//        mSubscription = sm.getOrCreateNewSubscription(sq);
//
//        boolean ret = sm.fetchData(mSubscription, this);
//        if(!ret) {
//            Log.w(TAG, "fetch Data not received");
//            ExerciseApplication.getInstance().getSyncManager().notifySyncCompleted(false);}
//        }
//
//    public void onDataReceived(Subscription subscription, JSONObject object) throws Exception{
//        if (subscription.getID() == mSubscription.getID()) {
//            ExerciseDeprecatedSyncManager sm = ExerciseApplication.getInstance().getSyncManager();
//            sm.notifyJsonReceived();
//
//            boolean succeed = JsonHandler.parse(object);
//
//            if (succeed) {
//                CheckAvailableOperation co = new CheckAvailableOperation();
//                co.addAll();
//                // 接在Json数据处理完成后执行，执行后直接通知前端，必须同步执行。
//                co.execute();
//            }
//            sm.updateTimeStamp();
//            sm.notifyJsonParsed();
//
//            if(!succeed){
//                throw new Exception("json parse error");
//            }
//}
//    }
//
//    public void onSyncComplete(Subscription subscription, boolean isSuccess) {
//        Log.i(TAG, "onSyncComplete");
//        if(isSuccess)
//            ExerciseApplication.getInstance().getSyncManager().updatedSyncTime();
//        ExerciseApplication.getInstance().getSyncManager().notifySyncCompleted(isSuccess);
//    }
//}
