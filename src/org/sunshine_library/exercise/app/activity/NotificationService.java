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

	private Timer timer;
	static Handler handler;

	public void onCreate(){
		super.onCreate();

		timer = new Timer(true);



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

				Log.d("chenhao", "nowTime = "+ nowTime.hour + ":" + nowTime.minute + ":" + nowTime.second);

				if (nowTime.hour == 12 && nowTime.minute == 00) 
				{
					Message notificationMsg = new Message();	
					Bundle b = new Bundle();
					b.putString("NotificationMsg", "Notify");
					notificationMsg.setData(b);
					handler.sendMessage(notificationMsg);
				}
			}
		}, new Date(), 2*10*1000);

	}

	public void notificationBar(){
		Intent intent = new Intent(NotificationService.this, MainActivity.class);
		PendingIntent pd = PendingIntent.getActivity(NotificationService.this, 0, intent, 0);
		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification nf = new Notification();
		nf.icon = R.drawable.ic_notification;
		nf.tickerText = "hello";
		nf.flags |= Notification.FLAG_AUTO_CANCEL;  
		nf.setLatestEventInfo(NotificationService.this, "hello", "world", pd);
		mgr.notify(0, nf);
	}
}