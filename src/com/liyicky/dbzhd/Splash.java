package com.liyicky.dbzhd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splash extends Activity {

	private Thread timer;
	private MediaPlayer intro;
	private SharedPreferences getPrefs;
	private SharedPreferences getPrefs2;
	final Splash splash = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		timer = new Thread() {
			public void run() {
				try {

					synchronized (this) {
						sleep(1500);

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

					overridePendingTransition(R.anim.appear, R.anim.disappear);
					Intent intent = new Intent();
					intent.setClass(splash, WebViewPager.class);
					startActivity(intent);

				}
			}
		};

		StartAni();

		getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean skipSplash = getPrefs.getBoolean("splash_checkbox", true);
		if (skipSplash == false) {
			Intent intent = new Intent();
			intent.setClass(splash, WebViewPager.class);
			startActivity(intent);
		} else {
			timer.start();
		}

		intro = MediaPlayer.create(Splash.this, R.raw.splash_charge);
		getPrefs2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs2.getBoolean("sound_checkbox", false);
		if (music == true && skipSplash == true) {
			intro.start();
		}

	}

	private void StartAni() {

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.splash);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		ImageView i = (ImageView) findViewById(R.id.shenron);
		i.clearAnimation();
		i.startAnimation(anim);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		intro.release();
		finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
