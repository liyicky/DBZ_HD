package com.liyicky.dbzhd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.flurry.android.FlurryAgent;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class DBZAdView extends Activity {

	private ProgressDialog ppDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dbz_ad);
		FlurryAgent.logEvent("AdView Loaded");

		AdView adView = (AdView) findViewById(R.id.adView);
		adView = new AdView(this, AdSize.BANNER, "KEY");
		adView.loadAd(new AdRequest());

		ppDialog = new ProgressDialog(DBZAdView.this);
		ppDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		ppDialog.setMessage("Thank you for supporting this app!");
		ppDialog.show();
		
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				ppDialog.hide();
			}
		}, 8000);
	}
	
	
	@Override
	public void onBackPressed() {

		ppDialog.hide();
		finish();

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//FlurryAgent.onEndSession(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		//FlurryAgent.onStartSession(this, "KEY");

	}


}
