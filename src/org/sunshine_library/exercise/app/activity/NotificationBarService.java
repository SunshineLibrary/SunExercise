package org.sunshine_library.exercise.app.activity;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class NotificationBarService extends Service implements Runnable{
	
	private static final String TAG_STRING = "NotificationBarService";

    private Handler notificationHandler;
	private Time nowTime;
	
	public NotificationBarService(Handler handler) {
		// TODO Auto-generated constructor stub
		notificationHandler = handler;
	}
	
	public IBinder onBind(Intent intent){
		return null;
	}
	
	public void onCreate(){
		super.onCreate();
		
		Log.d(TAG_STRING, "on create");
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		
		nowTime = new Time();
		nowTime.setToNow();
		
	}

		
	public void run(){
		while(true){
			Log.d(TAG_STRING, "loop");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (nowTime.hour == 11 && nowTime.minute == 40) {
				Log.d(TAG_STRING, "run");
				Message notificationMsg = new Message();	
				Bundle b = new Bundle();
				b.putString("NotificationMsg", "Notify");
				notificationMsg.setData(b);
				notificationHandler.sendMessage(notificationMsg);
			}
		}
	}
	
	public void onStart(Intent intent, int started){
		Log.d(TAG_STRING, "on Start");
		
	}
	
	public void onDestory(){
		super.onDestroy();
		Log.d(TAG_STRING, "on destory");
	}

	
}



