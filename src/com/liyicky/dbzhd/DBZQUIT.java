package com.liyicky.dbzhd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class DBZQUIT extends Activity {

	Button quit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dbz_quit);
		StartAni();
		quit = (Button) findViewById(R.id.btn_quit);
		quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				 Intent i = new Intent(getApplicationContext(),
				 WebViewPager.class);
				 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 i.putExtra("EXIT", true);
				 startActivity(i);

			}
		});
	}

	private void StartAni() {

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_two);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.dbz_quit);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate2);
		anim.reset();
		Button i = (Button) findViewById(R.id.btn_quit);
		i.clearAnimation();
		i.startAnimation(anim);
	}

}
