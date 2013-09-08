package com.liyicky.dbzhd;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StreamClient extends WebViewClient {
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView v, String url){
		
		v.loadUrl(url);
		return true;
	}

}
