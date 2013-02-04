package org.sunshine_library.exercise.app.activity;

import org.sunshine_library.exercise.R;

import android.app.Notification;


public class NotificationBuilder {
	private Notification noti;
	
	public NotificationBuilder(){
		noti = new Notification();
		setByDefualt();
	}
	
	Notification getNotification(){
		return noti;
	}
	
	public void setByDefualt(){
		noti.icon = R.drawable.ic_notification;
		//noti.tickerText = "阳光书屋";
		noti.flags |= Notification.FLAG_AUTO_CANCEL;  
	}
	
	public void setByCustomer(){
		
	}
	
	
}
