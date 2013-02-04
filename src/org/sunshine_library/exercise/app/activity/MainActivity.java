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


public class MainActivity extends TopActivity implements OnClickListener{

	private WebView content;
	private VideoView mVideoView;
	private LinearLayout mLinearLayout;
	private RelativeLayout play;

	
    /**
     * Called when the activity is first created.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);




        content = (WebView) findViewById(R.id.content);
        
    	mVideoView = (VideoView) findViewById(R.id.vv);
		mLinearLayout =(LinearLayout)findViewById(R.id.player_loading);
		play=(RelativeLayout) findViewById(R.id.play);
        
        WebSettings settings = content.getSettings();
        settings.setJavaScriptEnabled(true);
        content.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
        });

        // 过滤调用第三方浏览器。并且解析视频网站播放地址，传给播放器
        content.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {

                if (url.contains("3gp") || url.contains("mp4")) {
                    loadurl(view, url, true);// 载入视频
                } else {
                    loadurl(view, url, false);// 载入网页
                }

                return true;
            }// 重写点击动作,用webview载入
        });


        loadurl(content, "file:///android_asset/index.html", false);
        content.addJavascriptInterface(new MyHtmlInterface(), "android");

        startService(new Intent(MainActivity.this, NotificationService.class));
    }

    public void openVideoActivity(View v){
      loadurl(content,"http://m.youku.com/smartphone/channels?cid=92",false);

    }

 /**
	 * 加载网络页面
	 * @param view
	 * @param url
	 * @param isVideoUrl
	 */
	public void loadurl(final WebView view, final String url,
			final boolean isVideoUrl) {

		String uris = url;
		if (isVideoUrl) {
			// 判断是否是播放地址，并启动播放的
			if (uris.contains("3gp") || uris.contains("mp4")) {
				// 转义uri地址
				Uri uri = Uri.parse(uris);
				play(uri);
			}
		} else {
			view.loadUrl(url);// 载入网页
		}
	}
	
	// 播放器的效果
	public void play(Uri uri) {

		play.setVisibility(View.VISIBLE);
		
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
				play.setVisibility(View.GONE);
			}
		});
	}
	
	// 捕捉返回键，首先隐藏播放器，然后判断是否能够返回。
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		     if(play.getVisibility()==View.VISIBLE){
		    	 mVideoView.stopPlayback();
		    	 play.setVisibility(View.GONE);
		    	 return true;
		     }
		}
		return super.onKeyDown(keyCode, event);
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
