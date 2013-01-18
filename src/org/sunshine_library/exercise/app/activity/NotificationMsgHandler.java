package org.sunshine_library.exercise.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class NotificationMsgHandler extends Handler{
	private MainActivity mMainActivity;
	
	public NotificationMsgHandler(MainActivity pMainActivity){
		mMainActivity = pMainActivity;
	}
	
	public NotificationMsgHandler(Looper looper) {
		super(looper);
	}
	
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String notiMsg = bundle.getString("NotificationMsg");
		if (notiMsg == "Notify") {
			mMainActivity.initNotificationBar();
		}
	}
}
