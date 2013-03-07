package org.sunshine_library.exercise.metadata.sync;

import android.content.Context;
import android.content.IntentFilter;
import org.json.JSONObject;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.ApiUriBuilder;
import org.sunshinelibrary.support.api.subscription.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-1
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public class SyncSubscription {


    public static final String URL = "/api/exercise/updates.json";


    SubscriptionDataListener mDataListener;
    SubscriptionBroadcastReceiver mReceiver;
    Context context;
    SubscriptionManager mManager;
    String mSubsciptionUri;


    public SyncSubscription() {
        this.context = ExerciseApplication.getApplication();
        mManager = ApiManager.getInstance(context).getSubscriptionManager();
        this.mSubsciptionUri = URL;
        initComponents();
    }


    public void sync() {
        fetchExerciseData();
    }


    private void initComponents() {
        mDataListener = new SubscriptionDataListener() {

            @Override
            public void onDataReceived(Subscription subscription, JSONObject object) {
                new Sync().sync(object);
            }

            @Override
            public void onSyncComplete(Subscription subscription, boolean isSuccess) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };


        mReceiver = new SubscriptionBroadcastReceiver() {
            @Override
            public void onUpdateAvailable(SubscriptionManager subscriptionManager, Subscription subscription) {

                subscriptionManager.fetchData(subscription, mDataListener);
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(SubscriptionBroadcastReceiver.ACTION_SUBSCRIPTION_UPDATE);
        context.registerReceiver(mReceiver, filter);
    }


    private void fetchExerciseData() {
        Subscription subscription = mManager.getOrCreateNewSubscription(
                new SubscriptionRequest(ApiUriBuilder.getUriFromPath(mSubsciptionUri)));
        mManager.fetchData(subscription, mDataListener);
    }


}







