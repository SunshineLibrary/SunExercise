package org.sunshine_library.exercise.app.activity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.sunshine_library.exercise.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;

public class NotificationService extends Service{
	private static final String NOTI_SER_STRING = "NotiService";
	private static int count = 0;
	private Timer timer;
	private NotificationBuilder notificationBuilder;
	private NotificationManager notiMgr;
	static Handler handler;

    private static int requestCode = 0;

	public void onCreate(){
		super.onCreate();
		timer = new Timer(true);
		
		notiMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationBuilder = new NotificationBuilder();
		
		handler = new Handler(){
			public void handleMessage(Message msg) {
				if (msg.getData().getString("NotificationMsg") == "Notify") {
					notificationBar();
				}
			}
		};
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//super.onStart(intent, startId);
		Log.d("chenhao", "start service");
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Time nowTime = new Time();
				nowTime.setToNow();

				Log.d(NOTI_SER_STRING, "nowTime = "+ nowTime.hour + ":" + nowTime.minute + ":" + nowTime.second);

				if (nowTime.hour == 12 && nowTime.minute == 00) 
				{
					Log.d(NOTI_SER_STRING, "noti");
					Message notificationMsg = new Message();	
					Bundle b = new Bundle();
					b.putString("NotificationMsg", "Notify");
					notificationMsg.setData(b);
					handler.sendMessage(notificationMsg);
				}
			}
		}, new Date(), 60*1000);

	}

	public void notificationBar(){
		Intent intent = new Intent(NotificationService.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pd = PendingIntent.getActivity(NotificationService.this, ++requestCode, intent, 0);
		
		notificationBuilder.getNotification().tickerText = getString(R.string.notiTickerText);
		notificationBuilder.getNotification().setLatestEventInfo(
				NotificationService.this, getString(R.string.notiTitle), getString(R.string.notiSubTitle), pd);
		notiMgr.notify(count++, notificationBuilder.getNotification());
	}
}