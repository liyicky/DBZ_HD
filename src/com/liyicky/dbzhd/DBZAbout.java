package com.liyicky.dbzhd;




import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdView;

public class DBZAbout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbz_about);
		StartAni();
		
	}

	private void StartAni() {

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_two);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.dbzAbout);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		ImageView i = (ImageView) findViewById(R.id.dbi_4s);
		i.clearAnimation();
		i.startAnimation(anim);
		
		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha_two);
		anim.reset();
		TextView tv = (TextView) findViewById(R.id.tv_version);
		tv.clearAnimation();
		tv.startAnimation(anim2);
	}
	
	@Override
	public void onBackPressed() {

	    finish();
	   
	}

}
