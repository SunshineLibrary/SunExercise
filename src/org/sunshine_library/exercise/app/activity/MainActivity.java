package org.sunshine_library.exercise.app.activity;

import android.content.ContentValues;
import android.renderscript.Program;
import android.renderscript.ProgramStore;
import android.util.Log;
import org.apache.http.util.EncodingUtils;
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
import org.sunshine_library.exercise.metadata.MetadataContract;
import org.sunshine_library.exercise.metadata.data.DataBack;
import org.sunshine_library.exercise.metadata.data.DataReceiver;
import org.sunshine_library.exercise.metadata.sync.Sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;


public class MainActivity extends Activity implements OnClickListener{
	private WebView content;
    /**
     * Called when the activity is first created.
     */



    //读数据
    public String readFile(String fileName) throws IOException{
        String res="";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


       // new Sync().syncTest();

        try {
           //String jsonString = readFile("data.txt");
           //new DataReceiver().onReceive(jsonString);

          String jsonString = readFile("updata.json");
          new DataBack().BackData(jsonString);


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
