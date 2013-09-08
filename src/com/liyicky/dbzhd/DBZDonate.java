package com.liyicky.dbzhd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;

public class DBZDonate extends Activity implements AdListener {
	private ImageButton donater;
	private Button adLoader;
	private Button emailer;
	private String eAddress[] = { "liyicky@gmail.com" };
	private AdView adView;
	private LinearLayout adHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbz_donate);
		loadAd();
		StartAni();

		donater = (ImageButton) findViewById(R.id.btn_donate);
		donater.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ProgressDialog ppDialog = new ProgressDialog(DBZDonate.this);
				ppDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				ppDialog.setMessage("Loading PayPal...");
				ppDialog.show();
				Intent i = new Intent(DBZDonate.this, DonateView.class);
				startActivity(i);
				finish();
			}
		});

		adLoader = (Button) findViewById(R.id.btn_ad);
		adLoader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent a = new Intent(DBZDonate.this, DBZAdView.class);
				startActivity(a);
				finish();

			}
		});

		emailer = (Button) findViewById(R.id.btn_email);
		emailer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent e = new Intent(android.content.Intent.ACTION_SEND);
				e.putExtra(android.content.Intent.EXTRA_EMAIL, eAddress);
				e.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"DBZ HDTV App Email");
				e.setType("plain/text");
				startActivity(e);
				finish();

			}
		});

	}

	private void loadAd() {
		adHolder = (LinearLayout) findViewById(R.id.loAd);
		adView = (AdView) findViewById(R.id.adView);
		adView = new AdView(this, AdSize.BANNER, "KEY");
		//adHolder.addView(adView);
		//adView.loadAd(new AdRequest());
		adView.setAdListener(this);
	}
	public interface AdListener {
		public void onReceiveAd(Ad ad);
	}

	private void StartAni() {

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_two);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.dbz_donate);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		ImageView i = (ImageView) findViewById(R.id.dbi_1s);
		i.clearAnimation();
		i.startAnimation(anim);

		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha_two);
		anim.reset();
		TextView tv = (TextView) findViewById(R.id.tv_donate);
		tv.clearAnimation();
		tv.startAnimation(anim2);
	}

	@Override
	public void onBackPressed() {

		finish();

	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		

	}

}
