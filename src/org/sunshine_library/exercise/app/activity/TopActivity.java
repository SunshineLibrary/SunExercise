package org.sunshine_library.exercise.app.activity;

import android.app.Activity;
import android.view.KeyEvent;

public class TopActivity extends Activity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //your stuff if you wanna to have anything
            return true;
        }
        else
            return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            //your stuff if you wanna to have anything
            return true;
        }
        else
            return super.onKeyUp(keyCode, event);
    }

}
