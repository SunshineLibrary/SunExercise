package org.sunshine_library.exercise.metadata.sync;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONObject;
import org.sunshine_library.exercise.R;
import org.sunshine_library.exercise.app.application.ExerciseApplication;
import org.sunshine_library.exercise.metadata.data.DataReceiver;
import org.sunshinelibrary.support.api.ApiManager;
import org.sunshinelibrary.support.api.ApiUriBuilder;
import org.sunshinelibrary.support.api.subscription.*;

/**
 * Created with IntelliJ IDEA.
 * User: linxiangyu
 * Date: 13-2-1
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
public class SyncSubscription {

    SubscriptionDataListener mDataListener;
    SubscriptionBroadcastReceiver mReceiver;
    Context context;
    SubscriptionManager mManager;
    String mSubsciptionUri;


    public SyncSubscription(String uri) {
        this.context = ExerciseApplication.getApplication();
        mManager = ApiManager.getInstance(context).getSubscriptionManager();
        this.mSubsciptionUri = uri;
        initComponents();
    }



   public void sync(){
       fetchExerciseData();
   }


    private void initComponents() {
        mDataListener = new SubscriptionDataListener() {

            @Override
            public void onDataReceived(Subscription subscription, JSONObject object) {
                new DataReceiver().onReceive(object.toString());
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







