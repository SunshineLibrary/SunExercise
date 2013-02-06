package org.sunshine_library.exercise.app.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import org.sunshine_library.exercise.R;
import org.sunshine_library.exercise.app.interfaces.HtmlInterface;
import org.sunshine_library.exercise.app.service.NotificationService;

import java.util.HashMap;


public class MainActivity extends TopActivity {

    private static final String TAG = "Main";
    private static final String ASSETS = "file:///android_asset/";
    private static final String HTML = ".html";

    private HashMap<String, WebView> mWebViewCollection;
	private VideoView mVideoView;
    private String mCurrentView = null;
    private RelativeLayout mPlayView;
	private LinearLayout mLinearLayout;
    private HtmlInterface mInterface;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mWebViewCollection = new HashMap<String, WebView>();

    	mVideoView = (VideoView) findViewById(R.id.vv);
		mLinearLayout =(LinearLayout)findViewById(R.id.player_loading);
		mPlayView=(RelativeLayout) findViewById(R.id.play);


        mWebViewCollection.put("index", (WebView)findViewById(R.id.lesson));
        mWebViewCollection.put("stages", (WebView)findViewById(R.id.stage));
//        mWebViewCollection.put("section", (WebView)findViewById(R.id.lesson));
        mWebViewCollection.put("exercise", (WebView)findViewById(R.id.exercise));
        mWebViewCollection.put("quiz", (WebView)findViewById(R.id.quiz));
        mWebViewCollection.put("summery", (WebView) findViewById(R.id.summery));
        mInterface = new MyHtmlInterface();

        initWebViews();
        activateWebView("index");
        startService(new Intent(MainActivity.this, NotificationService.class));
    }

    public void openVideoActivity(View v){
        mWebViewCollection.get("index").loadUrl("http://m.youku.com/smartphone/channels?cid=92");
    }

    public void activateWebView(String s){
        WebView previousView = mWebViewCollection.get(mCurrentView);
        if(previousView != null)
            previousView.setVisibility(View.GONE);
        mCurrentView = s;
        WebView currentView = mWebViewCollection.get(mCurrentView);
        currentView.setVisibility(View.VISIBLE);

        if(s.equals("index")){
            currentView.loadUrl("javascript:Sun.fetchSubjects(\"showSubjects\")");
        }

    }

    private void initWebViews(){
        WebChromeClient wcc = new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        };

        WebViewClient wvc = new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {
                if (url.contains("3gp") || url.contains("mp4")) {
                    playView(Uri.parse(url));
                } else {
                    view.loadUrl(url);
                }

                return true;
            }// 重写点击动作,用webview载入
        };

        for (String name : mWebViewCollection.keySet()) {
            WebView webView = mWebViewCollection.get(name);
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            webView.setWebChromeClient(wcc);

            // 过滤调用第三方浏览器。并且解析视频网站播放地址，传给播放器
            webView.setWebViewClient(wvc);
            webView.loadUrl(ASSETS+name+HTML);

            webView.addJavascriptInterface(mInterface, "android");
        }
    }
	
	// 播放器的效果
	public void playView(Uri uri) {

		mPlayView.setVisibility(View.VISIBLE);
		mWebViewCollection.get(mCurrentView).setVisibility(View.GONE);

		// 初始化视频显示控件
		if (uri != null) {

			if (mVideoView != null) {
				// 加载播放地址
				mVideoView.setVideoURI(uri);
			}
		}
		if (mVideoView != null)
			mVideoView.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					if (mVideoView != null) {
						// 开始播放
						mVideoView.start();
						mLinearLayout.setVisibility(View.GONE);
					}

				}
			});
		if (mVideoView != null) {
			// 显示控制栏
			mVideoView.setMediaController(new MediaController(this));
		}

		// 监听播放完的事件。
		mVideoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mVideoView.stopPlayback();
				mPlayView.setVisibility(View.GONE);
                mWebViewCollection.get(mCurrentView).setVisibility(View.VISIBLE);
			}
		});
	}
	
	// 捕捉返回键，首先隐藏播放器，然后判断是否能够返回。
    public void onBackPressed() {
        Log.i(TAG, "in" + mPlayView.getVisibility() + mCurrentView);
        if (mPlayView.getVisibility() == View.VISIBLE) {
            mVideoView.stopPlayback();
            mPlayView.setVisibility(View.GONE);
            mWebViewCollection.get(mCurrentView).setVisibility(View.VISIBLE);
            return;
        }
        if (mCurrentView != null) {
            if ("index".equals(mCurrentView)) {
                // nothing
            } else {
                activateWebView("index");
                return;
            }
        }
        super.onBackPressed();
    }

	private class MyHtmlInterface implements HtmlInterface{

        @Override
        public String requestJson(String reqJson) {
            // 非UI线程
            Log.i(TAG, "request Json" + reqJson);
            return "hahaha";
        }

        @Override
        public void loadHtml(final String page) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activateWebView(page);
                }
            });
        }
    }
  }
