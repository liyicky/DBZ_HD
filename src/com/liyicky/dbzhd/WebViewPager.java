package com.liyicky.dbzhd;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.flurry.android.FlurryAgent;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.liyicky.dbzhd.SimpleGestureFilter.SimpleGestureListener;

public class WebViewPager extends Activity implements SimpleGestureListener {

	private FrameLayout holder;
	private WebView streamView;
	private WebView chatView;
	private ViewFlipper flipView;
	private String url = "http://www.justin.tv/yungboss318/popout";
	private String url2 = "http://www.justin.tv/chat/embed?channel=yungboss318";
	private String viewPref;
	private SimpleGestureFilter filter;
	private Animation fadeIn;
	private ProgressDialog progressB;
	private ProgressDialog progressA;
	private SharedPreferences prefs;
	private WakeLock lock;
	private Thread timer;
	private AudioManager am;
	private AdView adView;
	private boolean chatChanged = false;
	final Activity activity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (getIntent().getBooleanExtra("EXIT", false)) {
			WebViewPager.this.finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		GetIDs();
		PowerLock();
		setPrefView();
		StreamClientSettings();
		LoadingBar();
		AdLoader();
		ThreadLoader();

	}

	public class ChatTask extends Thread {

		public void run() {
			chatView = (WebView) findViewById(R.id.chatView);
			ChatClientSettings();

		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.filter.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	public void StreamClientSettings() {

		streamView.getSettings().setPluginState(PluginState.ON);
		streamView.getSettings().setJavaScriptEnabled(true);
		streamView.setWebViewClient(new StreamClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView v, String url) {
				return false;
			}

			@Override
			public void onPageFinished(WebView wv, String url) {
				super.onPageFinished(wv, url);
				progressB.hide();
				FlurryAgent.logEvent("Watched Stream");
				Toaster2();
			}
		});
		streamView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		streamView.getSettings().setLoadsImagesAutomatically(true);
		streamView.getSettings().setUseWideViewPort(true);
		streamView.getSettings().setLoadWithOverviewMode(true);
		streamView.getSettings().setSupportZoom(false);
		String s = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1)";
		streamView.getSettings().setUserAgentString(s);
		streamView.requestFocus(View.FOCUS_DOWN);
		streamView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					if (!v.hasFocus()) {
						v.requestFocus();
					}
					break;
				}
				return false;
			}
		});
		streamView.loadUrl(url);

	}

	public void ChatClientSettings() {

		chatView.getSettings().setPluginState(PluginState.ON);
		chatView.getSettings().setJavaScriptEnabled(true);
		chatView.setWebViewClient(new StreamClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView v, String url) {

				return false;
			}

			@Override
			public void onPageStarted(WebView v, String url, Bitmap b) {
				chatChanged = true;
				progressA = new ProgressDialog(WebViewPager.this);
				progressA.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressA.setMessage("Loading (Press Back to Close)");
				progressA.show();

			}

			@Override
			public void onPageFinished(WebView wv, String url) {
				super.onPageFinished(wv, url);
				progressA.hide();

			}
		});
		chatView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		chatView.getSettings().setLoadsImagesAutomatically(true);
		chatView.getSettings().setUseWideViewPort(true);
		chatView.getSettings().setLoadWithOverviewMode(true);
		String s = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1)";
		chatView.getSettings().setUserAgentString(s);

		// chatView.requestFocus(View.FOCUS_DOWN);
		chatView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					if (!v.hasFocus()) {
						v.requestFocus();
					}
					break;
				}
				return false;
			}
		});

	}

	private void ThreadLoader() {
		

		Handler a = new Handler();
		a.postDelayed(new Runnable() {
			@Override
			public void run() {
				chatChanged = false;

			}
		}, 30000);

		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				chatView.loadUrl(url2);

			}
		}, 4000);

		new ChatTask().start();

	}

	private void GetIDs() {
		FlurryAgent.logEvent("Loading Stream");
		holder = (FrameLayout) findViewById(R.id.streamViewHolder);
		streamView = (WebView) findViewById(R.id.streamView);
		flipView = (ViewFlipper) findViewById(R.id.mainFlipper);
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.alpha_two);
		this.filter = new SimpleGestureFilter(this, this);
		this.filter.setMode(SimpleGestureFilter.MODE_TRANSPARENT);
		
	}

	private void PowerLock() {
		PowerManager power = (PowerManager) getSystemService(Context.POWER_SERVICE);
		lock = power.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake Lock");
		lock.acquire();
	}

	private void setPrefView() {

		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean introView = prefs.getBoolean("view_checkbox", false);
		if (introView == true) {
			setChatView();
		}
	}

	private void setStreamView() {
		FlurryAgent.endTimedEvent("Swipe to Stream");
		holder.addView(streamView);
		flipView.setDisplayedChild(0);

	}

	private void setChatView() {
		FlurryAgent.endTimedEvent("Swiped to Chat");
		holder.removeAllViews();
		flipView.setDisplayedChild(1);
		flipView.setInAnimation(fadeIn);
	}

	public void LoadingBar() {
		progressB = new ProgressDialog(WebViewPager.this);
		progressB.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressB.setMessage("Loading the Show");
		progressB.show();

	}

	private void AdLoader() {
		adView = new AdView(this, AdSize.BANNER, "KEY");
		adView.loadAd(new AdRequest());

	}

	@Override
	public void onSwipe(int direction) {
		// TODO Auto-generated method stub

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT:
			if (flipView.getDisplayedChild() == 1) {
				setStreamView();

			}
			break;

		case SimpleGestureFilter.SWIPE_LEFT:
			if (flipView.getDisplayedChild() == 0) {
				setChatView();

			}
			break;
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_stream, menu);
		Toaster();
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.dbzAbout:
			Intent c = new Intent(this, DBZAbout.class);
			startActivity(c);
			break;
		case R.id.dbzDonate:
			Intent b = new Intent(this, DBZDonate.class);
			startActivity(b);
			break;
		case R.id.dbzR:
			if (flipView.getDisplayedChild() == 0) {
				streamView.loadUrl(url);
				LoadingBar();
			} else if (flipView.getDisplayedChild() == 1) {
				chatView.loadUrl(url2);
				LoadingBar();
			}
			break;
		case R.id.dbzQuit:
			Intent Q = new Intent(this, DBZQUIT.class);
			startActivity(Q);
			break;
		case R.id.dbzOptions:
			Intent o = new Intent(this, DBZPreferences.class);
			startActivity(o);
			break;
		}
		return false;
	}

	public void Toaster() {

		final Toast toast = Toast.makeText(this, "liyicky was here",
				Toast.LENGTH_SHORT);
		toast.show();

		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				toast.cancel();
			}
		}, 100);
	}

	public void Toaster2() {

		if (flipView.getDisplayedChild() == 0) {
			final Toast toast = Toast.makeText(this,
					"SWIPE or BACK BTN to Chat", Toast.LENGTH_SHORT);
			toast.show();
		} else if (flipView.getDisplayedChild() == 1) {
			final Toast toast = Toast.makeText(this,
					"SWIPE or BACK BTN to Stream", Toast.LENGTH_SHORT);
			toast.show();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		final ActivityManager amm = (ActivityManager) WebViewPager.this
				.getSystemService(Activity.ACTIVITY_SERVICE);
		amm.restartPackage("com.liyicky.dbzhd");
		amm.killBackgroundProcesses("com.liyicky.dbzhd");

	}

	@Override
	protected void onPause() {
		super.onPause();
		am = (AudioManager) getSystemService(AUDIO_SERVICE);
		am.setStreamMute(AudioManager.STREAM_MUSIC, true);
		lock.release();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		am = (AudioManager) getSystemService(AUDIO_SERVICE);
		am.setStreamMute(AudioManager.STREAM_MUSIC, false);
		lock.acquire();

	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.endTimedEvent("Finished Stream");
		FlurryAgent.onEndSession(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "KEY");

	}

	public void onBackPressed() {

		if (chatChanged == true) {
			chatView.loadUrl(url2);
			chatChanged = false;

		} else if (chatChanged == false) {
			if (flipView.getDisplayedChild() == 1) {
				setStreamView();

			} else if (flipView.getDisplayedChild() == 0) {
				setChatView();
			}

		}
	}

}
