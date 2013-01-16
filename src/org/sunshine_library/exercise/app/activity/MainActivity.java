package org.sunshine_library.exercise.app.activity;

import org.sunshine_library.exercise.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;


public class MainActivity extends Activity {
	private WebView content;
	
	private Button button_1;
	private NotificationManager notificationManager;
	private PendingIntent pendingIntent;
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        content = (WebView) findViewById(R.id.content);
        WebSettings settings = content.getSettings();
        settings.setJavaScriptEnabled(true);
        content.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
        	
        });
        
        content.loadUrl("file:///android_asset/index.html");
        
        init();
        //initNotify();
    }
    
   private void init()
   {
	   button_1 = (Button)findViewById(R.id.button_1);
	   button_1.setOnClickListener(onClickListener);
	   notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
	   Intent intent = new Intent();
	   pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
   }
   
   OnClickListener onClickListener = new OnClickListener() {
	private int Notification_ID_BASE = 110;
	private Notification baseNF;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		baseNF = new Notification();
		
		baseNF.icon = R.drawable.icon;
		baseNF.tickerText = "clicked";
		
		baseNF.defaults |= Notification.DEFAULT_SOUND;  
		baseNF.defaults |= Notification.DEFAULT_VIBRATE;  
		baseNF.defaults |= Notification.DEFAULT_LIGHTS;
		
		baseNF.flags |= Notification.FLAG_INSISTENT; 
		baseNF.flags |= Notification.FLAG_AUTO_CANCEL; 
		
		baseNF.setLatestEventInfo(MainActivity.this, "hello", "world", pendingIntent);
		notificationManager.notify(Notification_ID_BASE, baseNF);
		
	}
};
    

	public void initNotify(){
		String ns = Context.NOTIFICATION_SERVICE;

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        //定义通知栏展现的内容信息

        int icon = R.drawable.icon;

        CharSequence tickerText = "我的通知栏标题";

        long when = System.currentTimeMillis();

        Notification notification = new Notification();

      

        //定义下拉通知栏时要展现的内容信息

        Context context = getApplicationContext();

        CharSequence contentTitle = "我的通知栏标展开标题";

        CharSequence contentText = "我的通知栏展开详细内容";

       
       // Notification noti = new Notification.Builder(context)
       // .setContentTitle("我的通知栏标展开标题")
       // .setContentText("我的通知栏展开详细内容")
       // .setSmallIcon(R.drawable.icon)
       // .build();
        
        Intent notificationIntent = new Intent(this, MainActivity.class);

       PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent); 

      //  notification.when = 8000;
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知

      
        mNotificationManager.notify(1, notification);
	
	}

}












