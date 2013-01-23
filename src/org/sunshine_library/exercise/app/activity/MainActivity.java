package org.sunshine_library.exercise.app.activity;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sunshine_library.exercise.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.View.OnClickListener;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.sunshine_library.exercise.app.interfaces.HtmlInterface;

import java.util.Iterator;


public class MainActivity extends Activity implements OnClickListener{
	private WebView content;
    /**
     * Called when the activity is first created.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        try {
            String jsonString = "{\"FDA\" : 2, \"Fad\" : 3}";
            JSONObject temp = new JSONObject(jsonString);
            for(Iterator iter = temp.keys(); iter.hasNext(); ){
                Log.d("TAG", String.valueOf(iter.next().getClass()));

            }


        }catch (Exception e){
            e.printStackTrace();
        }


        content = (WebView) findViewById(R.id.content);
        WebSettings settings = content.getSettings();
        settings.setJavaScriptEnabled(true);
        content.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

        });


        content.loadUrl("file:///android_asset/index.html");
        
        content.addJavascriptInterface(new MyHtmlInterface(), "android");
    }

    public void openVideoActivity(View v){
    	startActivity(new Intent(this, VideoActivity.class));
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		}
	}
	
	private class MyHtmlInterface implements HtmlInterface{

		@Override
		public void getProblems() {
			// TODO Auto-generated method stub
			content.loadUrl("file:///android_asset/quiz.html");
		}

		@Override
		public void getResults() {
			// TODO Auto-generated method stub
			content.loadUrl("file:///android_asset/summary.html");
		}
		
	}
  }
