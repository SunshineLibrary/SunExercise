package org.sunshinelibrary.exercise.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import org.sunshinelibrary.exercise.R;



/**
 * Top Activity for all activities used in SunExercise
 * 
 * @author Tianzi Hou
 *
 */
public class TopActivity extends Activity {
	private static final String TAG = "TopActivity";

	private static final int DIALOG_EXIT_MAIN   = 0;
	private static final int DIALOG_EXIT_NORMAL = 1;
	
	/**
	 * Check whether the current intent is the main intent
	 * 
	 * @return true if it is the main intent, otherwise false
	 */
	private boolean isMainIntent() {
		return getIntent().getAction() != null && 
					getIntent().getAction().equals(Intent.ACTION_MAIN);
	}
	
	@Override
	public void onBackPressed() {
		/* show dialog based on the current intent type */
		showDialog(isMainIntent() ? DIALOG_EXIT_MAIN : DIALOG_EXIT_NORMAL);
	}

	@Override
	public Dialog onCreateDialog(int id) {
		
		switch (id) {
			case DIALOG_EXIT_MAIN:
				return new AlertDialog.Builder(this)
			        		.setMessage(R.string.dialog_exit_main_message)
			        		.setPositiveButton(R.string.dialog_exit_main_btn_ok, new DialogInterface.OnClickListener() {
				                    public void onClick(DialogInterface dialog, int id) {
				                    	//execute the back key pressed operation only if
				                    	//the OK button is pressed.
				                    	TopActivity.super.onBackPressed();
				                    }
			                	})
			        		.setNegativeButton(R.string.dialog_exit_main_btn_cancel, null)
			        		.create();
			case DIALOG_EXIT_NORMAL: 
				return new AlertDialog.Builder(this)
				    		.setMessage(R.string.dialog_exit_normal_message)
				    		.setPositiveButton(R.string.dialog_exit_normal_btn_ok, new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog, int id) {
			                    		//execute the back key pressed operation only if
			                    		//the OK button is pressed.
			    						TopActivity.super.onBackPressed();
				                    }
			                	})
				    		.setNegativeButton(R.string.dialog_exit_normal_btn_cancel, null)
				    		.create();
			default:
				return null;
		}
	}
}
