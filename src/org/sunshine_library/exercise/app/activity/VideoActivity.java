package org.sunshine_library.exercise.app.activity;

import android.os.Bundle;
import org.sunshine_library.exercise.R;


public class VideoActivity extends TopActivity {
	private static final String TAG = "VideoActivity";
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
    }
}
