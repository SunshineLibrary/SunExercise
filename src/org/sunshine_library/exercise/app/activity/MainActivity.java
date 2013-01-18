package org.sunshine_library.exercise.app.activity;

import org.sunshine_library.exercise.R;

import android.app.Activity;
<<<<<<< HEAD
=======
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
>>>>>>> 2b97e16ccf78608318dad6b131b35b957e0a39d3
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class MainActivity extends Activity {
	private  final static String TAG_MAINACTIVITY = "MainActivity";
	private WebView content;
	
	private NotificationManager notificationManager;
	private Notification notificationBar;
	private PendingIntent pendingIntent;
	
	private NotificationBarService mNotificationBarService;
	public static NotificationMsgHandler notificationMsgHandler;
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        notificationMsgHandler = new NotificationMsgHandler(this);
        
        mNotificationBarService = new NotificationBarService(notificationMsgHandler);
        
        new Thread(mNotificationBarService).run();
        
        
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
        
       
    }
<<<<<<< HEAD
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	    startService(new Intent(MainActivity.this, NotificationService.class));
	}
    
    
}
=======
    
    public void initNotificationBar(){
    	
    	 notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
		 notificationBar = new Notification();
		 Intent intent = new Intent();
		 pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
		 
		// notificationBar.icon = R.drawable.icon;
		 notificationBar.tickerText = "clicked";
		 notificationBar.defaults |= Notification.DEFAULT_SOUND;  
		 notificationBar.defaults |= Notification.DEFAULT_VIBRATE;  
		 notificationBar.defaults |= Notification.DEFAULT_LIGHTS;	
		 notificationBar.flags |= Notification.FLAG_INSISTENT; 
		 notificationBar.flags |= Notification.FLAG_AUTO_CANCEL; 
		 
		 notificationBar.setLatestEventInfo(MainActivity.this, "hello", "world", pendingIntent);
		 notificationManager.notify(0,notificationBar);
    }
 }
//   private void init()
//   {
//	   button_1 = (Button)findViewById(R.id.button_1);
//	   button_1.setOnClickListener(onClickListener);
//	   notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
//	   Intent intent = new Intent();
//	   pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//   }
//   
//   OnClickListener onClickListener = new OnClickListener() {
//	private int Notification_ID_BASE = 110;
//	private Notification baseNF;
//	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		baseNF = new Notification();
//		
//		baseNF.icon = R.drawable.icon;
//		baseNF.tickerText = "clicked";
//		
//		baseNF.defaults |= Notification.DEFAULT_SOUND;  
//		baseNF.defaults |= Notification.DEFAULT_VIBRATE;  
//		baseNF.defaults |= Notification.DEFAULT_LIGHTS;
//		
//		baseNF.flags |= Notification.FLAG_INSISTENT; 
//		baseNF.flags |= Notification.FLAG_AUTO_CANCEL; 
//		
//		baseNF.setLatestEventInfo(MainActivity.this, "hello", "world", pendingIntent);
//		notificationManager.notify(Notification_ID_BASE, baseNF);
//		
//	}
    
   
    








>>>>>>> 2b97e16ccf78608318dad6b131b35b957e0a39d3
