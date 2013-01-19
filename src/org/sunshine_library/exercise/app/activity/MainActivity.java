package org.sunshine_library.exercise.app.activity;

import org.sunshine_library.exercise.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends TopActivity {
	private static final String TAG = "MainActivity";
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void openVideoActivity(View v){
    	startActivity(new Intent(this, VideoActivity.class));
    }
}
