package org.sunshinelibrary.exercise.app.service;

import org.sunshinelibrary.exercise.R;

import android.app.Notification;


public class NotificationBuilder {
	private Notification noti;
	
	public NotificationBuilder(){
		noti = new Notification();
		setByDefualt();
	}
	
	public Notification getNotification(){
		return noti;
	}
	
	public void setByDefualt(){
		noti.icon = R.drawable.ic_notification;
		noti.flags |= Notification.FLAG_AUTO_CANCEL;  
	}
	
	public void setByCustomer(){
		
	}
	
	
}
