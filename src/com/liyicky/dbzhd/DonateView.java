package com.liyicky.dbzhd;

import java.math.BigDecimal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

public class DonateView extends Activity implements OnClickListener {

	private PayPal pp;
	private AdView adView;
	private ProgressDialog ppDialog;
	private CheckoutButton payButton, payButton2;
	private EditText amount;
	private String aString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initLibrary();

		ScrollView scroller = new ScrollView(this);
		scroller.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		amount = new EditText(this);
		amount.setText(".99");
		amount.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		amount.setGravity(Gravity.CENTER_HORIZONTAL);
		amount.setHint("Donation Amount");
		amount.setTextSize(20f);
		amount.setPadding(40, 10, 40, 0);
		amount.setInputType(InputType.TYPE_CLASS_PHONE);

		LinearLayout simplePay = new LinearLayout(this);
		simplePay.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		simplePay.setBackgroundColor(Color.TRANSPARENT);
		simplePay.setGravity(Gravity.CENTER_HORIZONTAL);
		simplePay.setPadding(10, 10, 10, 10);
		simplePay.setOrientation(LinearLayout.VERTICAL);
		simplePay.setBackgroundResource(R.drawable.pp_bg2);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_BOTTOM);
		params.topMargin = 20;
		payButton = pp.getCheckoutButton(this, PayPal.BUTTON_194x37,
				CheckoutButton.TEXT_DONATE);
		payButton.setOnClickListener(this);
		payButton.setLayoutParams(params);

		TextView btnLabel = new TextView(this);
		btnLabel.setGravity(Gravity.CENTER_HORIZONTAL);
		btnLabel.setText("Donate to DBZ-HD Stream");
		btnLabel.setTextSize(16f);
		btnLabel.setTypeface(null, Typeface.BOLD);
		btnLabel.setTextColor(Color.BLACK);

		LinearLayout btnHolder = new LinearLayout(this);
		btnHolder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		btnHolder.setGravity(Gravity.CENTER_HORIZONTAL);
		btnHolder.setOrientation(LinearLayout.VERTICAL);
		btnHolder.setBackgroundColor(Color.TRANSPARENT);
		btnHolder.setPadding(0, 40, 0, 40);
		btnHolder.addView(payButton);
		btnHolder.addView(btnLabel);

		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_BOTTOM);
		params2.topMargin = 20;
		payButton2 = pp.getCheckoutButton(this, PayPal.BUTTON_194x37,
				CheckoutButton.TEXT_DONATE);
		payButton2.setOnClickListener(this);
		payButton2.setLayoutParams(params2);

		TextView btnLabel2 = new TextView(this);
		btnLabel2.setGravity(Gravity.CENTER_HORIZONTAL);
		btnLabel2.setText("Donate to DBZ-HD App");
		btnLabel2.setTypeface(null, Typeface.BOLD);
		btnLabel2.setTextSize(16f);
		btnLabel2.setTextColor(Color.BLACK);

		LinearLayout btnHolder2 = new LinearLayout(this);
		btnHolder2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		btnHolder2.setGravity(Gravity.CENTER_HORIZONTAL);
		btnHolder2.setOrientation(LinearLayout.VERTICAL);
		btnHolder2.setBackgroundColor(Color.TRANSPARENT);
		btnHolder2.setPadding(0, 20, 0, 20);
		btnHolder2.addView(payButton2);
		btnHolder2.addView(btnLabel2);
		
		adView = new AdView(this, AdSize.BANNER, "KEY");
		adView.loadAd(new AdRequest());

		simplePay.addView(amount);
		//simplePay.addView(btnHolder);
		simplePay.addView(btnHolder2);
		simplePay.addView(adView);
		scroller.addView(simplePay);
		setContentView(scroller);
	}

	public void initLibrary() {

		pp = PayPal.getInstance();
		pp = PayPal.initWithAppID(DonateView.this, "KEY",
				PayPal.ENV_LIVE);
		pp.setLanguage("en_US");

	}

	public void libLoader() {
		ppDialog = new ProgressDialog(DonateView.this);
		ppDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		ppDialog.setMessage("Loading PayPal");
		ppDialog.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		aString = amount.getText().toString();
		if (aString == null)
			aString = "1";

		if (v == payButton) {
			ppDialog = new ProgressDialog(DonateView.this);
			ppDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			ppDialog.setMessage("Loading PayPal");
			ppDialog.show();

			PayPalPayment payment = new PayPalPayment();
			payment.setSubtotal(new BigDecimal(aString));
			payment.setCurrencyType("USD");
			payment.setRecipient("EMAIL");
			payment.setPaymentType(PayPal.PAYMENT_TYPE_PERSONAL);

			Intent checkoutI = PayPal.getInstance().checkout(payment, this);
			this.startActivityForResult(checkoutI, 1);

		} else if (v == payButton2) {
			ppDialog = new ProgressDialog(DonateView.this);
			ppDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			ppDialog.setMessage("Loading PayPal");
			ppDialog.show();

			PayPalPayment payment = new PayPalPayment();
			payment.setSubtotal(new BigDecimal(aString));
			payment.setCurrencyType("USD");
			payment.setRecipient("EMAIL");
			payment.setPaymentType(PayPal.PAYMENT_TYPE_PERSONAL);

			Intent checkoutI = PayPal.getInstance().checkout(payment, this);
			this.startActivityForResult(checkoutI, 1);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) {

		case Activity.RESULT_OK:
			ppDialog.cancel();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("THANK YOU FOR DONATING!!");
			builder.setPositiveButton("Back To the Show",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();

			break;

		case Activity.RESULT_CANCELED:
			ppDialog.cancel();
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle("Donation Canceled");
			builder2.setPositiveButton("Back To the Show",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			AlertDialog alert2 = builder2.create();
			alert2.show();
			break;

		case PayPalActivity.RESULT_FAILURE:
			ppDialog.cancel();
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setTitle("The Donation wasn't able to Process");
			builder3.setPositiveButton("Back To the Show",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			AlertDialog alert3 = builder3.create();
			alert3.show();
			break;
		}
	}

	public boolean onCreateOptionsMenu(android.view.Menu menu) {

		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.donate_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.dbzDBack:
			finish();
			break;
		}
		return false;
	}
	
	public void onBackPressed() {

	    finish();
	    // This above line close correctly
	}

}
